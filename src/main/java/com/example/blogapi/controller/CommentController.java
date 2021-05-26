package com.example.blogapi.controller;

import com.example.blogapi.dto.CommentMapper;
import com.example.blogapi.dto.ResponseMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMapper> createComment(@RequestBody CommentMapper commentMapper, HttpSession httpSession){

        ResponseMapper responseHandler = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatuscode(401);
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        if(commentService.createComment(commentMapper.getUserId(), commentMapper.getPostId(), commentMapper.getComment())){
            responseHandler.setStatuscode(201);
            responseHandler.setMessage("successfully created comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.CREATED);
        }else {
            responseHandler.setStatuscode(400);
            responseHandler.setMessage("couldn't create comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseMapper> editComment(@PathVariable Long id, @RequestBody CommentMapper commentMapper, HttpSession httpSession){

        ResponseMapper responseHandler = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatuscode(401);
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        if(commentService.editComment(id, person, commentMapper.getPostId(), commentMapper.getComment())){
            responseHandler.setStatuscode(204);
            responseHandler.setMessage("successfully updated comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.ACCEPTED);
        }else{
            responseHandler.setStatuscode(400);
            responseHandler.setMessage("couldn't update comment");

            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }
    }


}
