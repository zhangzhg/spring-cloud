package com.cloud.security;

import com.cloud.domain.SysUser;
import com.cloud.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("============================loadUserByUsername  "+username);
        String lowcaseUsername = username.toLowerCase();
        Optional<SysUser> realUser = sysUserRepository.findOneWithRolesByUsername(lowcaseUsername);

        return realUser.map(user -> {
            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities();
            log.info("============================map user  "+user.getUsername());
            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!"));
    }
}
