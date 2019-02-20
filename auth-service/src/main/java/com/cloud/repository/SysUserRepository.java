package com.cloud.repository;

import com.cloud.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    List<SysUser> findByUsername(String username);
}
