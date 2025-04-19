package com.iflove.aichat.api.ai.controller;

import com.iflove.aichat.api.ai.domain.entity.AIMessage;
import com.iflove.aichat.api.ai.domain.vo.request.message.AIMessageReq;
import com.iflove.aichat.api.ai.service.AIMessageService;
import com.iflove.aichat.common.domain.vo.response.RestBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.iflove.aichat.api.ai.constant.AIChatConstant.LISTEN_EVENT;


/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@RestController
@RequestMapping("capi/ai/chat")
@Tag(name = "AI聊天接口")
@Slf4j
@RequiredArgsConstructor
public class AIChatController {
    private final AIMessageService aiMessageService;

    // TODO 加上频控
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(
            summary = "ai聊天接口(stream)",
            description = "ai聊天接口，返回聊天结果, 前端通过SSE接收数据, 格式为json，监听事件为 " + LISTEN_EVENT + " (重要！！！)"
    )
    public Flux<ServerSentEvent<String>> chatStream(@RequestBody @Valid AIMessageReq req) {
        return aiMessageService.chatStream(req);
    }

    @PostMapping(value = "block")
    @Operation(
            summary = "ai聊天接口(block)",
            description = "ai聊天接口(block)"
    )
    public RestBean<String> chatBlocking(@RequestBody @Valid AIMessageReq req) {
        return RestBean.success(aiMessageService.chatBlocking(req));
    }

    @GetMapping("/history/{sessionId}")
    @Operation(
            summary = "获取历史记录",
            description = "获取历史记录"
    )
    public RestBean<List<AIMessage>> getHistory(@PathVariable String sessionId) {
        return RestBean.success(aiMessageService.getHistory(sessionId));
    }

    @DeleteMapping("/history/{sessionId}")
    @Operation(
            summary = "删除历史记录",
            description = "删除历史记录"
    )
    public RestBean<Void> deleteHistory(@PathVariable String sessionId) {
        aiMessageService.deleteHistory(sessionId);
        return RestBean.success();
    }
}

