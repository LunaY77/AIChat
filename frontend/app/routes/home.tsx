import type { Route } from "./+types/home";
import { Link } from "react-router";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import LoginButton from "~/auth/LoginButton";
import TestButton from "~/TestButton";
const queryClient = new QueryClient();

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="container mx-auto p-4">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-2xl font-bold">AI 聊天应用</h1>
          <LoginButton />
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="card bg-base-200 shadow-xl">
            <div className="card-body">
              <h2 className="card-title">开始聊天</h2>
              <p>使用我们的AI聊天功能，轻松与AI进行对话。</p>
              <div className="card-actions justify-end mt-4">
                <Link to="/chat" className="btn btn-primary">
                  开始聊天
                </Link>
              </div>
            </div>
          </div>
          
          <div className="card bg-base-200 shadow-xl">
            <div className="card-body">
              <h2 className="card-title">测试功能</h2>
              <p>测试按钮功能。</p>
              <div className="card-actions justify-end mt-4">
                <TestButton />
              </div>
            </div>
          </div>
        </div>
      </div>
    </QueryClientProvider>
  )
}
