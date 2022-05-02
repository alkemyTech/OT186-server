package com.alkemy.ong.services;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;

import java.util.List;
import java.util.UUID;

public interface SlideService {
    SlideDTO getDetailsById(UUID id);
    List<SlideDTOBasic> getAllSlideBasic();
    List<SlideDTO> getAllSlide();
}
