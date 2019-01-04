package com.cloud.feign.fallback;

import com.cloud.feign.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceFallback implements UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getHello(String name) {
        log.error("获取用户接口异常");
        return null;
    }
}
