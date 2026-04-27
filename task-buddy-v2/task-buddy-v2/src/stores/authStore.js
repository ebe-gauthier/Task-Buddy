import { defineStore } from 'pinia'
import { login, register } from '../services/api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: JSON.parse(localStorage.getItem('user') || 'null'),
    token: localStorage.getItem('token') || null,
    error: null,
    loading: false
  }),

  getters: {
    isLoggedIn: (state) => !!state.token
  },

  actions: {
    async registerUser(name, email, password) {
      this.loading = true
      this.error = null
      try {
        const res = await register({ name, email, password })
        this._saveSession(res.data)
        return true
      } catch (e) {
        this.error = e.response?.data?.message || 'Registration failed'
        return false
      } finally {
        this.loading = false
      }
    },

    async loginUser(email, password) {
      this.loading = true
      this.error = null
      try {
        const res = await login({ email, password })
        this._saveSession(res.data)
        return true
      } catch (e) {
        this.error = e.response?.data?.message || 'Invalid email or password'
        return false
      } finally {
        this.loading = false
      }
    },

    logout() {
      this.user = null
      this.token = null
      localStorage.removeItem('user')
      localStorage.removeItem('token')
    },

    _saveSession(data) {
      this.token = data.token
      this.user  = { id: data.id, name: data.name, email: data.email }
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(this.user))
    }
  }
})
