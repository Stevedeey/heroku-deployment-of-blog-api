package com.example.blogapi.service;

import com.example.blogapi.dto.CommentMapper;
import com.example.blogapi.model.Person;

import java.util.List;

public interface CommentService {
    boolean createComment(Long userId, Long postId, String comment);
    List<CommentMapper> getCommentsByPostId(Long postId);
    boolean editComment(Long commentId, Person person, Long postId, String comment);
    boolean deleteComment(Long commentId);
}
