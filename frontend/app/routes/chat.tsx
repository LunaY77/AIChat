import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ChatPage from "~/components/ai/ChatPage";

const queryClient = new QueryClient();

export function meta() {
  return [
    { title: "AI 聊天 | AI-Chat" },
    { name: "description", content: "与AI进行聊天" },
  ];
}

export default function Chat() {
  return (
    <QueryClientProvider client={queryClient}>
      <ChatPage />
    </QueryClientProvider>
  )
} 