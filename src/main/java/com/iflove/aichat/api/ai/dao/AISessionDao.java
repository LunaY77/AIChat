package com.iflove.aichat.api.ai.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iflove.aichat.api.ai.domain.entity.AISession;
import com.iflove.aichat.api.ai.mapper.AISessionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AISessionDao extends ServiceImpl<AISessionMapper, AISession> {

    public IPage<AISession> findByUserId(Long userId, IPage page) {
        return lambdaQuery()
                .eq(AISession::getUserId, userId)
                .page(page);
    }

    public AISession findByIdAndUserId(String id, Long userId) {
        return lambdaQuery()
                .eq(AISession::getId, id)
                .eq(AISession::getUserId, userId)
                .one();
    }

    public void deleteAllByIdInBatchAndUserId(List<String> ids, Long userId) {
        lambdaUpdate()
                .in(AISession::getId, ids)
                .eq(AISession::getUserId, userId)
                .remove();
    }
}
