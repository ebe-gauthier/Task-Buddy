<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <RouterView />
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Syne:wght@400;600;700;800&family=DM+Mono:wght@300;400;500&display=swap');

*, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

:root {
  --bg:          #07090f;
  --surface:     #0e1320;
  --surface2:    #151d2e;
  --border:      #1a2540;
  --border2:     #243050;
  --text:        #e2e8f0;
  --text-muted:  #4a6080;
  --text-dim:    #8899aa;
  --accent:      #3b82f6;
  --accent-dim:  #1d4ed8;
  --accent-glow: #3b82f622;
  --green:       #4ade80;
  --yellow:      #fbbf24;
  --red:         #f87171;
  --radius:      12px;
  --radius-sm:   8px;
}

body {
  background: var(--bg);
  color: var(--text);
  font-family: 'Syne', sans-serif;
  min-height: 100vh;
  -webkit-font-smoothing: antialiased;
}

/* Shared grid background */
.grid-bg {
  position: fixed;
  inset: 0;
  background-image:
    linear-gradient(var(--border) 1px, transparent 1px),
    linear-gradient(90deg, var(--border) 1px, transparent 1px);
  background-size: 44px 44px;
  opacity: 0.25;
  pointer-events: none;
  z-index: 0;
}

/* Shared glow blob */
.glow-blob {
  position: fixed;
  border-radius: 50%;
  filter: blur(120px);
  pointer-events: none;
  z-index: 0;
}

/* Shared page wrapper */
.page { position: relative; z-index: 1; min-height: 100vh; }

/* Shared card */
.card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 16px;
}

/* Shared input */
.input {
  width: 100%;
  background: var(--bg);
  border: 1px solid var(--border);
  color: var(--text);
  padding: 12px 16px;
  border-radius: var(--radius);
  font-family: 'Syne', sans-serif;
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.input:focus {
  border-color: var(--accent);
  box-shadow: 0 0 0 3px var(--accent-glow);
}
.input::placeholder { color: var(--text-muted); }

textarea.input { resize: none; }

/* Shared button */
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: var(--radius);
  font-family: 'Syne', sans-serif;
  font-weight: 700;
  font-size: 14px;
  cursor: pointer;
  border: none;
  transition: all 0.2s;
}
.btn:disabled { opacity: 0.45; cursor: not-allowed; }
.btn-primary  { background: var(--accent); color: #fff; }
.btn-primary:hover:not(:disabled) { background: var(--accent-dim); box-shadow: 0 4px 20px var(--accent-glow); }
.btn-ghost    { background: none; border: 1px solid var(--border); color: var(--text-dim); }
.btn-ghost:hover:not(:disabled) { border-color: var(--border2); color: var(--text); }
.btn-danger   { background: #450a0a; color: var(--red); border: 1px solid #7f1d1d; }
.btn-danger:hover:not(:disabled) { background: #7f1d1d; }

/* Field label */
.label {
  display: block;
  font-size: 11px;
  font-weight: 700;
  color: var(--text-muted);
  text-transform: uppercase;
  letter-spacing: 1.2px;
  margin-bottom: 8px;
}
.required { color: var(--red); }

/* Error message */
.err-msg {
  background: #450a0a55;
  border: 1px solid #7f1d1d;
  color: #fca5a5;
  padding: 12px 16px;
  border-radius: var(--radius-sm);
  font-size: 13px;
}

/* Page transitions */
.fade-enter-active, .fade-leave-active { transition: opacity 0.2s ease; }
.fade-enter-from, .fade-leave-to       { opacity: 0; }
</style>
