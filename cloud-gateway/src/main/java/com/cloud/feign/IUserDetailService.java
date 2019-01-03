package com.cloud.feign;

import com.cloud.feign.impl.UserDetailFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Map;

@FeignClient(name = "auth-server", fallback = UserDetailFallback.class)
public interface IUserDetailService {
    @GetMapping("/resources")
    Map<String,Collection<ConfigAttribute>> loadAllResource();
}
