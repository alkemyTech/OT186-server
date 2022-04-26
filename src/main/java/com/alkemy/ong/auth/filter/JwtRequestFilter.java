package com.alkemy.ong.auth.filter;

import com.alkemy.ong.auth.utils.JwtUtils;
import com.alkemy.ong.services.UserService;
import com.alkemy.ong.services.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceImp userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7);
            email = jwtUtils.extractUsername(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userService.loadUserByUsername(email);

            if (jwtUtils.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());

                Authentication auth = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}