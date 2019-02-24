package com.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    /**
     * 获取登录用户
     * feign client 获取不到这个，会报错，因为Principal没法序列化
     * 需要改造下
     */
    @RequestMapping("/oauth/user")
    public Principal user(Principal user){
        return user;
    }
}
