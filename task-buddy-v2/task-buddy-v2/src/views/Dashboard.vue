<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { useTaskStore } from '../stores/taskStore'

const router = useRouter()
const auth   = useAuthStore()
const store  = useTaskStore()

// UI state
const showModal   = ref(false)
const editingTask = ref(null)
const activeFilter = ref('ALL')
const searchQuery  = ref('')

// Form
const form = ref({ title: '', description: '', priority: 'MEDIUM', status: 'TODO' })

const priorities = ['LOW', 'MEDIUM', 'HIGH']
const statuses   = ['TODO', 'IN_PROGRESS', 'DONE']
const filters    = ['ALL', 'TODO', 'IN_PROGRESS', 'DONE']

onMounted(() => store.fetchTasks())

const filteredTasks = computed(() => {
  let list = store.tasks
  if (activeFilter.value !== 'ALL')
    list = list.filter(t => t.status === activeFilter.value)
  if (searchQuery.value.trim()) {
    const q = searchQuery.value.toLowerCase()
    list = list.filter(t =>
      t.title.toLowerCase().includes(q) ||
      (t.description || '').toLowerCase().includes(q))
  }
  return list
})

function openCreate() {
  editingTask.value = null
  form.value = { title: '', description: '', priority: 'MEDIUM', status: 'TODO' }
  showModal.value = true
}

function openEdit(task) {
  editingTask.value = task
  form.value = { title: task.title, description: task.description || '', priority: task.priority, status: task.status }
  showModal.value = true
}

function closeModal() {
  showModal.value = false
  editingTask.value = null
}

async function submitForm() {
  if (!form.value.title.trim()) return
  if (editingTask.value) {
    await store.editTask(editingTask.value.id, form.value)
  } else {
    await store.addTask(form.value)
  }
  closeModal()
}

async function removeTask(id) {
  if (confirm('Delete this task?')) await store.removeTask(id)
}

async function cycleStatus(task) {
  const next = { TODO: 'IN_PROGRESS', IN_PROGRESS: 'DONE', DONE: 'TODO' }
  await store.editTask(task.id, { ...task, status: next[task.status] })
}

function logout() {
  auth.logout()
  store.clearTasks()
  router.push('/')
}

// Style helpers
const priorityColor = { LOW: '#4ade80', MEDIUM: '#fbbf24', HIGH: '#f87171' }
const priorityBg    = { LOW: '#052e16', MEDIUM: '#451a03', HIGH: '#450a0a' }
const statusLabel   = { TODO: 'To Do', IN_PROGRESS: 'In Progress', DONE: 'Done' }
const statusColor   = { TODO: '#94a3b8', IN_PROGRESS: '#60a5fa', DONE: '#4ade80' }
const filterLabel   = { ALL: 'All', TODO: 'To Do', IN_PROGRESS: 'Active', DONE: 'Done' }
</script>

