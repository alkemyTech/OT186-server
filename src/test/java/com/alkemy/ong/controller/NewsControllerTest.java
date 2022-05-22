package com.alkemy.ong.controller;

import com.alkemy.ong.auth.AuthForTest;
import com.alkemy.ong.config.AmazonS3ClientConfig;
import com.alkemy.ong.dto.CommentForNewsDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.PageFormatter;
import com.alkemy.ong.entity.Categories;
import com.alkemy.ong.entity.Comments;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.mapper.CommentsMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.services.CommentService;
import com.alkemy.ong.services.OrganizationService;
import com.alkemy.ong.services.SlideService;
import com.alkemy.ong.services.imp.AmazonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsControllerTest extends AuthForTest {

    @MockBean
    private NewsRepository newsRepository;
    @MockBean
    private CommentService commentService;
    @MockBean
    private SlideService slideService;
    @MockBean
    private OrganizationService service;
    @MockBean
    private AmazonServiceImpl amazonService;
    @MockBean
    private AmazonS3ClientConfig amazonS3ClientConfig;
    @MockBean
    private CommentsMapper commentsMapper;

    private static final String NEWS_PATH = "/news/";

    public  static final UUID ID_NEWS = UUID.fromString("123e4567-e89b-12d3-a456-426614174200");

    public List<News> newsList = List.of(buildEntity());

    public News buildEntity(){
        Categories category = new Categories();
        category.setId(UUID.randomUUID());
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        News news = new News();
        news.setId(ID_NEWS);
        news.setName("name");
        news.setContent("content");
        news.setImage("image");
        news.setCategories(category);
        news.setSoftDelete(false);
        return news;
    }

    private NewsDTO buildDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(ID_NEWS);
        newsDTO.setContent("content");
        newsDTO.setName("name");
        newsDTO.setImage("img");
        return newsDTO;
    }

    @Test
    void getAllshouldReturn200() {
        Page<News> page = new PageImpl<>(newsList);
        when(newsRepository.findAll(any(Pageable.class))).thenReturn(page);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<PageFormatter> response = testRestTemplate.exchange(generateUriWithPort(NEWS_PATH), HttpMethod.GET, httpEntity, PageFormatter.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotEquals(0, response.getBody().getPageContent().size());
    }

    @Test
    @WithMockUser
    void getAllshouldReturn401() {
        Page<News> page = new PageImpl<>(newsList);
        when(newsRepository.findAll(any(Pageable.class))).thenReturn(page);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PageFormatter> response = testRestTemplate.exchange(generateUriWithPort(NEWS_PATH), HttpMethod.GET, httpEntity, PageFormatter.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void getByIDshouldReturn200(){
        News newsEntity = buildEntity();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.of(newsEntity));;
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + "/" + ID_NEWS), HttpMethod.GET, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByIDshouldReturn403(){
        News newsEntity = buildEntity();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.of(newsEntity));;
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<NewsDTO> response = testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + "/" + ID_NEWS), HttpMethod.GET, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void save_shouldReturn201() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<NewsDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/news"), HttpMethod.POST, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(newsDTO.getName(), responseEntity.getBody().getName());
        Assertions.assertEquals(newsDTO.getContent(), responseEntity.getBody().getContent());
    }

    @Test
    @WithMockUser
    public void save_shouldReturn401() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        ResponseEntity<NewsDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort("/news"), HttpMethod.POST, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void save_shouldReturn403() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<NewsDTO> responseEntity = testRestTemplate.exchange(generateUriWithPort(NEWS_PATH), HttpMethod.POST, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    void update_shouldReturn200() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<NewsDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.PUT, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser
    void update_shouldReturn401() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        ResponseEntity<NewsDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.PUT, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void update_shouldReturn403() {
        News newsEntity = buildEntity();
        NewsDTO newsDTO = buildDTO();
        when(newsRepository.findById(ID_NEWS)).thenReturn(Optional.ofNullable(newsEntity));
        when(newsRepository.save(any(News.class))).thenReturn(newsEntity);
        HttpEntity<NewsDTO> httpEntity = new HttpEntity<>(newsDTO, httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<NewsDTO> responseEntity =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.PUT, httpEntity, NewsDTO.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void delete_shouldReturn204() {
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        ResponseEntity<PageFormatter> response =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.DELETE, httpEntity, PageFormatter.class);
        verify(newsRepository, times(1)).deleteById(ID_NEWS);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @WithMockUser
    public void delete_shouldReturn401() {
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PageFormatter> response =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.DELETE, httpEntity, PageFormatter.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void delete_shouldReturn403() {
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_USER");
        ResponseEntity<PageFormatter> response =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + ID_NEWS.toString()), HttpMethod.DELETE, httpEntity, PageFormatter.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    void getAllComments_shouldReturn200() throws Exception {
        News newsEntity = buildEntity();
        List<Comments> listComment= new ArrayList<>(List.of());
        List<CommentForNewsDTO> commentForNewsDTOS = commentsMapper.listComment2listCommentForNewsDTO(listComment);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        putTokenInHeader("ROLE_ADMIN");
        when(commentService.findAllByNewsId(ID_NEWS)).thenReturn(commentForNewsDTOS);
        ResponseEntity<CommentForNewsDTO> response =
                testRestTemplate.exchange(generateUriWithPort(NEWS_PATH + "comments/" + ID_NEWS.toString()), HttpMethod.GET, httpEntity, CommentForNewsDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
