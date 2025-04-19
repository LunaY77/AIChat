package com.iflove.aichat.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@AllArgsConstructor
@Getter
public enum AIErrorEnum implements ErrorEnum {
    ILLEGAL_MESSAGE_TYPE(50001, "不支持的ai消息类型"),
    SESSION_NOT_FOUND(50002, "会话不存在"),
    ;

    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
