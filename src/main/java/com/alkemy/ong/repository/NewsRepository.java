package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.entity.News;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>{

}
