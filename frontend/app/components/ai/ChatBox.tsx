import { useState, useEffect, useRef } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { sendChatMessage, getChatHistory } from "../../utils/ai/aiApi";
import type { AIMessage } from "../../types/aiType";

interface ChatBoxProps {
  sessionId: string | null;
}

export default function ChatBox({ sessionId }: ChatBoxProps) {
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const queryClient = useQueryClient();

  // 获取聊天历史
  const { data: chatHistoryData, isLoading: historyLoading } = useQuery({
    queryKey: ["chatHistory", sessionId],
    queryFn: () => getChatHistory(sessionId as string),
    enabled: !!sessionId, // 只有在有会话ID时才获取历史
  });

  // 发送消息
  const sendMessageMutation = useMutation({
    mutationFn: sendChatMessage,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["chatHistory", sessionId] });
    },
  });

  // 发送消息处理函数
  const handleSendMessage = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!sessionId || !message.trim()) return;

    const userMessage = message.trim();
    setMessage("");
    setIsLoading(true);

    try {
      await sendMessageMutation.mutateAsync({
        content: userMessage,
        messageType: 0, // 用户消息
        sessionId: sessionId,
      });
    } catch (error) {
      console.error("发送消息失败:", error);
    } finally {
      setIsLoading(false);
    }
  };

  // 聊天记录滚动到底部
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [chatHistoryData]);

  // 渲染消息气泡
  const renderMessage = (message: AIMessage) => {
    const isUser = message.messageType === 0;
    
    return (
      <div 
        key={message.id} 
        className={`flex ${isUser ? 'justify-end' : 'justify-start'} mb-4`}
      >
        <div 
          className={`${
            isUser 
              ? 'bg-primary text-primary-content' 
              : 'bg-base-300 text-base-content'
          } p-3 rounded-lg max-w-[80%] break-words`}
        >
          {/* 支持简单的Markdown格式，比如换行 */}
          {message.content.split('\n').map((line, i) => (
            <p key={i}>{line || <br />}</p>
          ))}
        </div>
      </div>
    );
  };

  // 如果没有选择会话，显示提示信息
  if (!sessionId) {
    return (
      <div className="bg-base-100 rounded-lg p-6 flex flex-col h-full justify-center items-center">
        <h3 className="text-xl font-bold text-center mb-2">开始一个新的对话</h3>
        <p className="text-center text-gray-500">
          请从左侧选择一个会话或创建一个新会话
        </p>
      </div>
    );
  }

  return (
    <div className="bg-base-100 rounded-lg p-4 flex flex-col h-full">
      {/* 聊天记录区域 */}
      <div className="flex-grow overflow-y-auto mb-4 p-2">
        {historyLoading ? (
          <div className="flex justify-center items-center h-full">
            <span className="loading loading-spinner"></span>
          </div>
        ) : chatHistoryData?.data?.length === 0 ? (
          <div className="text-center text-gray-500 h-full flex items-center justify-center">
            没有聊天记录，发送一条消息开始对话吧！
          </div>
        ) : (
          <>
            {chatHistoryData?.data?.map(renderMessage)}
            <div ref={messagesEndRef} />
          </>
        )}
      </div>

      {/* 输入区域 */}
      <form onSubmit={handleSendMessage} className="flex gap-2">
        <textarea
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="输入消息..."
          className="textarea textarea-bordered flex-grow resize-none"
          rows={2}
          disabled={isLoading}
        />
        <button
          type="submit"
          className="btn btn-primary self-end"
          disabled={isLoading || !message.trim()}
        >
          {isLoading ? (
            <span className="loading loading-spinner loading-sm"></span>
          ) : (
            "发送"
          )}
        </button>
      </form>
    </div>
  );
} 