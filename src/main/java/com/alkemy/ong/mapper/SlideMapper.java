package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.entity.Slide;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlideMapper {

    public SlideDTO slide2DTO(Slide entity){
        SlideDTO dto = new SlideDTO();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());
        dto.setText(entity.getText());
        dto.setOrder(entity.getOrder());
        dto.setOrganizationId(entity.getOrganizationId());
        return dto;
    }

    public Slide slideDTO2Entity(SlideDTO dto){
        Slide slide = new Slide();
        slide.setId(dto.getId());
        slide.setImageUrl(dto.getImageUrl());
        slide.setText(dto.getText());
        slide.setOrder(dto.getOrder());
        slide.setOrganizationId(dto.getOrganizationId());
        return slide;
    }

    public List<SlideDTOBasic> slideDTOListBasic(List<Slide> entities){
        List<SlideDTOBasic> dtos = new ArrayList<>();
        SlideDTOBasic basicDTO;
        for(Slide entity : entities){
            basicDTO = new SlideDTOBasic();
            basicDTO.setImageUrl(entity.getImageUrl());
            basicDTO.setOrder(entity.getOrder());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public List<SlideDTO> slideDTOList(List<Slide> entities){
        List<SlideDTO> dtos = new ArrayList<>();
        SlideDTO basicDTO;
        for(Slide entity : entities){
            basicDTO = new SlideDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImageUrl(entity.getImageUrl());
            basicDTO.setText(entity.getText());
            basicDTO.setOrder(entity.getOrder());
            basicDTO.setOrganizationId(entity.getOrganizationId());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public void slideEntityRefreshValues(Slide slideEntity, SlideDTO slideDTO) {
        slideEntity.setImageUrl(slideDTO.getImageUrl());
        slideEntity.setText(slideDTO.getText());
        slideEntity.setOrder(slideDTO.getOrder());
        slideEntity.setOrganizationId(slideDTO.getOrganizationId());
    }

    public SlideDTO slideEntity2DTO(Slide slideEntity) {
        SlideDTO dto = new SlideDTO();
        dto.setId(slideEntity.getId());
        dto.setImageUrl(slideEntity.getImageUrl());
        dto.setText(slideEntity.getText());
        dto.setOrder(slideEntity.getOrder());
        dto.setOrganizationId(slideEntity.getOrganizationId());
        return dto;
    }

    public List<SlideDTO> listSlide2DTO(List<Slide> listSlide) {
        return listSlide.stream()
                .map(this::slide2DTO)
                .collect(Collectors.toList());
    }
}
