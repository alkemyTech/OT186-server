package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.entity.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest extends AuthForTest {

    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;

    private static final String MEMBER_PATH = "/members";

    public static final UUID ID_MEMBER =  java.util.UUID.fromString("f7753aa7-5e43-46a7-a980-eb67de845bbb");

    private MemberDTO aMemberDTO(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(ID_MEMBER);
        memberDTO.setName("John Doe");
        memberDTO.setFacebookUrl("www.facebook.com/john-doe");
        memberDTO.setInstagramUrl("www.instagram.com/john-doe");
        memberDTO.setLinkedinUrl("www.linkedin.com/in/john-doe");
        memberDTO.setImage("profile-john-doe.jpg");
        memberDTO.setDescription("He has been member since 2010");
        return memberDTO;
    }

    public Member aMember(){
        Member member = new Member();
        member.setId(ID_MEMBER);
        member.setDeleted(Boolean.FALSE);
        member.setName("John Doe");
        member.setFacebookUrl("www.facebook.com/john-doe");
        member.setInstagramUrl("www.instagram.com/john-doe");
        member.setImage("profile-john-doe.jpg");
        member.setDescription("He has been member since 2010");
        member.setTimestamps(Timestamp.from(Instant.now()));
        return member;
    }

    public  List<Member> aMemberList = List.of(aMember());

    @Test
    public void getAllHappyPath() {

        Page<Member> page = new PageImpl<>(aMemberList);
        when(memberRepository.findAll(any(Pageable.class)))
                .thenReturn(page);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<PageFormatter> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH),
                HttpMethod.GET,
                httpEntity,
                PageFormatter.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotEquals(0, response.getBody().getPageContent().size());
    }

    @Test
    public void getAllForbidden() {

        Page<Member> page = new PageImpl<>(aMemberList);
        when(memberRepository.findAll(any(Pageable.class)))
                .thenReturn(page);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_USER");

        ResponseEntity<PageFormatter> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH),
                HttpMethod.GET,
                httpEntity,
                PageFormatter.class
        );

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteHappyPath() {

        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<PageFormatter> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH + "/" + ID_MEMBER),
                HttpMethod.DELETE,
                httpEntity,
                PageFormatter.class
        );

        verify(memberRepository, times(1)).deleteById(ID_MEMBER);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void createHappyPath() {

        when(memberRepository.save(any(Member.class))).thenReturn(aMember());
        HttpEntity<MemberDTO> httpEntity = new HttpEntity<>(aMemberDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH),
                HttpMethod.POST,
                httpEntity,
                MemberDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void createInvalidBody400(){

        when(memberRepository.save(any(Member.class))).thenReturn(aMember());
        HttpEntity<MemberDTO> httpEntity = new HttpEntity<>(new MemberDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH),
                HttpMethod.POST,
                httpEntity,
                MemberDTO.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateHappyPath(){

        when(memberRepository.findById(ID_MEMBER)).thenReturn(Optional.of(aMember()));
        when(memberRepository.save(any(Member.class))).thenReturn(aMember());
        HttpEntity<MemberDTO> httpEntity = new HttpEntity<>(aMemberDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH + "/" + ID_MEMBER),
                HttpMethod.PUT,
                httpEntity,
                MemberDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ID_MEMBER, response.getBody().getId());
    }

    @Test
    public void updateNotFound() {

        when(memberRepository.findById(ID_MEMBER)).thenReturn(Optional.empty());
        HttpEntity<MemberDTO> httpEntity = new HttpEntity<>(aMemberDTO(), httpHeaders);
        putTokenInHeader("ROLE_ADMIN");

        ResponseEntity<MemberDTO> response = testRestTemplate.exchange(
                generateUriWithPort(MEMBER_PATH + "/" + ID_MEMBER),
                HttpMethod.PUT,
                httpEntity,
                MemberDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}