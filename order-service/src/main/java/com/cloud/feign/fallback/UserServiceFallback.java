package com.cloud.feign.fallback;

import com.cloud.feign.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceFallback implements IUserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Principal getUser() {
        log.error("获取用户接口异常");
        return null;
    }
}
