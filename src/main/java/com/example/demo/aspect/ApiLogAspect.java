package com.example.demo.aspect;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class ApiLogAspect {
    @Autowired
    private KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    // 拦截所有 Controller 的公共方法
    @Around("execution(* com.example.demo.controller.*.*(..))")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        String errorMsg = null;

        try {
            result = joinPoint.proceed(); // 执行原方法
        } catch (Exception e) {
            errorMsg = e.getMessage();
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - startTime;
            sendLogToKafka(buildLogMessage(joinPoint, result, cost, errorMsg));
        }
        return result;
    }

    private JsonNode buildLogMessage(ProceedingJoinPoint joinPoint, Object result, long cost, String errorMsg) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", Instant.now().toString());
        logData.put("method", joinPoint.getSignature().getName());
        logData.put("args", Arrays.toString(joinPoint.getArgs()));
        logData.put("result", result);
        logData.put("cost_ms", cost);
        logData.put("error", errorMsg);
        JsonNode log = new ObjectMapper().valueToTree(logData);
        return log;
    }

    private void sendLogToKafka(JsonNode message) {
        kafkaTemplate.send(topic, message);
    }
}
