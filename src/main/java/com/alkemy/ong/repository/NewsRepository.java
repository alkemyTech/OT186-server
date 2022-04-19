package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.entity.News;

public interface NewsRepository extends JpaRepository<News, Long>{

}
