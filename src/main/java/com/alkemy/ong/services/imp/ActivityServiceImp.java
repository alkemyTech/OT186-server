package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImp implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    public ActivityDTO save(ActivityDTO activityDTO) {
        Activity entity = activityRepository.save(activityMapper.activity2DTOEntity(activityDTO));
        ActivityDTO result = activityMapper.activity2DTO(entity);
        return result;
    }

    public ActivityDTO update(UUID id, ActivityDTO activityDTO) {
        Optional<Activity> result = activityRepository.findById(id);
        if(result.isPresent()){
            Activity entity = activityMapper.updateActivity2DTO(result.get(),activityDTO);
            Activity entityUpdated = activityRepository.save(entity);
            ActivityDTO dtoUpdated = activityMapper.activity2DTO(entityUpdated);
            return dtoUpdated;
        }else{
            throw new EntityNotFoundException("Testimonial not found");
        }
    }
}
