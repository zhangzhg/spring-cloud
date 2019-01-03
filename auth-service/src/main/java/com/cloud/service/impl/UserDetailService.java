package com.cloud.service.impl;

import com.cloud.domain.SysUser;
import com.cloud.model.dto.KeyValuePair;
import com.cloud.repository.SysUserRepository;
import com.cloud.repository.mybatis.AuthDAO;
import com.cloud.service.IUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserDetailService implements IUserDetailService,UserDetailsService {
    Logger log = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private AuthDAO authDAO;

    /**
     * 这边登录的时候会加载
     * 加载权限是 在 controller里面用
     * hasRole/hasAuthority的时候有效
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowcaseUsername = username.toLowerCase();
        Optional<SysUser> realUser = sysUserRepository.findOneWithRolesByUsername(lowcaseUsername);

        return realUser.map(user -> {
            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities();
            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!"));
    }

    /**
     * spring security 资源权限
     */
    @Override
    public Map<String, Collection<ConfigAttribute>> loadAllResource() {
        List<KeyValuePair> list = authDAO.findUrlRole();
        Map<String, List<KeyValuePair>> map = list.stream().collect(Collectors.groupingBy(o->o.getK()));
        final Map<String, Collection<ConfigAttribute>> result = new HashMap<>();
        map.forEach((k, v)->{
            List<ConfigAttribute> items = new ArrayList<>();
            for (KeyValuePair pair : v) {
                ConfigAttribute attribute = new SecurityConfig(pair.getV());
                items.add(attribute);
            }
            result.put(k, items);
        });

        return result;
    }
}
