package com.cloud.service.impl;

import com.cloud.mapper.SysUserMapper;
import com.cloud.model.SysUser;
import com.cloud.repository.SysUserRepository;
import com.cloud.service.IHelloService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService implements IHelloService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserRepository sysUserRepository;


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

    public void saveUser() {
        SysUser user = new SysUser();
        user.setUsername("test");
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode("test");
        user.setPassword(hashPass);
        sysUserRepository.save(user);
        throw new RuntimeException("xxx");
    }

    public List<SysUser> listUserPage() {
        SysUser user = new SysUser();
        user.setUsername("1111");
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode("test");
        user.setPassword(hashPass);
        sysUserRepository.save(user);
        return new ArrayList<>();
    }

}
