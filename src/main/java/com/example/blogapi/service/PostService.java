package com.example.blogapi.service;

import com.example.blogapi.dto.PostMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    boolean createPost(MultipartFile file, Person user, String body, String title);
    Post getPostById(Long postId);
    String updatePost(Person person, Post post);
    String deletePost(Long postId, Person person);
    List<PostMapper> getPost(Person currentUser);
    List<Post> displayAllPostByFollowee(Long id, Person person);
}
