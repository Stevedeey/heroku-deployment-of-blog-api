package com.example.blogapi.repository;

import com.example.blogapi.model.Likes;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    void deleteLikesByPostAndPerson(Post post, Person person);
    List<Likes> findAllByPostId(Long postId);
    List<Likes> findAllByPostIdAndPersonId(Long postId, Long personId);
}
