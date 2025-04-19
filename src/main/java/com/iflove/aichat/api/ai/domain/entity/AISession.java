package com.iflove.aichat.api.ai.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ai会话表
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-06
 */
@Getter
@Setter
@TableName("ai_session")
@Schema(description = "ai会话表")
@Builder
public class AISession implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @Schema(description = "会话名称")
    @TableField("name")
    private String name;

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
