package com.example.blogapi.controller;

import com.example.blogapi.dto.LikeMapper;
import com.example.blogapi.dto.ResponseMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.service.LikeService;
import com.example.blogapi.service.serviceImplementation.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/")
public class LikesController {
    @Autowired
    LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<ResponseMapper> likePost(@RequestBody LikeMapper likesMapper, HttpSession httpSession){

        ResponseMapper responseHandler = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            responseHandler.setMessage("User not login");
            responseHandler.setStatuscode(401);

            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }

        System.out.println(likesMapper);

        if(likeService.likePost(person, likesMapper.getPostId(), likesMapper.getAction())){
            responseHandler.setMessage("successful");
            responseHandler.setStatuscode(204);

            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }else {
            responseHandler.setMessage("Server Error");
            responseHandler.setStatuscode(500);

            return new ResponseEntity<>(responseHandler, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
