package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestimonialServiceImp implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    public TestimonialDTO save(TestimonialDTO testimonialDTO) {
        Testimonial entity = testimonialRepository.save(testimonialMapper.testimonial2DTOEntity(testimonialDTO));
        TestimonialDTO result = testimonialMapper.testimonial2DTO(entity);
        return result;
    }

    public TestimonialDTO update(UUID id, TestimonialDTO testimonialDTO) {
        Optional<Testimonial> result = testimonialRepository.findById(id);
        if(result.isPresent()){
            Testimonial entity = testimonialMapper.updateTestimonial2DTO(result.get(),testimonialDTO);
            Testimonial entityUpdated = testimonialRepository.save(entity);
            TestimonialDTO dtoUpdated = testimonialMapper.testimonial2DTO(entityUpdated);
            return dtoUpdated;
        }else{
            throw new EntityNotFoundException("Testimonial not found");
        }
    }
}
