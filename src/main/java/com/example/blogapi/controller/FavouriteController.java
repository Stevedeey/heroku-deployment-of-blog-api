package com.example.blogapi.controller;

import com.example.blogapi.dto.ResponseMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> saveFavourite(@PathVariable Long postId, HttpSession httpSession){
        ResponseMapper res = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        if(favouriteService.saveFavourite(person, postId)){
            res.setMessage("successfully added to favourites");
            res.setStatuscode(201);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }else {
            res.setMessage("Couldn't add to favourites");
            res.setStatuscode(400);

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserFavourites(@PathVariable Long userId, HttpSession httpSession){

        ResponseMapper res = new ResponseMapper();

        Person person = (Person) httpSession.getAttribute("person");

        if(person == null) {
            res.setMessage("User not login");
            res.setStatuscode(401);
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(favouriteService.getFavouritesByUser(person), HttpStatus.OK);
    }

}
