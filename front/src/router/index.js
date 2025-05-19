import { createRouter, createWebHistory } from 'vue-router'
import PhilosophyView from '@/views/PhilosophyView.vue'

const routes = [
  {path: '/', name: 'home', component: PhilosophyView},
  {path: '/politics', name: 'politics', component: () => import('../views/PoliticsView.vue')},
  {path: '/suggest', name: 'suggest', component: () => import('../views/SuggestView.vue')},
  {path: '/motto', name: 'main', component: () => import('../views/MottoView.vue')},
]

const router = createRouter({
  history: createWebHistory('/'),
  routes
})

export default router
