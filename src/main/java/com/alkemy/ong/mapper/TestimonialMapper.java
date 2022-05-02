package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public TestimonialDTO testimonial2DTO(Testimonial entity){
        TestimonialDTO dto = new TestimonialDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setContent(entity.getContent());
        return dto;
    }

    public Testimonial testimonial2DTOEntity(TestimonialDTO dto){
        Testimonial testimonial = new Testimonial();
        testimonial.setId(dto.getId());
        testimonial.setName(dto.getName());
        testimonial.setImage(dto.getImage());
        testimonial.setContent(dto.getContent());
        return testimonial;
    }

    public Testimonial updateTestimonial2DTO(Testimonial entity, TestimonialDTO dto){
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setContent(dto.getContent());
        return entity;
    }
}
