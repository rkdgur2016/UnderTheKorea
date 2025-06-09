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
              @click=" modalStore.openLogin()"
              class="text-slate-700 hover:text-gray-600 px-4 py-2 text-sm font-medium transition-colors duration-200"
            >
              로그인
            </button>
            <button
              @click="modalStore.openSignup()"
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
  </template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useLoginStore } from "@/stores/LoginStore";
import { useModalStore } from "@/stores/modalStore"; // 새로 만든 모달 스토어 임포트

const router = useRouter();
const activeNav = ref("");
const mobileMenuOpen = ref(false);

const loginStore = useLoginStore();
const modalStore = useModalStore(); // 모달 스토어 사용

const setActiveNav = (nav) => {
  activeNav.value = nav;
};

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

const handleLogout = async () => {
  try {
    // await axios.post('/api/users/logout');
    console.log("로그아웃 되었습니다.");
  } catch (error) {
    console.error("로그아웃 처리 중 오류 발생:", error);
  } finally {
    loginStore.setLoginState(null); // Pinia 스토어 초기화
  }
};
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

</style>