package com.alkemy.ong.services;

import com.alkemy.ong.dto.NewsDTO;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    NewsDTO getDetailsById(UUID id);
    List<NewsDTO> getAllNews();
    NewsDTO save(NewsDTO newsDTO);
    NewsDTO update(UUID id, NewsDTO newsDTO);
    void delete(UUID id);

}
