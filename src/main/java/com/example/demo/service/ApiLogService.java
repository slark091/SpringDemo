package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ApiLogService {
    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
