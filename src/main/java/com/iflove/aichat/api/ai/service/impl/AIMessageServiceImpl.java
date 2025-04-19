package com.iflove.aichat.api.ai.service.impl;

import com.iflove.aichat.api.ai.constant.AIChatPrompt;
import com.iflove.aichat.api.ai.dao.AIMessageChatMemory;
import com.iflove.aichat.api.ai.dao.AIMessageDao;
import com.iflove.aichat.api.ai.domain.entity.AIMessage;
import com.iflove.aichat.api.ai.domain.vo.request.message.AIMessageReq;
import com.iflove.aichat.api.ai.service.AIMessageService;
import com.iflove.aichat.common.utils.JsonUtils;
import com.iflove.aichat.common.utils.RequestHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.iflove.aichat.api.ai.constant.AIChatConstant.LISTEN_EVENT;

/**
 * <p>
 * ai消息表 服务实现类
 * </p>
 *
 * @author 苍镜月
 * @since 2025-04-06
 */
@Service
@RequiredArgsConstructor
public class AIMessageServiceImpl implements AIMessageService {

    private final VectorStore vectorStore;
    private final ChatModel dashscopeChatModel;
    private final AIMessageDao aiMessageDao;
    private final ChatMemory chatMemory;

    @Override
    public Flux<ServerSentEvent<String>> chatStream(AIMessageReq req) {
        return createChatClient(req)
                .stream()
                .chatResponse()
                .map(chatResponse ->
                        ServerSentEvent.builder(JsonUtils.toStr(chatResponse))
                                .event(LISTEN_EVENT) // 和前端监听的事件相对应
                                .build()
                );
    }

    @Override
    public String chatBlocking(AIMessageReq req) {
        return createChatClient(req)
                .call()
                .content();
    }

    @Override
    public List<AIMessage> getHistory(String sessionId) {
        return aiMessageDao.getHistory(sessionId, RequestHolder.get().getUserId());
    }

    private ChatClient.ChatClientRequestSpec createChatClient(AIMessageReq req) {
        String[] functionBeanNames = new String[0];
        // 如果启用Agent则获取Agent的bean
        if (Boolean.TRUE.equals(req.getEnableAgent())) {
            // 获取带有Agent注解的bean
            // TODO Agent
            // Map<String, Object> beansWithAnnotation = SpringUtil.getBeanFactory().getBeansWithAnnotation(Agent.class);
            // functionBeanNames = new String[beansWithAnnotation.size()];
            // functionBeanNames = beansWithAnnotation.keySet().toArray(functionBeanNames);
        }
        
        return ChatClient.create(dashscopeChatModel).prompt()
                .system(this::toSystemPrompt)                               // 启用文件问答
                .user(promptUserSpec -> toUserPrompt(promptUserSpec, req))  // agent列表
                .functions(functionBeanNames)
                .advisors(advisorSpec -> {
                    useChatHistory(advisorSpec, req.getSessionId());        // 使用历史消息
                    useVectorStore(advisorSpec, req.getEnableVectorStore());// 使用向量数据库
                    useLogging(advisorSpec);                                // 使用日志
                });
    }

    private void useLogging(ChatClient.AdvisorSpec advisorSpec) {
        advisorSpec.advisors(new SimpleLoggerAdvisor());
    }

    private void useVectorStore(ChatClient.AdvisorSpec advisorSpec, Boolean enableVectorStore) {
        if (Boolean.FALSE.equals(enableVectorStore)) return;
        // question_answer_context是一个占位符，会替换成向量数据库中查询到的文档。QuestionAnswerAdvisor会替换。
        String promptWithContext = """
                                   下面是上下文信息
                                   ---------------------
                                   {question_answer_context}
                                   ---------------------
                                   给定的上下文和提供的历史信息，而不是事先的知识，回复用户的意见。如果答案不在上下文中，告诉用户你不能回答这个问题。
                                   """;
        // todo SearchRequest配置？
        advisorSpec.advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build(), promptWithContext));
    }

    private void useChatHistory(ChatClient.AdvisorSpec advisorSpec, String sessionId) {
        // 1. 如果需要存储会话和消息到数据库，自己可以实现ChatMemory接口，这里使用自己实现的AiMessageChatMemory，数据库存储。
        // 2. 传入会话id，MessageChatMemoryAdvisor会根据会话id去查找消息。
        // 3. 只需要携带最近10条消息
        // MessageChatMemoryAdvisor会在消息发送给大模型之前，从ChatMemory中获取会话的历史消息，然后一起发送给大模型。
        advisorSpec.advisors(new MessageChatMemoryAdvisor(chatMemory, sessionId, 10));
    }

    private void toUserPrompt(ChatClient.PromptUserSpec promptUserSpec, AIMessageReq req) {
        // 转换为Spring AI Message
        Message message = AIMessageChatMemory.toSpringAIMessage(req);
        // 用户发送的文本
        promptUserSpec.text(message.getText());
    }

    private void toSystemPrompt(ChatClient.PromptSystemSpec promptSystemSpec) {
        promptSystemSpec.text(AIChatPrompt.CHAT_SYSTEM_PROMPT);
    }

    @Override
    public void deleteHistory(String sessionId) {
        chatMemory.clear(sessionId);
    }

}
