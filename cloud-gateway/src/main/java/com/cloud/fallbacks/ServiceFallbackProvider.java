package com.cloud.fallbacks;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * zuul降级异常统一处理
 */
@Component
public class ServiceFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                StringBuilder builder = new StringBuilder("服务故障，请稍后重试!\n")
                        .append("[")
                        .append(route)
                        .append("]:\n")
                        .append(cause.getMessage());
                return new ByteArrayInputStream(builder.toString().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    
	}

}
