package com.iflove.aichat.api.ai.service;

import com.iflove.aichat.api.ai.domain.entity.AIMessage;
import com.iflove.aichat.api.ai.domain.vo.request.message.AIMessageReq;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * <p>
 * ai消息表 服务类
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-06
 */
public interface AIMessageService {

    Flux<ServerSentEvent<String>> chatStream(AIMessageReq req);

    void deleteHistory(String sessionId);

    String chatBlocking(AIMessageReq req);

    List<AIMessage> getHistory(String sessionId);
}
