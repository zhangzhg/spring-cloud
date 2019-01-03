package com.cloud.security;

import com.cloud.feign.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 获取请求url需要的权限
 * 主要是加载权限
 */
@Component
public class CustomMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
//    @Autowired
//    private PropertySource propertySourceBean;
    @Autowired
    private IUserDetailService userDetailsService;

    private PathMatcher matcher = new AntPathMatcher();

    // 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
    // PostConstruct在构造函数之后执行,init()方法之前执行。
    @PostConstruct
    private void loadResourceDefine() {
        /*
         * 应当是资源为key， 权限为value。 资源通常为url，
		 * 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		 */
        resourceMap = new HashMap<>();//userDetailsService.loadAllResource();
    }

    /**
     * 此方法就是通过url地址获取 角色信息的方法
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前访问url
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        //设置不拦截 白名单
//        if (propertySourceBean.getProperty("ignore.urls") != null) {
//            String[] paths = (String[]) propertySourceBean.getProperty("ignore.urls");
//            List<ConfigAttribute> result = new ArrayList<>();
//            //判断是否符合规则
//            for (String path : paths) {
//                String temp = StringUtil.clearSpace(path);
//                if (matcher.match(temp, url)) {
//                    ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
//                    result.add(attribute);
//                    return result;
//                }
//            }
//        }

        List<ConfigAttribute> result = new ArrayList<>();
        ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
        result.add(attribute);

        if (resourceMap == null) {
            loadResourceDefine();
        }

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (matcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }

        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        HashSet allAttributes = new HashSet();
        Iterator ite = resourceMap.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            allAttributes.addAll((Collection) entry.getValue());
        }

        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}

