// AI会话相关类型
export type AISessionCreateRequest = {
  name: string;
};

export type AISessionInfoResponse = {
  id: string;
  name: string;
  userId: number;
  createTime: string;
  updateTime: string;
};

export type AISessionNameChangeRequest = {
  sessionId: string;
  name: string;
};

export type AISessionDeleteRequest = {
  sessionIds: string[];
};

// AI聊天相关类型
export type AIMessageType = 0 | 1 | 2; // 0:user, 1:assistant, 2:system

export type AIMessageRequest = {
  content: string;
  messageType: AIMessageType;
  sessionId: string;
  enableVectorStore?: boolean;
  enableAgent?: boolean;
};

export type AIMessage = {
  id: number;
  content: string;
  messageType: AIMessageType;
  sessionId: string;
  userId: number;
  createTime: string;
  updateTime: string;
};

// 分页相关类型
export type PageBaseRequest = {
  pageNo: number;
  pageSize: number;
};

export type PageBaseResponse<T> = {
  pageNo: number;
  pageSize: number;
  totalRecords: number;
  isLast: boolean;
  list: T[];
}; 