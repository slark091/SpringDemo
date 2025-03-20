package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.model.mongodb.User;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDto> mongoSave(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> mongoUpdate(@PathVariable String id, @RequestBody String username) {
        userService.updateName(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> mongoGetAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(UserDto::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> mongoGet(@PathVariable String id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new UserDto(user));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> mongoDelete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
