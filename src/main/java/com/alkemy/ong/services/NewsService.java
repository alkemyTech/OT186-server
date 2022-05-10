package com.alkemy.ong.services;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.PageFormatter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    NewsDTO getDetailsById(UUID id);
    List<NewsDTO> getAllNews();
    NewsDTO save(NewsDTO newsDTO);
    NewsDTO update(UUID id, NewsDTO newsDTO);
    void delete(UUID id);

    PageFormatter<NewsDTO> findPageable(Pageable pageable);
    Boolean existById(UUID id);

}