<template>
  <div class="page dash">
    <div class="grid-bg"></div>

    <!-- Header -->
    <header class="header">
      <div class="header-inner">
        <button class="logo-btn" @click="router.push('/')">⚡ Task<span class="accent">Buddy</span></button>

        <div class="stats">
          <div class="stat">
            <span class="stat-num">{{ store.todoTasks.length }}</span>
            <span class="stat-lbl">To Do</span>
          </div>
          <div class="divider"></div>
          <div class="stat">
            <span class="stat-num" style="color:#60a5fa">{{ store.inProgressTasks.length }}</span>
            <span class="stat-lbl">Active</span>
          </div>
          <div class="divider"></div>
          <div class="stat">
            <span class="stat-num" style="color:#4ade80">{{ store.doneTasks.length }}</span>
            <span class="stat-lbl">Done</span>
          </div>
        </div>

        <div class="header-right">
          <span class="user-name">👋 {{ auth.user?.name }}</span>
          <button class="btn btn-ghost btn-sm" @click="logout">Sign Out</button>
          <button class="btn btn-primary btn-sm" @click="openCreate">+ New Task</button>
        </div>
      </div>
    </header>

    <!-- Main -->
    <main class="main">

      <!-- Error -->
      <div v-if="store.error" class="err-msg" style="margin-bottom:24px">
        ⚠️ {{ store.error }}
      </div>

      <!-- Toolbar -->
      <div class="toolbar">
        <div class="search-wrap">
          <span class="search-icon">🔍</span>
          <input v-model="searchQuery" class="input search-input" placeholder="Search tasks…" />
        </div>
        <div class="filters">
          <button
            v-for="f in filters" :key="f"
            class="filter-btn"
            :class="{ active: activeFilter === f }"
            @click="activeFilter = f"
          >{{ filterLabel[f] }}</button>
        </div>
      </div>

      <!-- Loading -->
      <div v-if="store.loading" class="center-msg">
        <div class="spinner"></div>
        <span>Loading your tasks…</span>
      </div>

      <!-- Empty -->
      <div v-else-if="filteredTasks.length === 0 && !store.error" class="empty">
        <div class="empty-icon">📋</div>
        <p class="empty-title">
          {{ store.tasks.length === 0 ? 'No tasks yet' : 'No tasks match this filter' }}
        </p>
        <p class="empty-sub">
          {{ store.tasks.length === 0 ? 'Create your first task to get started' : 'Try selecting a different filter' }}
        </p>
        <button v-if="store.tasks.length === 0" class="btn btn-primary" @click="openCreate">
          + Create Task
        </button>
      </div>

      <!-- Task grid -->
      <div v-else class="task-grid">
        <div
          v-for="task in filteredTasks"
          :key="task.id"
          class="task-card"
        >
          <div class="task-top">
            <span
              class="priority-badge"
              :style="{ color: priorityColor[task.priority], background: priorityBg[task.priority] }"
            >{{ task.priority }}</span>
            <div class="task-actions">
              <button class="icon-btn" @click="openEdit(task)" title="Edit">✏️</button>
              <button class="icon-btn" @click="removeTask(task.id)" title="Delete">🗑️</button>
            </div>
          </div>

          <h3 class="task-title" :class="{ done: task.status === 'DONE' }">{{ task.title }}</h3>
          <p class="task-desc">{{ task.description || 'No description' }}</p>

          <div class="task-bottom">
            <button
              class="status-btn"
              :style="{ color: statusColor[task.status], borderColor: statusColor[task.status] + '55' }"
              @click="cycleStatus(task)"
              title="Click to advance"
            >
              <span class="dot" :style="{ background: statusColor[task.status] }"></span>
              {{ statusLabel[task.status] }}
            </button>
            <span class="hint">click to advance →</span>
          </div>
        </div>
      </div>
    </main>

    <!-- Modal -->
    <Transition name="modal">
      <div v-if="showModal" class="overlay" @click.self="closeModal">
        <div class="modal card">

          <div class="modal-head">
            <h2>{{ editingTask ? 'Edit Task' : 'New Task' }}</h2>
            <button class="close-btn" @click="closeModal">✕</button>
          </div>

          <div class="modal-body">
            <div class="field">
              <label class="label">Title <span class="required">*</span></label>
              <input v-model="form.title" class="input" placeholder="What needs to be done?" />
            </div>

            <div class="field">
              <label class="label">Description</label>
              <textarea v-model="form.description" class="input" rows="3" placeholder="Add details…"></textarea>
            </div>

            <div class="field-row">
              <div class="field">
                <label class="label">Priority</label>
                <div class="btn-group">
                  <button
                    v-for="p in priorities" :key="p"
                    class="opt-btn"
                    :class="{ active: form.priority === p }"
                    :style="form.priority === p
                      ? { background: priorityBg[p], color: priorityColor[p], borderColor: priorityColor[p] }
                      : {}"
                    @click="form.priority = p"
                  >{{ p }}</button>
                </div>
              </div>

              <div class="field">
                <label class="label">Status</label>
                <div class="btn-group">
                  <button
                    v-for="s in statuses" :key="s"
                    class="opt-btn"
                    :class="{ active: form.status === s }"
                    :style="form.status === s
                      ? { color: statusColor[s], borderColor: statusColor[s] }
                      : {}"
                    @click="form.status = s"
                  >{{ statusLabel[s] }}</button>
                </div>
              </div>
            </div>
          </div>

          <div class="modal-foot">
            <button class="btn btn-ghost" @click="closeModal">Cancel</button>
            <button class="btn btn-primary" :disabled="!form.title.trim()" @click="submitForm">
              {{ editingTask ? 'Save Changes' : 'Create Task' }}
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.dash { display: flex; flex-direction: column; min-height: 100vh; }

