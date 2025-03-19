package com.example.demo.dto;

import com.example.demo.model.mongodb.User;

public record UserDto(String id, String name, int age) {
    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getAge());
    }
}