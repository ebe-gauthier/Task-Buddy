<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

const router = useRouter()
const auth   = useAuthStore()

const email    = ref('')
const password = ref('')

async function submit() {
  const ok = await auth.loginUser(email.value, password.value)
  if (ok) router.push('/dashboard')
}
</script>

<template>
  <div class="page auth-page">
    <div class="grid-bg"></div>
    <div class="glow-blob" style="width:500px;height:500px;background:#1d4ed8;top:-150px;left:-150px;opacity:0.1;"></div>

    <nav class="auth-nav">
      <button class="logo-link" @click="router.push('/')">⚡ Task<span class="accent">Buddy</span></button>
    </nav>

    <div class="auth-wrap">
      <div class="card auth-card">

        <div class="auth-head">
          <h1>Welcome back</h1>
          <p>Sign in to your account to continue</p>
        </div>

        <div v-if="auth.error" class="err-msg">{{ auth.error }}</div>

        <div class="auth-form">
          <div class="field">
            <label class="label">Email</label>
            <input
              v-model="email"
              class="input"
              type="email"
              placeholder="you@example.com"
              @keyup.enter="submit"
            />
          </div>

          <div class="field">
            <label class="label">Password</label>
            <input
              v-model="password"
              class="input"
              type="password"
              placeholder="••••••••"
              @keyup.enter="submit"
            />
          </div>

          <button
            class="btn btn-primary btn-full"
            :disabled="auth.loading || !email || !password"
            @click="submit"
          >
            <span v-if="auth.loading">Signing in…</span>
            <span v-else>Sign In →</span>
          </button>
        </div>

        <p class="auth-footer">
          Don't have an account?
          <button class="link-btn" @click="router.push('/register')">Create one</button>
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.auth-page { display: flex; flex-direction: column; min-height: 100vh; }

.auth-nav {
  padding: 20px 32px;
  border-bottom: 1px solid var(--border);
}
.logo-link {
  background: none; border: none; cursor: pointer;
  font-family: 'Syne', sans-serif;
  font-size: 18px; font-weight: 800;
  color: var(--text); letter-spacing: -0.5px;
}
.accent { color: var(--accent); }

.auth-wrap {
  flex: 1; display: flex;
  align-items: center; justify-content: center;
  padding: 40px 24px;
}
.auth-card {
  width: 100%; max-width: 420px;
  padding: 40px;
  display: flex; flex-direction: column; gap: 28px;
}
.auth-head h1 { font-size: 26px; font-weight: 800; margin-bottom: 6px; }
.auth-head p  { color: var(--text-dim); font-size: 14px; }

.auth-form { display: flex; flex-direction: column; gap: 20px; }
.field { display: flex; flex-direction: column; }

.btn-full { width: 100%; padding: 14px; font-size: 15px; }

.auth-footer {
  text-align: center; font-size: 13px; color: var(--text-dim);
}
.link-btn {
  background: none; border: none; cursor: pointer;
  color: var(--accent); font-family: 'Syne', sans-serif;
  font-size: 13px; font-weight: 700;
}
.link-btn:hover { text-decoration: underline; }
</style>
