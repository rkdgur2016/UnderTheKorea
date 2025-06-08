<template>
  <header
    class="bg-white/80 backdrop-blur-md border-b border-slate-200/50 sticky top-0 z-50"
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <div class="flex-shrink-0">
          <div class="text-left">
            <router-link
              :to="{ path: '/' }"
              class="hover:text-gray-600 transition-colors duration-200"
            >
              <h1 class="text-2xl font-bold text-slate-800 tracking-tight">
                Under The Korea
              </h1>
              <p class="text-xs text-slate-500 -mt-1">
                대한민국의 현재, 대한민국의 미래
              </p>
            </router-link>
          </div>
        </div>
        <nav class="hidden md:flex space-x-8">
          <router-link
            :to="{ path: '/philosophy', query: { category: '철학' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '철학' }"
            @click="setActiveNav('철학')"
          >
            철학
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '철학' }"
            ></span>
          </router-link>
          <router-link
            :to="{ path: '/politics', query: { category: '정치' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '정치' }"
            @click="setActiveNav('정치')"
          >
            정치
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '정치' }"
            ></span>
          </router-link>
          <router-link
            :to="{ path: '/economy', query: { category: '경제' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '경제' }"
            @click="setActiveNav('경제')"
          >
            경제
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '경제' }"
            ></span>
          </router-link>
          <router-link
            :to="{ path: '/social', query: { category: '사회문제' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '사회' }"
            @click="setActiveNav('사회')"
          >
            사회
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '사회' }"
            ></span>
          </router-link>
          <router-link
            :to="{ path: '/BalanceGame', query: { category: '광장' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '광장' }"
            @click="setActiveNav('광장')"
          >
            광장
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '광장' }"
            ></span>
          </router-link>
          <router-link
            :to="{ path: '/motto', query: { category: '좌우명' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200 relative group"
            :class="{ 'text-gray-600': activeNav === '좌우명' }"
            @click="setActiveNav('좌우명')"
          >
            이념
            <span
              class="absolute bottom-0 left-0 w-0 h-0.5 bg-gray-600 transition-all duration-300 group-hover:w-full"
              :class="{ 'w-full': activeNav === '좌우명' }"
            ></span>
          </router-link>
        </nav>

        <div class="flex items-center space-x-4">
          <div
            v-if="loginStore.getIsLoggedIn"
            class="flex items-center space-x-4"
          >
            <p class="text-slate-700 text-sm font-medium">
              {{ loginStore.getUserName }}님, 환영합니다!
            </p>
            <button
              @click="handleLogout"
              class="bg-gray-600 hover:bg-gray-700 text-white px-6 py-2 rounded-lg text-sm font-medium transition-all duration-200 shadow-md hover:shadow-lg"
            >
              로그아웃
            </button>
          </div>
          <div v-else class="flex items-center space-x-4">
            <button
              @click="openLoginModal"
              class="text-slate-700 hover:text-gray-600 px-4 py-2 text-sm font-medium transition-colors duration-200"
            >
              로그인
            </button>
            <button
              @click="openSignupModal"
              class="bg-gray-600 hover:bg-gray-700 text-white px-6 py-2 rounded-lg text-sm font-medium transition-all duration-200 shadow-md hover:shadow-lg"
            >
              회원가입
            </button>
          </div>
        </div>

        <div class="md:hidden">
          <button
            @click="toggleMobileMenu"
            class="text-slate-700 hover:text-gray-600 p-2"
          >
            <svg
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h16M4 18h16"
              />
            </svg>
          </button>
        </div>
      </div>

      <div
        v-if="mobileMenuOpen"
        class="md:hidden py-4 border-t border-slate-200"
      >
        <div class="flex flex-col space-y-2">
          <router-link
            :to="{ path: '/philosophy', query: { category: '철학' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('철학')"
          >
            철학
          </router-link>
          <router-link
            :to="{ path: '/politics', query: { category: '정치' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('정치')"
          >
            정치
          </router-link>
          <router-link
            :to="{ path: '/economy', query: { category: '경제' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('경제')"
          >
            경제
          </router-link>
          <router-link
            :to="{ path: '/social', query: { category: '사회문제' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('사회')"
          >
            사회
          </router-link>
          <router-link
            :to="{ path: '/BalanceGame', query: { category: '광장' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('광장')"
          >
            광장
          </router-link>
          <router-link
            :to="{ path: '/motto', query: { category: '좌우명' } }"
            class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium transition-colors duration-200"
            @click="setActiveNav('좌우명')"
          >
            좌우명
          </router-link>
          <div
            v-if="loginStore.getIsLoggedIn"
            class="flex flex-col space-y-2 pt-4 border-t border-slate-200"
          >
            <p class="text-slate-700 px-3 py-2 text-sm font-medium text-left">
              {{ loginStore.getUserName }}님, 환영합니다!
            </p>
            <button
              @click="handleLogout"
              class="bg-red-500 hover:bg-red-600 text-white px-3 py-2 rounded-lg text-sm font-medium transition-all duration-200 text-left"
            >
              로그아웃
            </button>
          </div>
          <div
            v-else
            class="flex flex-col space-y-2 pt-4 border-t border-slate-200"
          >
            <button
              @click="openLoginModal"
              class="text-slate-700 hover:text-gray-600 px-3 py-2 text-sm font-medium text-left"
            >
              로그인
            </button>
            <button
              @click="openSignupModal"
              class="bg-gray-600 hover:bg-gray-700 text-white px-3 py-2 rounded-lg text-sm font-medium transition-all duration-200 text-left"
            >
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
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
            <svg
              class="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
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
                  />
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
                  />
                </svg>
              </div>
              <input
                v-model="loginForm.password"
                type="password"
                required
                class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                placeholder="????????"
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

  <div
    v-if="showSignupModal"
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
          <h3 class="text-xl font-bold">회원가입</h3>
          <button
            @click="closeModals"
            class="text-white/80 hover:text-white transition-colors"
          >
            <svg
              class="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
          </button>
        </div>
        <p class="text-gray-100 text-sm mt-2">
          대한민국의 미래를 함께 그려나가요
        </p>
      </div>

      <div class="p-6">
        <form @submit.prevent="handleSignup" class="space-y-4">
          <div>
            <label class="block text-slate-700 text-sm font-medium mb-2"
              >아이디</label
            >
            <div class="relative flex">
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
                    d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                  />
                </svg>
              </div>
              <input
                v-model="signupForm.userId"
                type="text"
                required
                minlength="4"
                maxlength="20"
                class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                placeholder="user1"
                v-focus
              />
              <button
                type="button"
                @click="checkUserId"
                :disabled="isCheckingId"
                class="ml-2 px-4 py-2 bg-gray-500 hover:bg-gray-600 text-white rounded-lg text-sm font-medium transition-colors duration-200"
              >
                중복 확인
              </button>
            </div>
            <p v-if="signupErrors.userId" class="text-red-600 mt-2 text-sm">
              {{ signupErrors.userId }}
            </p>
            <p
              v-if="userIdCheckMessage"
              :class="{
                'text-green-600': isUserIdAvailable,
                'text-red-600': !isUserIdAvailable,
              }"
              class="mt-2 text-sm"
            >
              {{ userIdCheckMessage }}
            </p>
          </div>

          <div>
            <label class="block text-slate-700 text-sm font-medium mb-2"
              >닉네임</label
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
                  />
                </svg>
              </div>
              <input
                v-model="signupForm.username"
                type="text"
                required
                maxlength="50"
                class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                placeholder="상큼한 길동이"
              />
            </div>
            <p v-if="signupErrors.username" class="text-red-600 mt-2 text-sm">
              {{ signupErrors.username }}
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
                  />
                </svg>
              </div>
              <input
                v-model="signupForm.password"
                type="password"
                required
                minlength="8"
                class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                placeholder="????????"
              />
            </div>
            <p v-if="signupErrors.password" class="text-red-600 mt-2 text-sm">
              {{ signupErrors.password }}
            </p>
          </div>

          <div>
            <label class="block text-slate-700 text-sm font-medium mb-2"
              >비밀번호 확인</label
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
                  />
                </svg>
              </div>
              <input
                v-model="signupForm.confirmPassword"
                type="password"
                required
                class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                placeholder="????????"
              />
            </div>
            <p
              v-if="signupErrors.confirmPassword"
              class="text-red-600 mt-2 text-sm"
            >
              {{ signupErrors.confirmPassword }}
            </p>
          </div>

          <button
            type="submit"
            class="w-full bg-gray-600 hover:bg-gray-700 text-white py-3 rounded-lg font-medium transition-all duration-200 shadow-md hover:shadow-lg"
            :disabled="isSignupSubmitting"
          >
            회원가입
          </button>
          <p
            v-if="signupMessage"
            :class="{
              'text-green-600': isSignupSuccess,
              'text-red-600': !isSignupSuccess,
            }"
            class="mt-2 text-sm"
          >
            {{ signupMessage }}
          </p>
        </form>

        <div class="mt-6 text-center">
          <span class="text-sm text-slate-600">이미 계정이 있으신가요? </span>
          <button
            @click="openLoginModal"
            class="text-sm text-gray-600 hover:text-gray-800 font-medium transition-colors"
          >
            로그인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useLoginStore } from "@/stores/LoginStore"; // Pinia 스토어 임포트

