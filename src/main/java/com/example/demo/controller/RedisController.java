package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RedisService;


@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;
    
    @PostMapping("/keys")
    public String redisSetValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value: (" + key + ") set";
    }
    
    @GetMapping("/keys/{key}")
    public String redisGetValue(@PathVariable String key) {
        Object value = redisService.getValue(key);
        if (value == null) {
            return "Value: (" + key + ") not found";
        }   
        return value.toString();
    }
    
    @DeleteMapping("/keys/{key}")
    public String redisDeleteValue(@PathVariable String key) {
        redisService.deleteValue(key);
        return "Value: (" + key + ") deleted";
    }

}
