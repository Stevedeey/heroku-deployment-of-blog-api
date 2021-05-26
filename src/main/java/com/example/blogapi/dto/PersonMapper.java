package com.example.blogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonMapper {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String profilePicture;
}