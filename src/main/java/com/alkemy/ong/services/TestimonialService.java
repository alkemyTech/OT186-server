package com.alkemy.ong.services;

import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.dto.TestimonialDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestimonialService {

    TestimonialDTO save(TestimonialDTO testimonialDTO);
    TestimonialDTO update(UUID id, TestimonialDTO testimonialDTO);
    void delete(UUID id);
    public PageFormatter<TestimonialDTO> findPageable(Pageable pageable);
}