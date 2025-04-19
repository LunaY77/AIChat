package com.iflove.aichat.api.ai.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.ai.chat.messages.MessageType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@AllArgsConstructor
@Getter
public enum AIMessageTypeEnum {
    USER(0, MessageType.USER),
    ASSISTANT(1, MessageType.ASSISTANT),
    SYSTEM(2, MessageType.SYSTEM),
    ;

    private final Integer type;
    private final MessageType desc;

    private static Map<Integer, MessageType> cache1;
    private static Map<MessageType, Integer> cache2;

    static {
        cache1 = Arrays.stream(AIMessageTypeEnum.values()).collect(Collectors.toMap(AIMessageTypeEnum::getType, AIMessageTypeEnum::getDesc));
        cache2 = Arrays.stream(AIMessageTypeEnum.values()).collect(Collectors.toMap(AIMessageTypeEnum::getDesc, AIMessageTypeEnum::getType));
    }

    public static MessageType of(Integer type) {
        return cache1.getOrDefault(type, null);
    }

    public static Integer getType(MessageType messageType) {
        return cache2.getOrDefault(messageType, null);
    }
}
