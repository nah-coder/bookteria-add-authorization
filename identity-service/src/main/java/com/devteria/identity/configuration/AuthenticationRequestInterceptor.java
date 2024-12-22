package com.devteria.identity.configuration;

import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (servletRequestAttributes == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String auth = servletRequestAttributes.getRequest().getHeader("Authorization");
        if (auth == null || auth.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if(StringUtils.hasText(auth))
            requestTemplate.header("Authorization", auth);
    }
}
