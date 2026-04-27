import axios from 'axios'

const API = axios.create({
  baseURL: 'http://localhost:8080/api'
})

// Attach JWT token to every request automatically
API.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

// Auth
export const register = (data) => API.post('/auth/register', data)
export const login    = (data) => API.post('/auth/login', data)

// Tasks
export const getTasks    = ()           => API.get('/tasks')
export const createTask  = (task)       => API.post('/tasks', task)
export const updateTask  = (id, task)   => API.put(`/tasks/${id}`, task)
export const deleteTask  = (id)         => API.delete(`/tasks/${id}`)
