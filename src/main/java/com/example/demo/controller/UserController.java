package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.model.mongodb.User;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @PostMapping("/redis/set")
    public String redisSetValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value: (" + key + ") set";
    }
    
    @GetMapping("/redis/get")
    public String redisGetValue(@RequestParam String key) {
        Object value = redisService.getValue(key);
        if (value == null) {
            return "Value: (" + key + ") not found";
        }   
        return value.toString();
    }
    
    @DeleteMapping("/redis/delete")
    public String redisDeleteValue(@RequestParam String key) {
        redisService.deleteValue(key);
        return "Value: (" + key + ") deleted";
    }

    @PostMapping("/mongo/save")
    public ResponseEntity<UserDto> mongoSave(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PutMapping("/mongo/users/{id}")
    public ResponseEntity<Void> mongoUpdate(@PathVariable String id, @RequestBody String username) {
        userService.updateName(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mongo/get")
    public ResponseEntity<UserDto> mongoGet(@RequestParam String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new UserDto(user));
        }
    }
    
    @DeleteMapping("/mongo/delete")
    public ResponseEntity<UserDto> mongoDelete(@RequestBody String userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }    
    
}
