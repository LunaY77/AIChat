package com.iflove.aichat.api.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iflove.aichat.api.user.domain.entity.User;
import com.iflove.aichat.api.user.domain.vo.request.UserLoginRequest;
import com.iflove.aichat.api.user.domain.vo.request.UserRegisterRequest;
import com.iflove.aichat.api.user.domain.vo.response.LoginInfoResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author cangjingyue
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2025-04-07 20:53:18
 */
public interface UserService extends IService<User> {

    void register(UserRegisterRequest registerRequest);

    // 修改login方法签名
    LoginInfoResponse login(UserLoginRequest userLoginRequest, HttpServletResponse response);
    
    // 修改refreshToken方法签名
    String refreshToken(String refreshToken, HttpServletResponse response);

    void logout(String refreshToken);
}
