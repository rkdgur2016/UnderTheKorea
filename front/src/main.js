import { createApp } from "vue";
import { createPinia } from "pinia";
import "@/assets/style.css";
import App from "./App.vue";
import router from "@/router";

const pinia = createPinia();
const app = createApp(App); // Vue 앱 인스턴스를 먼저 생성

app.use(pinia); // Pinia를 먼저 연결
app.use(router); // 그 다음 라우터를 연결 (또는 순서 바꿔도 무방, 중요!)

app.directive("focus", {
  mounted(el) {
    el.focus();
  },
});

app.mount("#app");
