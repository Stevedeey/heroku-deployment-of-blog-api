package com.example.blogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int personDeactivated;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int isDeleted;

    @Column(name = "removeDate")
    private String removeDate;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String profilePicture;

    @OneToMany(mappedBy = "person", fetch
            = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> post;


}
