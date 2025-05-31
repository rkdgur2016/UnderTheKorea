import { createRouter, createWebHistory } from 'vue-router'
import PhilosophyView from '@/views/PhilosophyView.vue'

const routes = [
  {path: '/', name: 'home', component: PhilosophyView},
  {path: '/philosophy', name: 'philosophy', component: () => import('../views/PhilosophyView.vue')},
  {path: '/politics', name: 'politics', component: () => import('../views/PoliticsView.vue')},
  {path: '/social', name: 'social', component: () => import('../views/SocialView.vue')},
  {path: '/suggest', name: 'suggest', component: () => import('../views/SuggestView.vue')},
  {path: '/motto', name: 'motto', component: () => import('../views/MottoView.vue')},
  {path: '/register', name: 'Register', component: () => import('../views/RegisterPage.vue')},
]

const router = createRouter({
  history: createWebHistory('/'),
  routes
})

export default router
