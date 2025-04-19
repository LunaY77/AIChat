package com.iflove.aichat.api.ai.dao;

import com.iflove.aichat.api.ai.domain.entity.AIMessage;
import com.iflove.aichat.api.ai.domain.enums.AIMessageTypeEnum;
import com.iflove.aichat.api.ai.domain.vo.request.message.AIMessageReq;
import com.iflove.aichat.common.exception.AIErrorEnum;
import com.iflove.aichat.common.exception.BusinessException;
import com.iflove.aichat.common.utils.RequestHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Service
@RequiredArgsConstructor
public class AIMessageChatMemory implements ChatMemory {

    private final AIMessageDao aiMessageDao;

    /**
     * 将AIMessageReq转换为SpringAIMessage
     *
     * @param input AIMessageReq
     * @return {@link Message}
     */
    public static Message toSpringAIMessage(AIMessageReq input) {
        return convertMessage(input.getMessageType(), input.getContent());
    }

    /**
     * 将AIMessage转换为SpringAIMessage
     *
     * @param input AIMessage
     * @return {@link Message}
     */
    public static Message toSpringAIMessage(AIMessage input) {
        return convertMessage(input.getMessageType(), input.getContent());
    }

    /**
     * 转换为SpringAIMessage
     *
     * @param messageType 消息类型
     * @param content     消息内容
     * @return {@link Message}
     */
    private static Message convertMessage(Integer messageType, String content) {
        switch (AIMessageTypeEnum.of(messageType)) {
            case USER -> {
                return new UserMessage(content);
            }
            case SYSTEM -> {
                return new SystemMessage(content);
            }
            case ASSISTANT -> {
                return new AssistantMessage(content);
            }
            default -> throw new BusinessException(AIErrorEnum.ILLEGAL_MESSAGE_TYPE);
        }
    }

    /**
     * 保存消息到数据库
     *
     * @param sessionId 会话id
     * @param messages  消息列表
     */
    @Override
    @Transactional
    public void add(String sessionId, List<Message> messages) {
        aiMessageDao.saveBatch(messages
                .stream()
                .map(m -> AIMessage.builder()
                        .userId(RequestHolder.get().getUserId())
                        .sessionId(sessionId)
                        .content(m.getText())
                        .messageType(AIMessageTypeEnum.getType(m.getMessageType()))
                        .build()
                )
                .toList()
        );
    }

    /**
     * 查询会话内的消息最新n条历史记录
     *
     * @param sessionId 会话id
     * @param lastN     最近n条
     * @return {@link List<Message>}
     */
    @Override
    public List<Message> get(String sessionId, int lastN) {
        return aiMessageDao
                .getPageBySessionId(sessionId, lastN)
                .getRecords()
                .stream()
                .map(AIMessageChatMemory::toSpringAIMessage)
                .toList();
    }

    /**
     * 清除会话内的消息
     *
     * @param sessionId 会话id
     */
    @Override
    public void clear(String sessionId) {
        aiMessageDao.deleteBySessionId(sessionId);
    }
}
