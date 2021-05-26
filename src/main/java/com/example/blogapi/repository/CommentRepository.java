package com.example.blogapi.repository;

import com.example.blogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CommentRepository  extends JpaRepository<Comment, Long> {
     List<Comment> findAllByPostId(Long postId);
}
