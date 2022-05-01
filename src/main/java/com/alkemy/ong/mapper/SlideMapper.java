package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.entity.Slide;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

}
