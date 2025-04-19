package com.iflove.aichat.api.ai.domain.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Schema(description = "AI Session信息响应")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AISessionInfoResp {

    @Schema(description = "会话id")
    private String id;

    @Schema(description = "会话名称")
    private String name;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
