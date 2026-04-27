import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'

import Home     from '../views/Home.vue'
import Login    from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'

const routes = [
  { path: '/',          name: 'home',      component: Home },
  { path: '/login',     name: 'login',     component: Login },
  { path: '/register',  name: 'register',  component: Register },
  { path: '/dashboard', name: 'dashboard', component: Dashboard, meta: { requiresAuth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Guard: redirect to login if not authenticated
router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    return { name: 'login' }
  }
  // If already logged in, redirect away from login/register
  if ((to.name === 'login' || to.name === 'register') && auth.isLoggedIn) {
    return { name: 'dashboard' }
  }
})

export default router
