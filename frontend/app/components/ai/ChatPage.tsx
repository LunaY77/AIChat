import { useState, useEffect } from "react";
import { useQuery } from "@tanstack/react-query";
import { checkAuthStatus } from "../../utils/auth/authapi";
import SessionList from "./SessionList";
import ChatBox from "./ChatBox";
import LoginModal from "../../auth/AuthMain";

export default function ChatPage() {
  const [selectedSessionId, setSelectedSessionId] = useState<string | null>(null);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  
  // 检查用户登录状态
  const { data: authStatus, isLoading: authLoading } = useQuery({
    queryKey: ["authStatus"],
    queryFn: checkAuthStatus,
  });

  // 如果用户未登录，显示登录模态框
  useEffect(() => {
    if (!authLoading && !authStatus?.isLoggedIn) {
      setIsLoginModalOpen(true);
    }
  }, [authStatus, authLoading]);

  // 处理选择会话
  const handleSelectSession = (sessionId: string) => {
    setSelectedSessionId(sessionId);
  };

  // 登录模态框关闭处理
  const handleCloseLoginModal = () => {
    setIsLoginModalOpen(false);
  };

  // 加载状态
  if (authLoading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <span className="loading loading-spinner loading-lg"></span>
      </div>
    );
  }

  return (
    <>
      <div className="container mx-auto p-4">
        <div className="flex flex-col md:flex-row gap-4 min-h-[80vh]">
          {/* 会话列表 */}
          <div className="w-full md:w-1/4">
            <SessionList
              onSelectSession={handleSelectSession}
              selectedSessionId={selectedSessionId}
            />
          </div>
          
          {/* 聊天窗口 */}
          <div className="w-full md:w-3/4">
            <ChatBox sessionId={selectedSessionId} />
          </div>
        </div>
      </div>

      {/* 登录模态框 */}
      <LoginModal isOpen={isLoginModalOpen} onClose={handleCloseLoginModal} />
    </>
  );
} 