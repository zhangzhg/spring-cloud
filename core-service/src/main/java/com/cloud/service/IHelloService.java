package com.cloud.service;

import com.cloud.model.SysUser;

import java.util.List;

public interface IHelloService {
    String hello(String name);
    List<SysUser> listUser();
}
