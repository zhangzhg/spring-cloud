package com.cloud.security;

import com.cloud.mapper.UserDetailMapper;
import com.cloud.model.SysAuthority;
import com.cloud.model.SysUser;
import com.cloud.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("============================loadUserByUsername  "+username);
        String lowcaseUsername = username.toLowerCase();
        List<SysUser> users = sysUserRepository.findByUsername(lowcaseUsername);

        if (users.size() == 0) {
            throw new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!");
        }

        if (users.size() > 1) {
            throw new UsernameNotFoundException("用户" + lowcaseUsername + "，用户名重复!");
        }

        List<SysAuthority> authorityList = userDetailMapper.listAuthority(username);
        Set<GrantedAuthority> userAuthorities = new HashSet<>();
        authorityList.forEach( authority -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            userAuthorities.add(grantedAuthority);
        });

        SysUser user = users.get(0);
        return new User(user.getUsername(),user.getPassword(), userAuthorities);
    }
}
