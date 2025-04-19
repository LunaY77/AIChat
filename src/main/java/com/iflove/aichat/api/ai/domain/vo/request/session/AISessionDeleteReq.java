package com.iflove.aichat.api.ai.domain.vo.request.session;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Data
@Schema(description = "AI会话删除请求")
@AllArgsConstructor
@NoArgsConstructor
public class AISessionDeleteReq {

    @Schema(description = "会话ID列表")
    @Size(min = 1, message = "会话ID列表不能为空")
    private List<String> sessionIds;

}
