package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Comments;
import com.alkemy.ong.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, UUID> {

    List<Comments> findAllByOrderByTimestampAsc();
    Optional<List<Comments>> findByNews(News news);
}
