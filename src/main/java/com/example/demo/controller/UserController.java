package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RedisService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisService redisService;

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value: (" + key + ") set";
    }
    
    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        Object value = redisService.getValue(key);
        if (value == null) {
            return "Value: (" + key + ") not found";
        }   
        return value.toString();
    }
    
    @DeleteMapping("/delete")
    public String deleteValue(@RequestParam String key) {
        redisService.deleteValue(key);
        return "Value: (" + key + ") deleted";
    }
}
