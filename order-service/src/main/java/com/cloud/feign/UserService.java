package com.cloud.feign;

import com.cloud.feign.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/**
 * 降级熔断测试
 */
@FeignClient(name = "order", fallback = UserServiceFallback.class)
public interface UserService {
    @GetMapping("/hello")
    String getHello(@RequestParam("name") String name);
}
