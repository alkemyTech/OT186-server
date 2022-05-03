package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationServiceImp implements OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public OrganizationDTO getDetailsById(UUID id) {
        Optional<Organization> entity = this.organizationRepository.findById(id);
        if(entity.isEmpty()) {
            throw new ParamNotFound("invalid organization id");
        }
        return this.organizationMapper.entity2DTO(entity.get());

    }

    public OrganizationDTO update(UUID id, OrganizationDTO dto) {
        Optional<Organization> result = organizationRepository.findById(id);
        if (result.isPresent()) {
            Organization entity = organizationMapper.updateDTO2Entity(result.get(), dto);
            Organization entityUpdated = organizationRepository.save(entity);
            OrganizationDTO dtoUpdated = organizationMapper.entity2DTO(entityUpdated);
            return dtoUpdated;
        } else {
            throw new EntityNotFoundException("Organization not found");
        }
    }

}
