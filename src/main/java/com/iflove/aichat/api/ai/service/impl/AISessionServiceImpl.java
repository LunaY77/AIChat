package com.iflove.aichat.api.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iflove.aichat.api.ai.dao.AISessionDao;
import com.iflove.aichat.api.ai.domain.entity.AISession;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionCreateReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionDeleteReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionNameChangeReq;
import com.iflove.aichat.api.ai.domain.vo.response.AISessionInfoResp;
import com.iflove.aichat.api.ai.service.AISessionService;
import com.iflove.aichat.common.domain.vo.request.PageBaseReq;
import com.iflove.aichat.common.domain.vo.response.PageBaseResp;
import com.iflove.aichat.common.exception.AIErrorEnum;
import com.iflove.aichat.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AISessionServiceImpl implements AISessionService {
    private final AISessionDao aiSessionDao;

    @Override
    public AISession findById(String id) {
        return aiSessionDao.getById(id);
    }

    @Override
    public PageBaseResp<AISessionInfoResp> findByUser(PageBaseReq req, Long userId) {
        IPage<AISession> pageResult = aiSessionDao.findByUserId(userId, req.plusPage());
        if (CollectionUtil.isEmpty(pageResult.getRecords())) {
            return PageBaseResp.empty();
        }
        return PageBaseResp.init(pageResult, pageResult
                .getRecords()
                .stream()
                .map(a -> BeanUtil.copyProperties(a, AISessionInfoResp.class))
                .toList()
        );
    }

    @Override
    public void deleteSession(AISessionDeleteReq req, Long userId) {
        aiSessionDao.deleteAllByIdInBatchAndUserId(req.getSessionIds(), userId);
    }

    @Override
    public AISessionInfoResp createSession(AISessionCreateReq req, Long userId) {
        AISession session = AISession.builder()
                .userId(userId)
                .name(req.getName())
                .build();
        aiSessionDao.save(session);
        return BeanUtil.copyProperties(session, AISessionInfoResp.class);
    }

    @Override
    public void changeName(AISessionNameChangeReq req, Long userId) {
        AISession session = aiSessionDao.findByIdAndUserId(req.getSessionId(), userId);
        if (Objects.isNull(session)) {
            throw new BusinessException(AIErrorEnum.SESSION_NOT_FOUND);
        }
        session.setName(req.getName());
        aiSessionDao.updateById(session);
    }
}