package com.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class UserController {

    /**
     * 获取登录用户
     * feign client 获取不到这个，会报错，因为Principal没法序列化
     * 需要改造下
     */
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }


    /**
     * 认证页面
     * @return ModelAndView
     */
    @GetMapping("/oauth/login")
    public ModelAndView loginPage() {
        return new ModelAndView("/ftl/login");
    }

    @PostMapping("/oauth/form")
    public void form() {

    }
}
