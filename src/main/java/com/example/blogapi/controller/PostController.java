package com.example.blogapi.controller;

import com.example.blogapi.dto.ResponseMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import com.example.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMapper> createPost(@RequestPart("imageFile") MultipartFile file, @RequestPart("body") String body, @RequestPart("title") String title, HttpSession httpSession){

        ResponseMapper res = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        boolean valid = postService.createPost(file, person, body, title);

        if(!valid){
            res.setMessage("Post Already Exist");
            res.setStatuscode(409);
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        res.setMessage("successfully created post");
        res.setStatuscode(201);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getPostById(@PathVariable Long id, HttpSession httpSession){
//        ResponseMapper res = new ResponseMapper();
//
//        Person person = (Person) httpSession.getAttribute("person");
//
//        if(person == null) {
//            res.setMessage("User not login");
//            res.setStatuscode(401);
//            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
//        }
//
//        List<Post> post = postService.getPostById(id);
//
//        return new ResponseEntity<>(post, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostByPostId(@PathVariable Long id, HttpSession httpSession){
        ResponseMapper res = new ResponseMapper();
        Person person = (Person) httpSession.getAttribute("person");
        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<?> getPost(HttpSession httpSession){
        ResponseMapper  responseMapper= new ResponseMapper();
        Person person = (Person) httpSession.getAttribute("person");
        if(person == null) {
            responseMapper.setMessage("User not login");
            responseMapper.setStatuscode(401);
            return new ResponseEntity<>(responseMapper, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(postService.getPost(person), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMapper> getUpdatePost(@PathVariable Long id, @RequestBody Post post, HttpSession httpSession){
        ResponseMapper res = new ResponseMapper();
        Person person = (Person) httpSession.getAttribute("person");
        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        String message = postService.updatePost(person, post);
        res.setMessage(message);
        if(message.equals("successfully updated post")){
            res.setStatuscode(204);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            if(message.equals("user not authorized")) res.setStatuscode(401);
            else res.setStatuscode(400);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMapper> deletePost(@PathVariable Long id, HttpSession httpSession){
        ResponseMapper res = new ResponseMapper();
        Person person = (Person) httpSession.getAttribute("person");
        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        String message = postService.deletePost(id, person);
        res.setMessage(message);
        if(message.equals("successfully deleted post")){
            res.setStatuscode(200);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            if(message.equals("user not authorized")) res.setStatuscode(401);
            else res.setStatuscode(400);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/followeepost/{userId}")
    public ResponseEntity<?> getPostByFollowee(@PathVariable Long userId, HttpSession httpSession){
        ResponseMapper res = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(postService.displayAllPostByFollowee(userId, person), HttpStatus.OK);
    }

}
