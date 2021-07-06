package com.example.blogapi.repository;

import com.example.blogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findPostById(Long postId);
    Post findPostByIdAndPersonId(Long postId, Long personId);
    Post findPostByTitleAndBody(String title, String body);
    List<Post> findAllByStatusIsOrderById(String status);
    List<Post> findPostByPersonId(Long id);
    Post findPostByStatusAndId(String status, Long id);


}
