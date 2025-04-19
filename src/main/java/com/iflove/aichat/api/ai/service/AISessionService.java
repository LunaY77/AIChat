package com.iflove.aichat.api.ai.service;

import com.iflove.aichat.api.ai.domain.entity.AISession;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionCreateReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionDeleteReq;
import com.iflove.aichat.api.ai.domain.vo.request.session.AISessionNameChangeReq;
import com.iflove.aichat.api.ai.domain.vo.response.AISessionInfoResp;
import com.iflove.aichat.common.domain.vo.request.PageBaseReq;
import com.iflove.aichat.common.domain.vo.response.PageBaseResp;

/**
 * <p>
 * ai会话表 服务类
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-07
 */
public interface AISessionService {

    AISession findById(String id);

    AISessionInfoResp createSession(AISessionCreateReq req, Long userId);

    void changeName(AISessionNameChangeReq req, Long userId);

    PageBaseResp<AISessionInfoResp> findByUser(PageBaseReq req, Long userId);

    void deleteSession(AISessionDeleteReq req, Long userId);
}
