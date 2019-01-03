package com.cloud.feign.impl;

import com.cloud.feign.IUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class UserDetailFallback implements IUserDetailService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public Map<String,Collection<ConfigAttribute>> loadAllResource() {
        log.error("读取权限数据失败，检查auth-server是否开启");
        return null;
    }
}
