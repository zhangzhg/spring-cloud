package com.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /**
     * 认证页面
     * @return ModelAndView
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
