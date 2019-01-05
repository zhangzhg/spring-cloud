package com.cloud.mapper;

import com.cloud.model.SysUser;
import org.springframework.data.domain.Page;

public interface SysUserMapper {
    Page<SysUser> listUser();
}
