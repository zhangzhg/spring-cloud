package com.cloud.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
//@SessionAttributes("authorizationRequest")
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
    @RequestMapping("/login/page")
    public ModelAndView loginPage(boolean error) {
        ModelAndView mv = new ModelAndView("/ftl/login");
        mv.addObject("param", error);
        return mv;
    }

    @RequestMapping("/login/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView("ftl/assess");
        view.addObject("clientId", authorizationRequest.getClientId());
        return view;
    }
}
