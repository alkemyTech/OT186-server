package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlideRepository extends JpaRepository<Slide, UUID> {
    Optional<List<Slide>> findByOrganizationId(UUID organization_id);
}