const router = useRouter();
const activeNav = ref("");

// Modals state
const showLoginModal = ref(false);
const showSignupModal = ref(false);

const mobileMenuOpen = ref(false);

// Pinia 스토어 인스턴스 사용
const loginStore = useLoginStore();

// 반응형 객체 공유 : reactive를 사용해서 전역적으로 공유되는 반응형 객체를 생성할 수 있음.
const loginForm = reactive({
  userId: "",
  username: "",
  password: "",
  rememberMe: false,
});
const isLoginSubmitting = ref(false);
const loginMessage = ref("");
const isLoginSuccess = ref(false);

// Signup form state
const signupForm = reactive({
  userId: "",
  username: "",
  password: "",
  confirmPassword: "",
  agreeTerms: false,
});
const signupErrors = reactive({
  userId: "",
  password: "",
  confirmPassword: "",
  username: "",
});
const isSignupSubmitting = ref(false);
const signupMessage = ref("");
const isSignupSuccess = ref(false);

const isCheckingId = ref(false);
const userIdCheckMessage = ref("");
const isUserIdAvailable = ref(false);

const setActiveNav = (nav) => {
  activeNav.value = nav;
};

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

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
    });
    console.log(response);

    if (response.data && response.data.userId) {
      loginMessage.value = `${response.data.userName}님, 로그인되었습니다.`;
      isLoginSuccess.value = true;

      loginStore.setLoginState(response.data);

      loginForm.userId = "";
      loginForm.password = "";
      closeModals();
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

const handleLogout = async () => {
  try {
    // await axios.post('/api/users/logout'); // 필요하다면 백엔드 로그아웃 API 호출
    console.log("로그아웃 되었습니다.");
  } catch (error) {
    console.error("로그아웃 처리 중 오류 발생:", error);
  } finally {
    // Pinia 스토어와 localStorage 업데이트
    // loginStore.isLoggedIn.value = false; // 이 부분을 제거
    // loginStore.loggedInUser.value = null; // 이 부분을 제거
    loginStore.setLoginState(null); // 이 액션 하나로 모두 처리

    loginMessage.value = "로그아웃되었습니다.";
    isLoginSuccess.value = true;
    // router.push('/'); // 필요하다면 로그아웃 후 리다이렉트
  }
};

// ... (validateSignupForm, checkUserId, handleSignup, openLoginModal, openSignupModal, closeModals 함수는 변경 없음) ...

const validateSignupForm = () => {
  let isValid = true;
  Object.keys(signupErrors).forEach((key) => (signupErrors[key] = ""));

  if (!signupForm.userId) {
    signupErrors.userId = "아이디를 입력해주세요.";
    isValid = false;
  } else if (signupForm.userId.length < 4 || signupForm.userId.length > 20) {
    signupErrors.userId = "아이디는 4자 이상 20자 이하로 입력해주세요.";
    isValid = false;
  }

  if (!signupForm.password) {
    signupErrors.password = "비밀번호를 입력해주세요.";
    isValid = false;
  } else if (signupForm.password.length < 8) {
    signupErrors.password = "비밀번호는 최소 8자 이상이어야 합니다.";
    isValid = false;
  }

  if (signupForm.password !== signupForm.confirmPassword) {
    signupErrors.confirmPassword = "비밀번호가 일치하지 않습니다.";
    isValid = false;
  }

  if (!signupForm.username) {
    signupErrors.username = "사용자 이름을 입력해주세요.";
    isValid = false;
  } else if (signupForm.username.length > 50) {
    signupErrors.username = "사용자 이름은 50자를 초과할 수 없습니다.";
    isValid = false;
  }
  return isValid;
};

const checkUserId = async () => {
  signupErrors.userId = "";
  userIdCheckMessage.value = "";
  isUserIdAvailable.value = false;

  if (!signupForm.userId) {
    signupErrors.userId = "아이디를 입력해주세요.";
    return;
  }
  if (signupForm.userId.length < 4 || signupForm.userId.length > 20) {
    signupErrors.userId = "아이디는 4자 이상 20자 이하로 입력해주세요.";
    return;
  }

  isCheckingId.value = true;
  try {
    const response = await axios.get(
      `/api/users/idConfirm?userId=${signupForm.userId}`
    );

    if (response.data === 0) {
      userIdCheckMessage.value = `사용 가능한 아이디입니다.`;
      isUserIdAvailable.value = true;
    } else if (response.data !== 0) {
      userIdCheckMessage.value = `이미 사용 중인 아이디입니다.`;
      isUserIdAvailable.value = false;
    } else {
      userIdCheckMessage.value =
        "아이디 확인 중 알 수 없는 응답이 발생했습니다.";
      isUserIdAvailable.value = false;
    }
  } catch (error) {
    console.error("아이디 중복 확인 실패:", error);
    isUserIdAvailable.value = false;
    if (error.response) {
      userIdCheckMessage.value = `아이디 확인 실패: ${error.response.status} 오류`;
    } else if (error.request) {
      userIdCheckMessage.value =
        "아이디 확인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.";
    } else {
      userIdCheckMessage.value =
        "아이디 확인 요청을 보내는 중 오류가 발생했습니다.";
    }
  } finally {
    isCheckingId.value = false;
  }
};

const handleSignup = async () => {
  if (!validateSignupForm()) {
    signupMessage.value = "입력값을 확인해주세요.";
    isSignupSuccess.value = false;
    return;
  }

  if (!isUserIdAvailable.value) {
    signupMessage.value =
      "아이디 중복 확인을 완료하고 사용 가능한 아이디를 선택해주세요.";
    isSignupSuccess.value = false;
    return;
  }

  isSignupSubmitting.value = true;
  signupMessage.value = "";
  userIdCheckMessage.value = "";
  isUserIdAvailable.value = false;
  try {
    const response = await axios.post("/api/users/register", {
      userId: signupForm.userId,
      password: signupForm.password,
      userName: signupForm.username,
    });
    if (response.data === 1) {
      signupMessage.value = "회원가입이 완료되었습니다!";
      isSignupSuccess.value = true;
      Object.keys(signupForm).forEach((key) => (signupForm[key] = ""));
      Object.keys(signupErrors).forEach((key) => (signupErrors[key] = ""));
      setTimeout(() => {
        showSignupModal.value = false; // Close signup modal
        showLoginModal.value = true; // Open login modal
        loginMessage.value = "회원가입이 완료되었습니다. 이제 로그인해주세요.";
        isLoginSuccess.value = true;
        signupMessage.value = "";
      }, 1500);
    } else if (response.data === 0) {
      signupMessage.value =
        "회원가입에 실패했습니다. 이미 사용 중인 아이디일 수 있습니다.";
      isSignupSuccess.value = false;
    } else {
      signupMessage.value = "회원가입 중 알 수 없는 응답이 발생했습니다.";
      isSignupSuccess.value = false;
    }
  } catch (error) {
    console.error("회원가입 실패:", error);
    isSignupSuccess.value = false;
    if (error.response) {
      if (error.response.status === 409) {
        signupErrors.userId = "이미 사용 중인 아이디입니다.";
        signupMessage.value = "회원가입 실패: 이미 사용 중인 아이디입니다.";
      } else if (error.response.status === 400) {
        signupMessage.value = "입력값을 확인해주세요.";
      } else {
        signupMessage.value = `회원가입 실패: ${error.response.status} 오류가 발생했습니다.`;
      }
    } else if (error.request) {
      signupMessage.value =
        "회원가입 실패: 서버 응답이 없습니다. 네트워크 연결을 확인해주세요.";
    } else {
      signupMessage.value =
        "회원가입 실패: 요청을 보내는 중 오류가 발생했습니다.";
    }
  } finally {
    isSignupSubmitting.value = false;
  }
};

const openLoginModal = () => {
  showLoginModal.value = true;
  showSignupModal.value = false;
  loginMessage.value = ""; // Clear messages when opening modal
  signupMessage.value = ""; // Clear messages when opening modal
  Object.keys(loginForm).forEach((key) => {
    if (key !== "rememberMe") loginForm[key] = "";
  }); // Clear login form except rememberMe
  Object.keys(signupForm).forEach((key) => (signupForm[key] = "")); // Clear signup form
  Object.keys(signupErrors).forEach((key) => (signupErrors[key] = "")); // Clear signup errors
  userIdCheckMessage.value = ""; // Clear user ID check message
  isUserIdAvailable.value = false; // Reset user ID availability
};

const openSignupModal = () => {
  showSignupModal.value = true;
  showLoginModal.value = false;
  loginMessage.value = ""; // Clear messages when opening modal
  signupMessage.value = ""; // Clear messages when opening modal
  Object.keys(loginForm).forEach((key) => {
    if (key !== "rememberMe") loginForm[key] = "";
  }); // Clear login form except rememberMe
  Object.keys(signupForm).forEach((key) => (signupForm[key] = "")); // Clear signup form
  Object.keys(signupErrors).forEach((key) => (signupErrors[key] = "")); // Clear signup errors
  userIdCheckMessage.value = ""; // Clear user ID check message
  isUserIdAvailable.value = false; // Reset user ID availability
};

const closeModals = () => {
  showLoginModal.value = false;
  showSignupModal.value = false;
  loginMessage.value = ""; // Clear messages when closing modal
  signupMessage.value = ""; // Clear messages when closing modal
  Object.keys(loginForm).forEach((key) => {
    if (key !== "rememberMe") loginForm[key] = "";
  }); // Clear login form except rememberMe
  Object.keys(signupForm).forEach((key) => (signupForm[key] = "")); // Clear signup form
  Object.keys(signupErrors).forEach((key) => (signupErrors[key] = "")); // Clear signup errors
  userIdCheckMessage.value = ""; // Clear user ID check message
  isUserIdAvailable.value = false; // Reset user ID availability
};

// onMounted 훅에서 Pinia 스토어 초기화
onMounted(() => {
  loginStore.initializeAuth(); // Pinia 스토어에서 localStorage를 읽어와 상태 설정
});
</script>

<style scoped>
/* Custom scrollbar */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f5f9;
}

::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}

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

/* Tailwind CSS classes are used for most styling.
 Ensure Tailwind CSS is properly set up in your project.
*/
</style>