/* Header */
.header {
  position: sticky; top: 0; z-index: 10;
  background: #07090fee; backdrop-filter: blur(12px);
  border-bottom: 1px solid var(--border);
}
.header-inner {
  max-width: 1200px; margin: 0 auto;
  padding: 0 24px; height: 64px;
  display: flex; align-items: center; gap: 20px;
}
.logo-btn {
  background: none; border: none; cursor: pointer;
  font-family: 'Syne', sans-serif; font-size: 18px; font-weight: 800;
  color: var(--text); letter-spacing: -0.5px; flex: 1;
}
.accent { color: var(--accent); }

.stats { display: flex; align-items: center; gap: 14px; }
.stat  { display: flex; flex-direction: column; align-items: center; }
.stat-num { font-family: 'DM Mono', monospace; font-size: 17px; font-weight: 500; }
.stat-lbl { font-size: 9px; color: var(--text-muted); text-transform: uppercase; letter-spacing: 1px; }
.divider  { width: 1px; height: 26px; background: var(--border); }

.header-right { display: flex; align-items: center; gap: 10px; }
.user-name { font-size: 13px; color: var(--text-dim); white-space: nowrap; }
.btn-sm { padding: 8px 16px; font-size: 13px; }

/* Main */
.main { max-width: 1200px; margin: 0 auto; padding: 32px 24px; width: 100%; }

/* Toolbar */
.toolbar { display: flex; gap: 14px; align-items: center; margin-bottom: 28px; flex-wrap: wrap; }
.search-wrap { flex: 1; min-width: 180px; position: relative; }
.search-icon { position: absolute; left: 14px; top: 50%; transform: translateY(-50%); font-size: 13px; }
.search-input { padding-left: 38px; }

.filters { display: flex; gap: 6px; }
.filter-btn {
  background: var(--surface); border: 1px solid var(--border);
  color: var(--text-muted); padding: 8px 16px; border-radius: 999px;
  font-family: 'Syne', sans-serif; font-size: 12px; font-weight: 600;
  cursor: pointer; transition: all 0.2s;
}
.filter-btn:hover { border-color: var(--border2); color: var(--text); }
.filter-btn.active { background: var(--accent-glow); border-color: var(--accent); color: var(--accent); }

