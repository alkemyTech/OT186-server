package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.entity.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO activity2DTO(Activity entity){
        ActivityDTO dto = new ActivityDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContent(entity.getContent());
        dto.setImage(entity.getImage());
        return dto;
    }

    public Activity activity2DTOEntity(ActivityDTO dto){
        Activity activity = new Activity();
        activity.setId(dto.getId());
        activity.setName(dto.getName());
        activity.setContent(dto.getContent());
        activity.setImage(dto.getImage());
        return activity;
    }

    public Activity updateActivity2DTO(Activity entity, ActivityDTO dto){
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        entity.setImage(dto.getImage());
        return entity;
    }
}
