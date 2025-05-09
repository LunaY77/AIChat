package com.iflove.aichat.common.interceptor;

import com.iflove.aichat.common.domain.dto.RequestInfo;
import com.iflove.aichat.common.utils.RequestHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 信息收集拦截器
 */
@Component
@Slf4j
@Order(1)
public class CollectorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("{}", request.getCookies());
        // 收集请求信息
        RequestInfo requestInfo = (RequestInfo) request.getAttribute(TokenInterceptor.ATTRIBUTE_USER_INFO);
        RequestHolder.set(requestInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
