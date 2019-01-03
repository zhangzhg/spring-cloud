package com.cloud.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

public interface IUserDetailService {
    Map<String, Collection<ConfigAttribute>> loadAllResource();
}
