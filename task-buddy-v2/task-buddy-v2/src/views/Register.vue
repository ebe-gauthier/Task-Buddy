<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

const router = useRouter()
const auth   = useAuthStore()

const name     = ref('')
const email    = ref('')
const password = ref('')

async function submit() {
  const ok = await auth.registerUser(name.value, email.value, password.value)
  if (ok) router.push('/dashboard')
}
</script>

<template>
  <div class="page auth-page">
    <div class="grid-bg"></div>
    <div class="glow-blob" style="width:500px;height:500px;background:#7c3aed;top:-150px;right:-150px;opacity:0.09;"></div>

    <nav class="auth-nav">
      <button class="logo-link" @click="router.push('/')">⚡ Task<span class="accent">Buddy</span></button>
    </nav>

    <div class="auth-wrap">
      <div class="card auth-card">

        <div class="auth-head">
          <h1>Create account</h1>
          <p>Start organizing your tasks today</p>
        </div>

        <div v-if="auth.error" class="err-msg">{{ auth.error }}</div>

        <div class="auth-form">
          <div class="field">
            <label class="label">Full Name</label>
            <input
              v-model="name"
              class="input"
              type="text"
              placeholder="Your name"
              @keyup.enter="submit"
            />
          </div>

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
              placeholder="At least 6 characters"
              @keyup.enter="submit"
            />
          </div>

          <button
            class="btn btn-primary btn-full"
            :disabled="auth.loading || !name || !email || !password"
            @click="submit"
          >
            <span v-if="auth.loading">Creating account…</span>
            <span v-else>Create Account →</span>
          </button>
        </div>

        <p class="auth-footer">
          Already have an account?
          <button class="link-btn" @click="router.push('/login')">Sign in</button>
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
