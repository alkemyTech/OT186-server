package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user) {
        UserDto userResponse = new UserDto();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoto(user.getPhoto());
        return userResponse;
    }
}