/* Loading / Empty */
.center-msg {
  display: flex; align-items: center; justify-content: center;
  gap: 14px; padding: 80px; color: var(--text-muted);
}
.spinner {
  width: 22px; height: 22px;
  border: 2px solid var(--border); border-top-color: var(--accent);
  border-radius: 50%; animation: spin 0.8s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

.empty {
  text-align: center; padding: 90px 24px;
  display: flex; flex-direction: column; align-items: center; gap: 12px;
}
.empty-icon  { font-size: 44px; opacity: 0.4; }
.empty-title { font-size: 18px; font-weight: 700; }
.empty-sub   { color: var(--text-dim); font-size: 13px; margin-bottom: 4px; }

/* Task grid */
.task-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(290px, 1fr));
  gap: 14px;
}
.task-card {
  background: var(--surface); border: 1px solid var(--border);
  border-radius: 14px; padding: 20px;
  display: flex; flex-direction: column; gap: 10px;
  transition: all 0.2s; animation: up 0.25s ease both;
}
.task-card:hover { border-color: var(--border2); transform: translateY(-2px); box-shadow: 0 6px 28px #00000044; }
@keyframes up { from { opacity:0; transform: translateY(10px); } to { opacity:1; transform:none; } }

.task-top { display: flex; align-items: center; justify-content: space-between; }
.priority-badge {
  font-family: 'DM Mono', monospace; font-size: 9px; font-weight: 500;
  padding: 3px 10px; border-radius: 999px; letter-spacing: 1px;
}
.task-actions { display: flex; gap: 2px; }
.icon-btn {
  background: none; border: none; cursor: pointer;
  padding: 4px 6px; border-radius: 6px;
  font-size: 13px; opacity: 0.4; transition: all 0.2s;
}
.icon-btn:hover { opacity: 1; background: var(--surface2); }

.task-title { font-size: 15px; font-weight: 700; line-height: 1.3; }
.task-title.done { text-decoration: line-through; opacity: 0.5; }
.task-desc { font-size: 12px; color: var(--text-muted); line-height: 1.5; flex: 1; }

.task-bottom { display: flex; align-items: center; justify-content: space-between; }
.status-btn {
  display: flex; align-items: center; gap: 6px;
  background: none; border: 1px solid;
  padding: 5px 12px; border-radius: 999px;
  font-family: 'Syne', sans-serif; font-size: 11px; font-weight: 600;
  cursor: pointer; transition: all 0.2s;
}
.status-btn:hover { opacity: 0.75; transform: scale(1.04); }
.dot { width: 6px; height: 6px; border-radius: 50%; }
.hint { font-size: 9px; color: var(--text-muted); opacity: 0; transition: opacity 0.2s; }
.task-card:hover .hint { opacity: 1; }

/* Modal */
.overlay {
  position: fixed; inset: 0;
  background: #000000bb; backdrop-filter: blur(4px);
  display: flex; align-items: center; justify-content: center;
  z-index: 100; padding: 24px;
}
.modal { width: 100%; max-width: 520px; overflow: hidden; }

.modal-head {
  display: flex; align-items: center; justify-content: space-between;
  padding: 20px 24px; border-bottom: 1px solid var(--border);
}
.modal-head h2 { font-size: 18px; font-weight: 800; }
.close-btn {
  background: none; border: none; color: var(--text-muted);
  font-size: 15px; cursor: pointer; padding: 4px 8px;
  border-radius: 6px; transition: all 0.2s;
}
.close-btn:hover { background: var(--surface2); color: var(--text); }

.modal-body { padding: 24px; display: flex; flex-direction: column; gap: 18px; }
.field      { display: flex; flex-direction: column; gap: 8px; flex: 1; }
.field-row  { display: flex; gap: 16px; }

.btn-group { display: flex; gap: 6px; flex-wrap: wrap; }
.opt-btn {
  background: var(--bg); border: 1px solid var(--border);
  color: var(--text-muted); padding: 6px 14px; border-radius: var(--radius-sm);
  font-family: 'Syne', sans-serif; font-size: 11px; font-weight: 700;
  cursor: pointer; transition: all 0.2s;
}
.opt-btn:hover { border-color: var(--border2); color: var(--text); }

.modal-foot {
  display: flex; gap: 10px; justify-content: flex-end;
  padding: 18px 24px; border-top: 1px solid var(--border);
}

/* Modal transition */
.modal-enter-active, .modal-leave-active { transition: opacity 0.2s; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
.modal-enter-from .modal, .modal-leave-to .modal { transform: scale(0.96) translateY(8px); }
</style>
