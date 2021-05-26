package com.example.blogapi.service;

import com.example.blogapi.model.Person;

public interface LikeService {
    boolean likePost(Person person, Long postId, String action);
}
