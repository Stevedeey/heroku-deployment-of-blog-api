package com.example.blogapi.model;

import lombok.Data;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(
            strategy  = IDENTITY
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "comment",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String comment;


    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
}
