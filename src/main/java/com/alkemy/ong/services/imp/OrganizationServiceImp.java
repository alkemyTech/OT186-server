package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.services.OrganizationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class OrganizationServiceImp implements OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationMapper organizationMapper;

    @Override
    public OrganizationDTO getDetailsById(UUID id) {
        Optional<Organization> entity = this.organizationRepository.findById(id);
        if(entity.isEmpty()) {
            throw new ParamNotFound("invalid organization id");
        }
        return this.organizationMapper.characterEntity2DTO(entity.get());

}
}
