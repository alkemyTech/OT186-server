package com.alkemy.ong.services;

import com.alkemy.ong.dto.OrganizationDTO;

import java.util.UUID;

public interface OrganizationService {
    OrganizationDTO getDetailsById(UUID id);
}
