package com.example.blogapi.controller;

import com.example.blogapi.dto.PersonMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.service.PersonService;
import com.example.blogapi.dto.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PersonController {
    @Autowired
    PersonService personService;
    @PostMapping("/register")
    public Object createUser(@RequestBody Person person){

        String message = personService.createUser(person);
        //http server response
        System.out.println("Hello");
        ResponseMapper res = new ResponseMapper();
        if(message == "success"){
            res.setStatuscode(201);
            res.setMessage(message);
        }else{
            res.setMessage(message);
            if(message.equals("failed")) { //user exist already
                res.setStatuscode(409);
                res.setMessage("Email already exist");
            }else{
                res.setStatuscode(403);
                res.setMessage(message); //this means the person was not validated
            }
        }
        return res;
    }

    @PostMapping("/login")
    public ResponseMapper loginUser(@RequestBody Person person, HttpServletRequest req){
        HttpSession httpSession = req.getSession();
        String message = personService.loginUser(person.getEmail(), person.getPassword());
        //http server response
        ResponseMapper res = new ResponseMapper();
        if(message.equals("successful")){
            //put user inside session
            httpSession.setAttribute("person", person);
            res.setStatuscode(200);
            res.setMessage(message);
        }else{
            res.setStatuscode(404);
            res.setMessage(message);
        }
        return res;
    }
    @GetMapping("/")
    public List<PersonMapper> getUsers(){
        return personService.getUsers();
    }
    @GetMapping("/{id}")
    public PersonMapper getUserById(@PathVariable Long id){
        return personService.getUserById(id);
    }
    @PutMapping("/upload_image/{id}")
    public ResponseMapper uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");
        //http server response
        ResponseMapper res = new ResponseMapper();
        if(person == null) {
            res.setStatuscode(403);
            res.setMessage("user not login!!!");
            return res;
        }
        String message = personService.updateImage(file, id, person);
        if(message.equals("successfully uploaded image")){
            res.setStatuscode(201);
            res.setMessage(message);
        }else{
            if(message.equals("user not authorized")) res.setStatuscode(401);
            if(message.equals("Please select an image")) res.setStatuscode(400);
            if(message.equals("failed")) {
                res.setStatuscode(500);
                res.setMessage("Internal Server error");
                return res;
            }
            res.setMessage(message);
        }
        return res;
    }


    @DeleteMapping("delete/{id}")
    public ResponseMapper deleteUser(@PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");
        //http server response
        ResponseMapper res = new ResponseMapper();
        if(person == null) {
            res.setStatuscode(403);
            res.setMessage("user not login!!!");
            return res;
        }
        if(personService.deleteUser(id, person)){
            res.setStatuscode(204);
            res.setMessage("account deletion pending");
        }else{
            res.setStatuscode(401);
            res.setMessage("user not authorized");
        }
        return res;
    }

    @PostMapping("/reverseDelete/{id}")
    public ResponseEntity<?> reverseAccountDeletion(@PathVariable Long id, HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute("person");
        //http server response
        ResponseMapper responseMapper = new ResponseMapper();
        if(person == null) {
            responseMapper.setStatuscode(401);
            responseMapper.setMessage("user not login!!!");
            return new ResponseEntity<>(responseMapper, HttpStatus.UNAUTHORIZED);
        }
        String message = personService.reverseDelete(person, id);
        if(message.equals("successfully reversed")){
            responseMapper.setStatuscode(204);
            responseMapper.setMessage(message);
            return new ResponseEntity<>(responseMapper, HttpStatus.ACCEPTED);
        }else{
            responseMapper.setMessage(message);
            if(message.equals("user not authorized")){
                responseMapper.setStatuscode(401);
                return new ResponseEntity<>(responseMapper, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(responseMapper, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
