package com.iuh.IUHStudent.controller;

import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = {"", "/"})
    public List<User> getListUser() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping(path = {"", "/"}, consumes = "application/json", produces = "application/json")
    public User addNewUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

}
