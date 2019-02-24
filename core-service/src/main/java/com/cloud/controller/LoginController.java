package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response, @RequestParam String code) {
        logger.info("request one  name is "+code);
        return code;
    }
}
