package com.example.blogapi.service;

import com.example.blogapi.dto.PersonMapper;
import com.example.blogapi.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {
    String createUser(Person person);
    String loginUser(String email, String password);
    List<PersonMapper> getUsers();
    PersonMapper getUserById(Long id);
    String updateImage(MultipartFile file, Long personId, Person user);
    boolean deleteUser(Long personId, Person person);
    void deactivatedPersonScheduler();
    String reverseDelete(Person pers, Long personId);
}
