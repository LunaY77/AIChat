package com.iflove.aichat.api.ai.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.aichat.api.ai.domain.entity.AIMessage;
import com.iflove.aichat.api.ai.mapper.AIMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

@Service
public class AIMessageDao extends ServiceImpl<AIMessageMapper, AIMessage> {

    /**
     * 根据sessionId删除AIMessage
     *
     * @param sessionId sessionId
     */
    public void deleteBySessionId(String sessionId) {
        lambdaUpdate()
                .eq(AIMessage::getSessionId, sessionId)
                .remove();
    }

    /**
     * 根据sessionId获取分页数据，查询会话内消息最新n条历史记录
     *
     * @param sessionId sessionId
     * @param lastN     最新n条历史记录
     * @return 分页数据
     */
    public IPage<AIMessage> getPageBySessionId(String sessionId, int lastN) {
        return lambdaQuery()
                .eq(AIMessage::getSessionId, sessionId)
                .orderByDesc(AIMessage::getCreateTime)
                .page(new Page<>(1, lastN));
    }

    public List<AIMessage> getHistory(String sessionId, Long userId) {
        return lambdaQuery()
                .eq(AIMessage::getSessionId, sessionId)
                .eq(AIMessage::getUserId, userId)
                .orderByAsc(AIMessage::getCreateTime)
                .list();
    }
}
