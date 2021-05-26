package com.example.blogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavouriteMapper {
    private Long postId;
    private String postTitle;
    private String postBody;
}
