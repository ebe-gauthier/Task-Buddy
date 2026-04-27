<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

const router = useRouter()
const auth   = useAuthStore()

function goStart() {
  router.push(auth.isLoggedIn ? '/dashboard' : '/register')
}
</script>

<template>
  <div class="page home">
    <div class="grid-bg"></div>
    <div class="glow-blob" style="width:600px;height:600px;background:#1d4ed8;top:-200px;left:-200px;opacity:0.12;"></div>
    <div class="glow-blob" style="width:400px;height:400px;background:#7c3aed;bottom:-100px;right:-100px;opacity:0.08;"></div>

    <!-- Nav -->
    <nav class="nav">
      <div class="nav-inner">
        <div class="logo">⚡ Task<span class="accent">Buddy</span></div>
        <div class="nav-links">
          <template v-if="auth.isLoggedIn">
            <button class="btn btn-primary" @click="router.push('/dashboard')">Go to Dashboard</button>
          </template>
          <template v-else>
            <button class="btn btn-ghost" @click="router.push('/login')">Sign In</button>
            <button class="btn btn-primary" @click="router.push('/register')">Get Started</button>
          </template>
        </div>
      </div>
    </nav>

    <!-- Hero -->
    <section class="hero">
      <div class="hero-badge">✦ Full-Stack Task Management</div>
      <h1 class="hero-title">
        Manage your tasks<br/>
        <span class="accent">without the chaos.</span>
      </h1>
      <p class="hero-sub">
        TaskBuddy keeps your work organized with priorities, statuses,<br/>
        and a clean interface that stays out of your way.
      </p>
      <div class="hero-actions">
        <button class="btn btn-primary btn-lg" @click="goStart">
          {{ auth.isLoggedIn ? 'Go to Dashboard →' : 'Start for Free →' }}
        </button>
        <button v-if="!auth.isLoggedIn" class="btn btn-ghost btn-lg" @click="router.push('/login')">
          Sign In
        </button>
      </div>
    </section>

    <!-- Features -->
    <section class="features">
      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">🎯</div>
          <h3>Priority Levels</h3>
          <p>Tag tasks as Low, Medium, or High priority so you always know what to tackle first.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">📊</div>
          <h3>Status Tracking</h3>
          <p>Move tasks from To Do → In Progress → Done with a single click.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">🔒</div>
          <h3>Secure Accounts</h3>
          <p>Your tasks are private. JWT authentication keeps your data safe and yours alone.</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon">⚡</div>
          <h3>Instant Sync</h3>
          <p>Built on Spring Boot + MongoDB. Everything saves instantly and persists across sessions.</p>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <span>TaskBuddy — Built with Spring Boot, Vue.js & MongoDB</span>
    </footer>
  </div>
</template>

<style scoped>
.home { display: flex; flex-direction: column; min-height: 100vh; }

/* Nav */
.nav {
  position: sticky; top: 0; z-index: 10;
  background: #07090fcc; backdrop-filter: blur(12px);
  border-bottom: 1px solid var(--border);
}
.nav-inner {
  max-width: 1100px; margin: 0 auto;
  padding: 0 24px; height: 64px;
  display: flex; align-items: center; justify-content: space-between;
}
.logo { font-size: 20px; font-weight: 800; letter-spacing: -0.5px; }
.accent { color: var(--accent); }
.nav-links { display: flex; gap: 12px; align-items: center; }

/* Hero */
.hero {
  flex: 1;
  display: flex; flex-direction: column; align-items: center;
  justify-content: center; text-align: center;
  padding: 100px 24px 80px;
  gap: 24px;
}
.hero-badge {
  display: inline-flex;
  background: var(--accent-glow);
  border: 1px solid var(--accent);
  color: var(--accent);
  padding: 6px 16px; border-radius: 999px;
  font-size: 12px; font-weight: 700; letter-spacing: 1px;
}
.hero-title {
  font-size: clamp(36px, 6vw, 64px);
  font-weight: 800; line-height: 1.1;
  letter-spacing: -1px;
}
.hero-sub {
  font-size: 16px; color: var(--text-dim);
  line-height: 1.7; max-width: 520px;
}
.hero-actions { display: flex; gap: 12px; flex-wrap: wrap; justify-content: center; }
.btn-lg { padding: 14px 32px; font-size: 15px; }

/* Features */
.features {
  padding: 60px 24px 80px;
  max-width: 1100px; margin: 0 auto; width: 100%;
}
.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}
.feature-card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 28px 24px;
  display: flex; flex-direction: column; gap: 12px;
  transition: border-color 0.2s, transform 0.2s;
}
.feature-card:hover { border-color: var(--border2); transform: translateY(-3px); }
.feature-icon { font-size: 28px; }
.feature-card h3 { font-size: 16px; font-weight: 700; }
.feature-card p  { font-size: 13px; color: var(--text-dim); line-height: 1.6; }

/* Footer */
.footer {
  text-align: center; padding: 24px;
  border-top: 1px solid var(--border);
  color: var(--text-muted); font-size: 12px;
}
</style>
