package com.example.demo.service.impl;

import com.example.demo.model.mongodb.User;
import com.example.demo.repository.mongodb.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with the same ID already exists.");
        }
        return userRepository.insert(user);
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override  
    public void updateName(String id, String name) {
        userRepository.updateName(id, name);
    }

    @Override
    public void updateAge(String id, int age) {
        userRepository.updateAge(id, age);
    }
}