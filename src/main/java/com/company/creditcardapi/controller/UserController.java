package com.company.creditcardapi.controller;

import com.company.creditcardapi.models.User;
import com.company.creditcardapi.service.UserServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserServiceLayer service;

    //
    // User basic CRUD API
    //
    //@PostMapping is equivalent to @RequestMapping(value = "/user", method = RequestMethod.POST) - same for the others
    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return service.addNewUser(user);
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return service.getUserById(id);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
    }

}
