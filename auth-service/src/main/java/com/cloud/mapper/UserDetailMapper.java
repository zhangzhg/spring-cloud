package com.cloud.mapper;

import com.cloud.model.SysAuthority;

import java.util.List;

public interface UserDetailMapper {
    List<SysAuthority> listAuthority(String userName);
}
