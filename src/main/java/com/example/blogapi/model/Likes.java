package com.example.blogapi.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Likes {

    @Id
    @GeneratedValue(
            strategy  = IDENTITY
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
}
