package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationDTO entity2DTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setImage(organization.getImage());
        dto.setAddress(organization.getAddress());
        dto.setPhone(organization.getPhone());
        dto.setFacebook(organization.getFacebook());
        dto.setLinkedin(organization.getLinkedin());
        dto.setInstagram(organization.getInstagram());
        return dto;
    }

    public Organization updateDTO2Entity(Organization entity, OrganizationDTO dto) {
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setFacebook(dto.getFacebook());
        entity.setLinkedin(dto.getLinkedin());
        entity.setInstagram(dto.getInstagram());
        return entity;
    }
}
