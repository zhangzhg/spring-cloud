package com.cloud.service.impl;

import com.cloud.service.IHelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloService implements IHelloService {

    @Override
    public String hello(String name) {
        return "hello "+name+"，this is first messge";
    }
}
