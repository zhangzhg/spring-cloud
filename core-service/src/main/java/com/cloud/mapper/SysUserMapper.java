package com.cloud.mapper;

import com.cloud.model.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface SysUserMapper {
    Page<SysUser> listUser(PageRequest pageable);
}
