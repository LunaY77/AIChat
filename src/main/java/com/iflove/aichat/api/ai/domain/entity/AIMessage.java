package com.iflove.aichat.api.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ai消息表
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-06
 */
@Getter
@Setter
@TableName("ai_message")
@Schema(description = "ai消息表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * @see com.iflove.aichat.api.ai.domain.enums.AIMessageTypeEnum
     */
    @Schema(description = "ai消息类型(0 user / 1 assistant / 2 system)")
    @TableField("message_type")
    private Integer messageType;

    @Schema(description = "ai消息内容")
    @TableField("content")
    private String content;

    @Schema(description = "ai消息会话id")
    @TableField("session_id")
    private String sessionId;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
