package com.example.demo.service;

import com.example.demo.model.mongodb.User;

public interface UserService {
    User create(User user);
    User save(User user);
    void delete(String id);
    User findById(String id);
    void updateName(String id, String name);
    void updateAge(String id, int age);
}