import { defineStore } from 'pinia'
import { getTasks, createTask, updateTask, deleteTask } from '../services/api'

export const useTaskStore = defineStore('task', {
  state: () => ({
    tasks: [],
    loading: false,
    error: null
  }),

  getters: {
    todoTasks:       (state) => state.tasks.filter(t => t.status === 'TODO'),
    inProgressTasks: (state) => state.tasks.filter(t => t.status === 'IN_PROGRESS'),
    doneTasks:       (state) => state.tasks.filter(t => t.status === 'DONE'),
  },

  actions: {
    async fetchTasks() {
      this.loading = true
      this.error = null
      try {
        const res = await getTasks()
        this.tasks = res.data
      } catch (e) {
        this.error = 'Could not load tasks. Make sure the backend is running.'
      } finally {
        this.loading = false
      }
    },

    async addTask(task) {
      const res = await createTask(task)
      this.tasks.push(res.data)
    },

    async editTask(id, task) {
      const res = await updateTask(id, task)
      const i = this.tasks.findIndex(t => t.id === id)
      if (i !== -1) this.tasks[i] = res.data
    },

    async removeTask(id) {
      await deleteTask(id)
      this.tasks = this.tasks.filter(t => t.id !== id)
    },

    clearTasks() {
      this.tasks = []
    }
  }
})
