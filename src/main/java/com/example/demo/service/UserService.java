package com.example.demo.service;

import java.util.List;

import com.example.demo.model.mongodb.User;

public interface UserService {
    User create(User user);
    User save(User user);
    void delete(String id);
    User findById(String id);
    List<User> findAll();
    void updateName(String id, String name);
    void updateAge(String id, int age);
}