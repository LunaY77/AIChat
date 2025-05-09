package com.iflove.aichat.common.config.interceptor;


import com.iflove.aichat.common.interceptor.CollectorInterceptor;
import com.iflove.aichat.common.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final CollectorInterceptor collectorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
//                .order(Const.INTERCEPTOR_ORDER_FIRST)
                .addPathPatterns("/capi/**").excludePathPatterns("/capi/**/public/**", "/capi/user/logout");
        registry.addInterceptor(collectorInterceptor)
//                .order(Const.INTERCEPTOR_ORDER_SECOND)
                .addPathPatterns("/capi/**");
    }
}
