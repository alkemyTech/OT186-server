package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideDTOBasic;
import com.alkemy.ong.dto.mapper.SlideMapper;
import com.alkemy.ong.entity.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            throw  new EntityNotFoundException("Slide not found");
        }
    }

    @Override
    public List<SlideDTOBasic> getAllSlideBasic() {
        List<Slide> slide = slideRepository.findAll();
        List<SlideDTOBasic> result = slideMapper.slideDTOBasicList(slide);
        return result;
    }

}
