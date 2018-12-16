package com.cloud.handler;

import com.cloud.cm.ErrorCode;
import com.cloud.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * 包装失败信息到PigDeniedException
 */
@Component
public class DomainAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 403 没有权限， 拒绝处理错误改成中文输出
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        response.setCharacterEncoding(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setContentType(StandardCharsets.UTF_8.name());
        response.setStatus(ErrorCode.Sys.noAuth.getCode());
        R<String> result = new R<>(new AccessDeniedException(ErrorCode.Sys.noAuth.getName()));
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
