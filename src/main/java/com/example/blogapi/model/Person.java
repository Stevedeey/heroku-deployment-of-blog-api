package com.example.blogapi.model;

import com.example.blogapi.utility.Timer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person extends Timer implements Serializable {
    @Id
    @JsonIgnore
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

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int personDeactivated;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int isDeleted;

    @JsonIgnore
    @Column(name = "removeDate")
    private String removeDate;

    @JsonIgnore
    @Column(columnDefinition = "MEDIUMBLOB")
    private String profilePicture;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch
            = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> post;

    //setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPersonDeactivated() {
        return personDeactivated;
    }

    public void setPersonDeactivated(int personDeactivated) {
        this.personDeactivated = personDeactivated;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", personDeactivated=" + personDeactivated +
                ", isDeleted=" + isDeleted +
                ", removeDate='" + removeDate + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", post=" + post +
                '}';
    }
}
