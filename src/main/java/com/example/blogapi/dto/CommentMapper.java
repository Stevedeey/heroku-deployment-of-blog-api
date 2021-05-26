package com.example.blogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentMapper {
    private Long postId;
    private Long id;
    private String username;
    private String name;
    private String comment;
    private String title;
    private String image;
    private Long userId;
}
