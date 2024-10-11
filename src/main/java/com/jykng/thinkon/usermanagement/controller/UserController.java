package com.jykng.thinkon.usermanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jykng.thinkon.usermanagement.entity.User;
import com.jykng.thinkon.usermanagement.exception.UserNotFoundException;

import java.util.List;
import com.jykng.thinkon.usermanagement.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




/*
 * You are to create a REST API for managing users. Your service will be responsible for the following:
• Creating new users
• Listing all available users
• Listing a single user
• Updating an existing user
• Deleting an existing user

Exposing an API | Your service should present the following API endpoint:
• POST /users - create a new user
• GET /users - get a list of users
• GET /users/{id}
• PUT /users/{id}
• DELETE /users/{id}
 */
@RestController
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public User getOne(@PathVariable int id){
        return userRepository.findById(id)
        .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping(path="/{id}", produces = "application/json", consumes = "application/json")
    public User update(HttpServletResponse response, @RequestBody User user, @PathVariable int id){
        return userRepository.findById(id)
        .map(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(u);
        }).orElseGet(()->{ //create user instead
            response.setStatus(HttpServletResponse.SC_CREATED); //because something is created
            return userRepository.save(user);
        });
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        userRepository.deleteById(id);
    }
}
