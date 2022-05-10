package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestimonialServiceImp implements TestimonialService {

    private final String pattern="localhost:8080/testimonial?page=";
    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    public TestimonialDTO save(TestimonialDTO testimonialDTO) {
        Testimonial entity = testimonialRepository.save(testimonialMapper.testimonial2DTOEntity(testimonialDTO));
        return testimonialMapper.testimonial2DTO(entity);
    }

    public TestimonialDTO update(UUID id, TestimonialDTO testimonialDTO) {
        Optional<Testimonial> result = testimonialRepository.findById(id);
        if(result.isPresent()){
            Testimonial entity = testimonialMapper.updateTestimonial2DTO(result.get(),testimonialDTO);
            Testimonial entityUpdated = testimonialRepository.save(entity);
            return testimonialMapper.testimonial2DTO(entityUpdated);
        }else{
            throw new EntityNotFoundException("Testimonial not found");
        }
    }

    public void delete(UUID id){
        if(testimonialRepository.findById(id) == null){
            throw new EntityNotFoundException("Testimonial not found");
        }
        testimonialRepository.deleteById(id);
    }

    @Override
    public PageFormatter<TestimonialDTO> findPageable(Pageable pageable) {
        Page<Testimonial> testimonials = testimonialRepository.findAll(pageable);
        if(testimonials.isEmpty()){
            throw new EntityNotFoundException("Testimonial not found");
        }else{
            Page<TestimonialDTO> testimonialDTOS = testimonials.map(entity -> testimonialMapper.testimonial2DTO(entity));
            PageFormatter<TestimonialDTO> pageFormatter = new PageFormatter<>();
            pageFormatter.setPageContent(testimonialDTOS.getContent());
            if(testimonialDTOS.getNumber() > 0){
                pageFormatter.setPreviousPageUrl(this.pattern + (testimonialDTOS.getNumber() - 1));
            }else{
                pageFormatter.setPreviousPageUrl("void");
            }
            if(testimonialDTOS.getNumber() < testimonialDTOS.getTotalPages() - 1){
                pageFormatter.setNextPageUrl(this.pattern + (testimonialDTOS.getNumber()) + 1);
            }else{
                pageFormatter.setNextPageUrl("void");
            }
            return pageFormatter;
        }
    }
}
