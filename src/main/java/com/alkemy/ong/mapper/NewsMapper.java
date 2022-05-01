package com.alkemy.ong.dto.mapper;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.entity.News;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {

    public NewsDTO news2DTO(News entity){
        NewsDTO dto = new NewsDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setCategories(entity.getCategories());
        return dto;
    }

    public News newsDTO2Entity(NewsDTO dto){
        News news = new News();
        news.setId(dto.getId());
        news.setName(dto.getName());
        news.setImage(dto.getImage());
        news.setCategories(dto.getCategories());
        return news;
    }

    public List<NewsDTO> newsDTOList(List<News> entities){
        List<NewsDTO> dtos = new ArrayList<>();
        NewsDTO basicDTO;
        for(News entity : entities){
            basicDTO = new NewsDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setName(entity.getName());
            basicDTO.setImage(entity.getImage());
            basicDTO.setCategories(entity.getCategories());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public News updateNewsDTO2Entity(News entity, NewsDTO dto){
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setCategories(dto.getCategories());
        return entity;
    }

}
