<template>
    <teleport to="body">
      <div
        v-if="showLoginModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
      >
        <div
          @click="closeModals"
          class="absolute inset-0 bg-slate-900/50 backdrop-blur-sm"
        ></div>
  
        <div
          class="relative bg-white/90 backdrop-blur-md max-w-md w-full rounded-2xl shadow-2xl border border-slate-200/50 overflow-hidden animate-modal-in"
        >
          <div
            class="relative bg-gradient-to-r from-gray-600 to-slate-700 text-white p-6"
          >
            <div class="flex items-center justify-between">
              <h3 class="text-xl font-bold">로그인</h3>
              <button
                @click="closeModals"
                class="text-white/80 hover:text-white transition-colors"
              >
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </div>
            <p class="text-gray-100 text-sm mt-2">
              Under The Korea에 오신 것을 환영합니다
            </p>
          </div>
  
          <div class="p-6">
            <form @submit.prevent="handleLogin" class="space-y-4">
              <div>
                <label class="block text-slate-700 text-sm font-medium mb-2"
                  >아이디</label
                >
                <div class="relative">
                  <div
                    class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                  >
                    <svg
                      class="h-5 w-5 text-slate-400"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"
                      ></path>
                    </svg>
                  </div>
                  <input
                    v-model="loginForm.userId"
                    type="text"
                    required
                    class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                    placeholder="user1"
                    v-focus
                  />
                </div>
                <p
                  v-if="loginMessage"
                  :class="{
                    'text-green-600': isLoginSuccess,
                    'text-red-600': !isLoginSuccess,
                  }"
                  class="mt-2 text-sm"
                >
                  {{ loginMessage }}
                </p>
              </div>
  
              <div>
                <label class="block text-slate-700 text-sm font-medium mb-2"
                  >비밀번호</label
                >
                <div class="relative">
                  <div
                    class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
                  >
                    <svg
                      class="h-5 w-5 text-slate-400"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                      ></path>
                    </svg>
                  </div>
                  <input
                    v-model="loginForm.password"
                    type="password"
                    required
                    class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                    placeholder="********"
                  />
                </div>
              </div>
  
              <div class="flex items-center justify-between">
                <label class="flex items-center">
                  <input
                    v-model="loginForm.rememberMe"
                    type="checkbox"
                    class="rounded text-gray-600 focus:ring-gray-500 border-slate-300"
                  />
                  <span class="ml-2 text-sm text-slate-600">로그인 상태 유지</span>
                </label>
                <a
                  href="#"
                  class="text-sm text-gray-600 hover:text-gray-800 transition-colors"
                >
                  비밀번호 찾기
                </a>
              </div>
  
              <button
                type="submit"
                class="w-full bg-gray-600 hover:bg-gray-700 text-white py-3 rounded-lg font-medium transition-all duration-200 shadow-md hover:shadow-lg"
                :disabled="isLoginSubmitting"
              >
                로그인
              </button>
            </form>
  
            <div class="mt-6 text-center">
              <span class="text-sm text-slate-600">계정이 없으신가요? </span>
              <button
                @click="openSignupModal"
                class="text-sm text-gray-600 hover:text-gray-800 font-medium transition-colors"
              >
                회원가입
              </button>
            </div>
          </div>
        </div>
      </div>
    </teleport>
  </template>
  
  <script setup>
  import { ref, reactive } from "vue";
  import axios from "axios";
  import { useLoginStore } from "@/stores/LoginStore";
  
  const props = defineProps({
    showLoginModal: Boolean,
  });
  
  const emit = defineEmits(["close", "open-signup"]);
  
  const loginStore = useLoginStore();
  
  const loginForm = reactive({
    userId: "",
    password: "",
    rememberMe: false,
  });
  const isLoginSubmitting = ref(false);
  const loginMessage = ref("");
  const isLoginSuccess = ref(false);
  
  const handleLogin = async () => {
    isLoginSubmitting.value = true;
    loginMessage.value = "";
    isLoginSuccess.value = false;
  
    if (!loginForm.userId || !loginForm.password) {
      loginMessage.value = "아이디와 비밀번호를 모두 입력해주세요.";
      isLoginSubmitting.value = false;
      return;
    }
  
    try {
      const response = await axios.post("/api/users/login", {
        userId: loginForm.userId,
        password: loginForm.password,
        rememberMe: loginForm.rememberMe,
      });
  
      if (response.data && response.data.userId) {
        loginMessage.value = `${response.data.userName}님, 로그인되었습니다.`;
        isLoginSuccess.value = true;
        loginStore.setLoginState(response.data);
        loginForm.userId = "";
        loginForm.password = "";
        emit("close"); // 모달 닫기 이벤트 발생
      } else {
        loginMessage.value = "아이디 또는 비밀번호가 일치하지 않습니다.";
        isLoginSuccess.value = false;
      }
    } catch (error) {
      console.error("로그인 실패:", error);
      isLoginSuccess.value = false;
      if (error.response) {
        if (error.response.status === 401) {
          loginMessage.value = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (error.response.status === 404) {
          loginMessage.value = "존재하지 않는 아이디입니다.";
        } else {
          loginMessage.value = `로그인 실패: ${error.response.status} 오류가 발생했습니다.`;
        }
      } else if (error.request) {
        loginMessage.value =
          "로그인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.";
      } else {
        loginMessage.value = "로그인 요청을 보내는 중 오류가 발생했습니다.";
      }
    } finally {
      isLoginSubmitting.value = false;
    }
  };
  
  const closeModals = () => {
    emit("close"); // 모달 닫기 이벤트 발생
    loginMessage.value = ""; // 메시지 초기화
    Object.keys(loginForm).forEach((key) => {
      if (key !== "rememberMe") loginForm[key] = "";
    }); // 폼 초기화
  };
  
  const openSignupModal = () => {
    emit("open-signup"); // 회원가입 모달 열기 이벤트 발생
    loginMessage.value = ""; // 현재 모달의 메시지 초기화
    Object.keys(loginForm).forEach((key) => {
      if (key !== "rememberMe") loginForm[key] = "";
    }); // 현재 모달 폼 초기화
  };
  
  </script>
  
  <style scoped>
  /* Modal Animations */
  @keyframes modal-in {
    from {
      opacity: 0;
      transform: translateY(-20px) scale(0.95);
    }
    to {
      opacity: 1;
      transform: translateY(0) scale(1);
    }
  }
  
  .animate-modal-in {
    animation: modal-in 0.3s ease-out forwards;
  }
  </style>