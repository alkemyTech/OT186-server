package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDTO userEntity2DTO(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setRoles(entity.getRoles());
        userDTO.setFirstName(entity.getFirstName());
        userDTO.setLastName(entity.getLastName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPassword(entity.getPassword());
        userDTO.setPhoto(entity.getPhoto());
        return userDTO;
    }


    public List<UserDTO> userEntityList2DTOList(List<User> entities){
        List<UserDTO> dtos = new ArrayList<>();
        for (User entity : entities) {
            dtos.add(this.userEntity2DTO(entity));
        }
        return dtos;
    }

}