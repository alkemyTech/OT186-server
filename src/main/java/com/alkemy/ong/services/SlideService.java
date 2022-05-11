package com.alkemy.ong.services;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SlideService {
    SlideDTO getDetailsById(UUID id);
    List<SlideDTOBasic> getAllSlideBasic();
    void delete(UUID id);
    SlideDTO update(UUID id, SlideDTO slideDTO);
    List<SlideDTOBasic> listSlidesByOrganizationId(UUID organization_id);
    SlideDTO create(SlideDTO slideDTO)throws IOException;
    List<SlideDTO> findAllSlide();
}
