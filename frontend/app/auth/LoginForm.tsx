interface LoginFormProps {
  username: string;
  setUsername: (value: string) => void;
  password: string;
  setPassword: (value: string) => void;
  handleSubmit: (e: React.FormEvent) => void;
  isLoading: boolean;
}

export function LoginForm({
  username,
  setUsername,
  password,
  setPassword,
  handleSubmit,
  isLoading,
}: LoginFormProps) {
  return (
    <form onSubmit={handleSubmit}>
      <div className="form-control w-full mt-2">
        <label className="floating-label">
          <span className="label-text">用户ID</span>
          <input
            type="text"
            placeholder="请输入用户ID"
            className="input input-bordered w-full bg-base-200 dark:bg-base-300 text-base-content placeholder:text-base-content/60"
            value={username}
            onChange={e => setUsername(e.target.value)}
            required
          />
        </label>
      </div>

      <div className="form-control w-full mt-2">
        <label className="floating-label">
          <span className="label-text">密码</span>
          <input
            type="password"
            placeholder="请输入密码"
            className="input input-bordered w-full bg-base-200 dark:bg-base-300 text-base-content placeholder:text-base-content/60"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
          />
        </label>
      </div>

      <div className="form-control mt-6">
        <button
          type="submit"
          className="btn btn-primary hover:brightness-110 transition-all"
          disabled={isLoading}
        >
          {isLoading
            ? (
                <>
                  <span className="loading loading-spinner"></span>
                  登录中...
                </>
              )
            : (
                "登录"
              )}
        </button>
      </div>
    </form>
  );
}
