package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private SlideMapper slideMapper;

    public SlideDTO getDetailsById(UUID id){
        Optional<Slide> slide = slideRepository.findById(id);
        if(slide.isPresent()){
            SlideDTO result = slideMapper.slide2DTO(slide.get());
            return result;
        }else{
            throw new EntityNotFoundException("Slide not found");
        }
    }

    public List<SlideDTOBasic> getAllSlideBasic(){
        List<Slide> slide = slideRepository.findAll();
        List<SlideDTOBasic> result = slideMapper.slideDTOListBasic(slide);
        return result;
    }

    public void delete(UUID id) {
        Optional<Slide> slide = slideRepository.findById(id);
        if (slide.isPresent()){
            slideRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Slide not found.");
        }
    }

    public SlideDTO update(UUID id, SlideDTO slideDTO) {
        Optional<Slide> entity = this.slideRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFound("Invalid Slide ID");
        }

        this.slideMapper.slideEntityRefreshValues(entity.get(),slideDTO);
        Slide entitySaved = this.slideRepository.save(entity.get());
        SlideDTO result = this.slideMapper.slideEntity2DTO(entitySaved);
        return result;
    }

    @Override
    public List<SlideDTOBasic> listSlidesByOrganizationId(UUID organization_id) {
        Optional<List<Slide>> slides = slideRepository.findByOrganizationId(organization_id);
        if(slides.isPresent()){
            return this.sortBySlideOrder(slideMapper.slideDTOListBasic(slides.get()));
        }else{
            return new ArrayList<>();
        }
    }

    private List<SlideDTOBasic> sortBySlideOrder(List<SlideDTOBasic> slides) {
        return slides.stream()
                .sorted(Comparator.comparing(SlideDTOBasic::getOrder))
                .collect(Collectors.toList());
    }


}
