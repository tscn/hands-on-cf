package com.github.tscn.cf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

public class MessageStore {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    public void setMessage(String message) {
        valueOps.set("message", message);
    }

    public String getMessage() {
        return valueOps.get("message");
    }
}
