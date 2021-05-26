package com.example.blogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "comment",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank
    @Size(min = 3)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;

    @ManyToOne

    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
}
