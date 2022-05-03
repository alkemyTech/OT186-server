package com.alkemy.ong.services;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;

import java.util.UUID;

public interface ActivityService {

    ActivityDTO save(ActivityDTO activityDTO);
    ActivityDTO update(UUID id, ActivityDTO activityDTO);
}
