import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/layout/TheView.vue";

const routes = [
  { path: "/", name: "home", component: MainView },
  {
    path: "/philosophy",
    name: "philosophy",
    component: () => import("../views/PhilosophyView.vue"),
  },
  {
    path: "/politics",
    name: "politics",
    component: () => import("../views/PoliticsView.vue"),
  },
  {
    path: "/social",
    name: "social",
    component: () => import("../views/SocialView.vue"),
  },
  {
    path: "/economy",
    name: "economy",
    component: () => import("../views/EconomyView.vue"),
  },
  {
    path: "/motto",
    name: "motto",
    component: () => import("../views/MottoView.vue"),
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("../views/RegisterPage.vue"),
  },
  {
    path: "/PostCreate",
    name: "PostCreate",
    component: () => import("@/components/PostCreateView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory("/"),
  routes,
});

export default router;
