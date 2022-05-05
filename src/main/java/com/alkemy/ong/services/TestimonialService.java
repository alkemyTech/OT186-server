package com.alkemy.ong.services;

import com.alkemy.ong.dto.TestimonialDTO;

import java.util.UUID;

public interface TestimonialService {

    TestimonialDTO save(TestimonialDTO testimonialDTO);
    TestimonialDTO update(UUID id, TestimonialDTO testimonialDTO);
    void delete(UUID id);
}