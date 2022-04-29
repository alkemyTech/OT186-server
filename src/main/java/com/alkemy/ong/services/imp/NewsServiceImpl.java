package com.alkemy.ong.services.imp;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    public NewsDTO getDetailsById(UUID id){
        Optional<News> news = newsRepository.findById(id);
        if(news.isPresent()){
            NewsDTO result = newsMapper.news2DTO(news.get());
            return result;
        }else{
            throw new EntityNotFoundException("News not found");
        }
    }

    public List<NewsDTO> getAllNews(){
        List<News> news = newsRepository.findAll();
        List<NewsDTO> result = newsMapper.newsDTOList(news);
        return result;
    }

    public NewsDTO save(NewsDTO newsDTO) {
        News entitySaved = newsRepository.save(newsMapper.newsDTO2Entity(newsDTO));
        NewsDTO result = newsMapper.news2DTO(entitySaved);
        return result;
    }

    public NewsDTO update(UUID id, NewsDTO newsDTO) {
        Optional<News> result = newsRepository.findById(id);
        if(result.isPresent()){
            News entity = newsMapper.updateNewsDTO2Entity(result.get(),newsDTO);
            News entityUpdated = newsRepository.save(entity);
            NewsDTO dtoUpdated = newsMapper.news2DTO(entityUpdated);
            return dtoUpdated;
        }else{
            throw new EntityNotFoundException("News not found");
        }
    }

    public void delete(UUID id){
        if(newsRepository.findById(id) == null){
            throw new EntityNotFoundException("News not found");
        }
        newsRepository.deleteById(id);
    }

}
