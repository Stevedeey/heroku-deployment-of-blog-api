package com.example.blogapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long currentUserId;

    @Column(nullable = false)
    private Long followeeId;


}
