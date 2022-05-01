package com.alkemy.ong.dto.mapper;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationDTO characterEntity2DTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setImage(organization.getImage());
        dto.setAddress(organization.getAddress());
        dto.setPhone(organization.getPhone());
        return dto;
    }
}
