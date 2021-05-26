package com.example.blogapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @NotBlank
    @Size(max=1000)
    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String image;

    @Column()
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;
}
