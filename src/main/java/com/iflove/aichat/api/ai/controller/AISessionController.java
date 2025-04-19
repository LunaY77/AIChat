package com.iflove.aichat.api.ai.controller;

import com.iflove.aichat.api.ai.domain.entity.AISession;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionCreateReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionDeleteReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionNameChangeReq;
import com.iflove.aichat.api.ai.domain.vo.response.AISessionInfoResp;
import com.iflove.aichat.api.ai.service.AISessionService;
import com.iflove.aichat.common.domain.vo.request.PageBaseReq;
import com.iflove.aichat.common.domain.vo.response.PageBaseResp;
import com.iflove.aichat.common.domain.vo.response.RestBean;
import com.iflove.aichat.common.utils.RequestHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * ai会话表 前端控制器
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/capi/ai/session")
@Tag(name = "AI会话接口")
@Slf4j
@RequiredArgsConstructor
public class AISessionController {

    private final AISessionService aiSessionService;

    /**
     * 根据id查询会话
     *
     * @param id 会话id
     * @return 会话信息
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "根据id查询会话",
            description = "根据id查询会话"
    )
    public RestBean<AISession> findById(@PathVariable String id) {
        return RestBean.success(aiSessionService.findById(id));
    }

    /**
     * 创建会话
     *
     * @param req 会话创建请求
     * @return 会话创建响应
     */
    @PostMapping()
    @Operation(
            summary = "创建会话",
            description = "创建会话"
    )
    public RestBean<AISessionInfoResp> createSession(@RequestBody @Valid AISessionCreateReq req) {
        return RestBean.success(aiSessionService.createSession(req, RequestHolder.get().getUserId()));
    }

    /**
     * 修改会话名称
     *
     * @param req 会话名称修改请求
     */
    @PostMapping("/name")
    @Operation(
            summary = "修改会话名称",
            description = "修改会话名称"
    )
    public RestBean<Void> changeName(@RequestBody @Valid AISessionNameChangeReq req) {
        aiSessionService.changeName(req, RequestHolder.get().getUserId());
        return RestBean.success();
    }

    /**
     * 分页查询会话
     *
     * @param req 分页请求
     * @return 分页响应
     */
    @GetMapping("/page")
    @Operation(
            summary = "根据用户查询会话",
            description = "根据用户查询会话"
    )
    public RestBean<PageBaseResp<AISessionInfoResp>> findByUser(@Valid PageBaseReq req) {
        return RestBean.success(aiSessionService.findByUser(req, RequestHolder.get().getUserId()));
    }

    /**
     * 删除会话
     *
     * @param req 会话删除请求
     */
    @DeleteMapping()
    @Operation(
            summary = "批量删除会话",
            description = "批量删除会话"
    )
    public RestBean<Void> deleteSession(@RequestBody @Valid AISessionDeleteReq req) {
        aiSessionService.deleteSession(req, RequestHolder.get().getUserId());
        return RestBean.success();
    }

}
