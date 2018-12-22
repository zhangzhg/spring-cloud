package com.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * 降级熔断测试
 */
@FeignClient(name = "auth-server")
public interface UserService {
    @GetMapping("/user")
    Principal getUser();
}
