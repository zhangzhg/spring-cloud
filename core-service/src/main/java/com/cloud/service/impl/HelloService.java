package com.cloud.service.impl;

import com.cloud.mapper.SysUserMapper;
import com.cloud.model.SysUser;
import com.cloud.service.IHelloService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService implements IHelloService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public String hello(String name) {
        return "hello "+name+"，this is first messge";
    }

    @Override
    public List<SysUser> listUser() {
        PageRequest pageable = PageRequest.of(1, 1);
        Page<SysUser> page = sysUserMapper.listUser(pageable);
        return page.getContent();
    }
}
