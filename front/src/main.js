import { createApp } from 'vue'
import { createPinia } from 'pinia';
import '@/assets/style.css'
import App from './App.vue'
import router from '@/router'

const pinia = createPinia(); 
createApp(App).use(router).mount('#app')
app.use(pinia); // 앱에 Pinia 연결