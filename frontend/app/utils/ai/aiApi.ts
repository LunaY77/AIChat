import { fetchWithAuth } from "../auth/fetchWithAuth";
import type { 
  AIMessageRequest, 
  AISessionCreateRequest,
  AISessionDeleteRequest,
  AISessionNameChangeRequest,
  PageBaseRequest
} from "../../types/aiType";

const API_BASE_URL = 'http://localhost:8082';

// 会话管理API
export async function createSession(request: AISessionCreateRequest) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/session`, {
    method: 'POST',
    body: JSON.stringify(request),
  });
}

export async function getSessionById(sessionId: string) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/session/${sessionId}`, {
    method: 'GET',
  });
}

export async function getSessionsByUser(request: PageBaseRequest) {
  const { pageNo, pageSize } = request;
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/session/page?pageNo=${pageNo}&pageSize=${pageSize}`, {
    method: 'GET',
  });
}

export async function changeSessionName(request: AISessionNameChangeRequest) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/session/name`, {
    method: 'POST',
    body: JSON.stringify(request),
  });
}

export async function deleteSessions(request: AISessionDeleteRequest) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/session`, {
    method: 'DELETE',
    body: JSON.stringify(request),
  });
}

// 聊天API
export async function sendChatMessage(request: AIMessageRequest) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/chat/block`, {
    method: 'POST',
    body: JSON.stringify(request),
  });
}

export async function getChatHistory(sessionId: string) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/chat/history/${sessionId}`, {
    method: 'GET',
  });
}

export async function deleteChatHistory(sessionId: string) {
  return fetchWithAuth(`${API_BASE_URL}/capi/ai/chat/history/${sessionId}`, {
    method: 'DELETE',
  });
} 