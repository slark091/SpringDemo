package com.example.demo.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.demo.model.mongodb.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'name': ?1 } }")
    void updateName(String id, String name);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'age': ?1 } }")
    void updateAge(String id, int age);
}
