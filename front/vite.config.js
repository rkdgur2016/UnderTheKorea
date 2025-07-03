import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { fileURLToPath, URL } from "node:url";

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
    optimizeDeps: {
      include: ["@vueup/vue-quill"],
    },
  },
  build: {
    outDir: "../src/main/resources/static", // 빌드하면 파일 저장되는 경로
    emptyOutDir: true, // 기존 파일 자동 삭제
  },
  server: {
    proxy: {
      // Spring Boot 서버에 API 요청 프록시
      "/api": "http://localhost:80",
    },
  },
});
