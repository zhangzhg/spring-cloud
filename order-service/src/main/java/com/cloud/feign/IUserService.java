package com.cloud.feign;

import com.cloud.feign.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * 降级熔断测试
 */
@FeignClient(name = "/auth-server", fallback = UserServiceFallback.class)
public interface IUserService {
    @GetMapping("/user")
    Principal getUser();
}
