import { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  createSession,
  deleteSessions,
  getSessionsByUser,
  changeSessionName
} from "../../utils/ai/aiApi";
import type { AISessionInfoResponse } from "../../types/aiType";

interface SessionListProps {
  onSelectSession: (sessionId: string) => void;
  selectedSessionId?: string | null;
}

export default function SessionList({ onSelectSession, selectedSessionId }: SessionListProps) {
  const [newSessionName, setNewSessionName] = useState("");
  const [editingSessionId, setEditingSessionId] = useState<string | null>(null);
  const [editName, setEditName] = useState("");
  const [pageNo, setPageNo] = useState(1);
  const [pageSize] = useState(10);
  const queryClient = useQueryClient();

  // 获取会话列表
  const { data: sessionsData, isLoading } = useQuery({
    queryKey: ["sessions", pageNo, pageSize],
    queryFn: () => getSessionsByUser({ pageNo, pageSize }),
  });

  // 创建新会话
  const createSessionMutation = useMutation({
    mutationFn: createSession,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
      setNewSessionName("");
    },
  });

  // 删除会话
  const deleteSessionMutation = useMutation({
    mutationFn: deleteSessions,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
    },
  });

  // 修改会话名称
  const renameSessionMutation = useMutation({
    mutationFn: changeSessionName,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sessions"] });
      setEditingSessionId(null);
    },
  });

  // 处理创建会话
  const handleCreateSession = (e: React.FormEvent) => {
    e.preventDefault();
    if (newSessionName.trim()) {
      createSessionMutation.mutate({ name: newSessionName.trim() });
    }
  };

  // 处理删除会话
  const handleDeleteSession = (sessionId: string) => {
    deleteSessionMutation.mutate({ sessionIds: [sessionId] });
  };

  // 开始编辑会话名称
  const startEditingSession = (session: AISessionInfoResponse) => {
    setEditingSessionId(session.id);
    setEditName(session.name);
  };

  // 保存会话名称
  const saveSessionName = () => {
    if (editingSessionId && editName.trim()) {
      renameSessionMutation.mutate({
        sessionId: editingSessionId,
        name: editName.trim(),
      });
    }
  };

  // 处理分页
  const handleNextPage = () => {
    if (sessionsData?.data?.isLast === false) {
      setPageNo(pageNo + 1);
    }
  };

  const handlePrevPage = () => {
    if (pageNo > 1) {
      setPageNo(pageNo - 1);
    }
  };

  return (
    <div className="bg-base-200 p-4 rounded-lg min-h-[400px] flex flex-col">
      <h2 className="text-xl font-bold mb-4">会话列表</h2>
      
      {/* 创建新会话表单 */}
      <form onSubmit={handleCreateSession} className="mb-4 flex gap-2">
        <input
          type="text"
          value={newSessionName}
          onChange={(e) => setNewSessionName(e.target.value)}
          placeholder="新会话名称"
          className="input input-bordered flex-grow text-sm"
        />
        <button 
          type="submit" 
          className="btn btn-primary btn-sm"
          disabled={createSessionMutation.isPending || !newSessionName.trim()}
        >
          创建
        </button>
      </form>

      {/* 会话列表 */}
      {isLoading ? (
        <div className="flex justify-center items-center flex-grow">
          <span className="loading loading-spinner"></span>
        </div>
      ) : sessionsData?.data?.list.length === 0 ? (
        <div className="text-center text-gray-500 flex-grow flex items-center justify-center">
          没有会话，创建一个新会话开始聊天吧！
        </div>
      ) : (
        <div className="flex-grow overflow-y-auto mb-4">
          <ul className="space-y-2">
            {sessionsData?.data?.list.map((session: AISessionInfoResponse) => (
              <li
                key={session.id}
                className={`border rounded-md p-2 flex justify-between items-center ${
                  selectedSessionId === session.id ? "bg-primary/10 border-primary" : ""
                }`}
              >
                {editingSessionId === session.id ? (
                  <div className="flex gap-2 w-full">
                    <input
                      type="text"
                      value={editName}
                      onChange={(e) => setEditName(e.target.value)}
                      className="input input-sm input-bordered flex-grow"
                      autoFocus
                    />
                    <button
                      onClick={saveSessionName}
                      className="btn btn-sm btn-primary"
                      disabled={renameSessionMutation.isPending}
                    >
                      保存
                    </button>
                    <button
                      onClick={() => setEditingSessionId(null)}
                      className="btn btn-sm"
                    >
                      取消
                    </button>
                  </div>
                ) : (
                  <>
                    <div 
                      className="flex-grow cursor-pointer truncate"
                      onClick={() => onSelectSession(session.id)}
                    >
                      {session.name}
                    </div>
                    <div className="flex gap-1">
                      <button
                        onClick={() => startEditingSession(session)}
                        className="btn btn-xs btn-ghost"
                      >
                        编辑
                      </button>
                      <button
                        onClick={() => handleDeleteSession(session.id)}
                        className="btn btn-xs btn-ghost text-error"
                        disabled={deleteSessionMutation.isPending}
                      >
                        删除
                      </button>
                    </div>
                  </>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* 分页控制 */}
      {sessionsData?.data && sessionsData.data.totalRecords > pageSize && (
        <div className="flex justify-between mt-2">
          <button
            onClick={handlePrevPage}
            disabled={pageNo === 1}
            className="btn btn-sm"
          >
            上一页
          </button>
          <span>
            第 {pageNo} 页 / 共 {Math.ceil(sessionsData.data.totalRecords / pageSize)} 页
          </span>
          <button
            onClick={handleNextPage}
            disabled={sessionsData.data.isLast}
            className="btn btn-sm"
          >
            下一页
          </button>
        </div>
      )}
    </div>
  );
} 