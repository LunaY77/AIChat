package com.iflove.aichat.api.user.controller;

import com.iflove.aichat.common.constant.Const;
import com.iflove.aichat.common.domain.vo.response.RestBean;
import com.iflove.aichat.common.utils.RequestHolder;
import com.iflove.aichat.api.user.domain.vo.response.UserInfoResponse;
import com.iflove.aichat.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@RestController
@RequestMapping("/capi/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class UserController {
    private final UserService userService;

    @GetMapping("info/me")
    @Operation(summary = "获取当前用户信息",
            description = "获取当前用户信息",
            security = {@SecurityRequirement(name = "Authorization")}
    )
    public RestBean<UserInfoResponse> getUserInfo() {
        return RestBean.success(UserInfoResponse.builder()
                .id(RequestHolder.get().getUserId())
                .name(RequestHolder.get().getName())
                .build()
        );
    }

    @GetMapping("logout")
    @Operation(summary = "退出登录",
            description = "退出登录",
            security = {@SecurityRequirement(name = "Authorization")}
    )
    public RestBean<Void> logout(@CookieValue(value = Const.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) {
        userService.logout(refreshToken);
        return RestBean.success();
    }
}
