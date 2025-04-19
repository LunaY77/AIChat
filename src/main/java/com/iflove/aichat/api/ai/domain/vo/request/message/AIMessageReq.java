package com.iflove.aichat.api.ai.domain.vo.request.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AI消息请求")
public class AIMessageReq {

    @NotBlank
    @Schema(description = "ai消息内容")
    private String content;

    /**
     * @see com.iflove.aichat.api.ai.domain.enums.AIMessageTypeEnum
     */
    @NotNull
    @Schema(description = "ai消息类型(0 user / 1 assistant / 2 system)")
    private Integer messageType;

    @NotNull
    @Schema(description = "ai消息会话id")
    private String sessionId;

    @Schema(description = "是否开启向量数据库")
    private Boolean enableVectorStore;

    @Schema(description = "是否开启Agent模式")
    private Boolean enableAgent;
}
