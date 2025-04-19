package com.iflove.aichat.api.ai.domain.vo.request.session;

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
@Schema(description = "AI会话名称修改请求")
@AllArgsConstructor
@NoArgsConstructor
public class AISessionNameChangeReq {

    @Schema(description = "AI会话ID")
    @NotNull(message = "AI会话ID不能为空")
    private String sessionId;

    @Schema(description = "AI会话名称")
    @NotBlank(message = "AI会话名称不能为空")
    private String name;
}
