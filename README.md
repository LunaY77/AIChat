# AI-Chat 智能聊天应用

## 项目介绍
AI-Chat 是一个基于现代技术栈开发的智能聊天应用，支持用户与AI进行自然语言交互。项目采用前后端分离架构，提供流畅的用户体验和稳定的后端服务。

![screencapture-localhost-5173-chat-2025-04-19-11_55_53.png](https://cangjingyue.oss-cn-hangzhou.aliyuncs.com/picgo/screencapture-localhost-5173-chat-2025-04-19-11_55_53.png)

## 项目功能

- **用户管理**
  - 用户注册登录
  - 账号管理与安全设置
  - JWT认证授权

- **AI聊天**
  - 多会话管理
  - 历史对话记录
  - 阻塞式聊天 (non-streaming)
  - 支持丰富的交互场景

- **扩展能力**
  - 向量数据库集成
  - AI智能助手
  - 内容记忆与上下文保持

## 前端技术栈

| 技术 | 说明 | 官网 |
| :---: | :---: | :---: |
| React | 前端UI框架 | [https://reactjs.org/](https://reactjs.org/) |
| TypeScript | JavaScript超集，提供静态类型 | [https://www.typescriptlang.org/](https://www.typescriptlang.org/) |
| React Router | 路由管理 | [https://reactrouter.com/](https://reactrouter.com/) |
| React Query | 数据获取与缓存管理 | [https://tanstack.com/query/latest](https://tanstack.com/query/latest) |
| DaisyUI | 基于Tailwind的UI组件库 | [https://daisyui.com/](https://daisyui.com/) |
| TailwindCSS | 原子化CSS框架 | [https://tailwindcss.com/](https://tailwindcss.com/) |
| Vite | 现代前端构建工具 | [https://vitejs.dev/](https://vitejs.dev/) |

### 前端目录结构
```
frontend/
├── app/                    # 应用主目录
│   ├── auth/               # 认证相关组件
│   ├── components/         # 通用组件
│   │   └── ai/             # AI聊天相关组件
│   ├── routes/             # 路由组件
│   ├── types/              # TypeScript类型定义
│   ├── utils/              # 工具函数
│   │   └── auth/           # 认证相关工具
│   └── root.tsx            # 应用根组件
├── public/                 # 静态资源
├── package.json            # 项目依赖配置
└── vite.config.ts          # Vite配置
```

## 后端技术栈

| 技术 | 说明 | 官网 |
| :---: | :---: | :---: |
| SpringBoot | Web开发框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| SpringSecurity | 安全框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security) |
| Spring AI | AI功能集成框架 | [https://spring.io/projects/spring-ai](https://spring.io/projects/spring-ai) |
| MyBatisPlus | ORM框架与数据库操作简化 | [https://baomidou.com/](https://baomidou.com/) |
| Redis | 缓存与数据结构服务 | [https://redis.io](https://redis.io) |
| MySQL | 关系型数据库 | [https://www.mysql.com/](https://www.mysql.com/) |
| JWT | 用户认证方案 | [https://jwt.io](https://jwt.io) |
| Lombok | 代码简化工具 | [https://projectlombok.org](https://projectlombok.org) |
| Hutool | Java工具类库 | [https://github.com/looly/hutool](https://github.com/looly/hutool) |
| Knife4j | API文档工具 | [https://doc.xiaominfo.com/](https://doc.xiaominfo.com/) |
| Milvus | 向量数据库 | [https://milvus.io/](https://milvus.io/) |

### 后端功能模块

- **用户模块**：用户注册、登录、权限管理
- **会话模块**：聊天会话创建、管理、删除
- **聊天模块**：AI对话、历史记录管理
- **向量存储**：支持知识库和长期记忆

## 部署指南

### 前端部署
```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 本地开发
npm run dev

# 生产构建
npm run build
```

### 后端部署
```bash
# 使用Maven构建
mvn clean package

# 运行应用
java -jar AI-Chat-0.0.1-SNAPSHOT.jar
```