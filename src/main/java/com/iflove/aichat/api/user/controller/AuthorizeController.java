package com.iflove.aichat.api.user.controller;

import com.iflove.aichat.common.constant.Const;
import com.iflove.aichat.common.domain.vo.response.RestBean;
import com.iflove.aichat.api.user.domain.vo.request.UserLoginRequest;
import com.iflove.aichat.api.user.domain.vo.request.UserRegisterRequest;
import com.iflove.aichat.api.user.domain.vo.response.LoginInfoResponse;
import com.iflove.aichat.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@RestController
@RequestMapping("/capi/user/public")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户公共接口")
public class AuthorizeController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public RestBean<LoginInfoResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletResponse response) {
        return RestBean.success(userService.login(userLoginRequest, response));
    }

    @GetMapping("/refreshToken")
    @Operation(summary = "刷新token", description = "刷新token接口")
    public RestBean<String> refreshToken(@CookieValue(value = Const.REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
                                         HttpServletResponse response) {
        return RestBean.success(userService.refreshToken(refreshToken, response));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public RestBean<Void> register(@RequestBody @Valid UserRegisterRequest registerRequest) {
        userService.register(registerRequest);
        return RestBean.success();
    }

}