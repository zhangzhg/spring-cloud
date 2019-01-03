package com.cloud.repository;

import com.cloud.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    Optional<SysUser> findOneWithRolesByUsername(String username);
}
