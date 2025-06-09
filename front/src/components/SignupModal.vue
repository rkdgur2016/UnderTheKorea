<template>
    <teleport to="body">
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
            <button @click="closeModals" class="text-white/80 hover:text-white transition-colors">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
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
              <label class="block text-slate-700 text-sm font-medium mb-2">아이디</label>
              <div class="relative flex">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
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
              <label class="block text-slate-700 text-sm font-medium mb-2">닉네임</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207"></path>
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
              <label class="block text-slate-700 text-sm font-medium mb-2">비밀번호</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"></path>
                  </svg>
                </div>
                <input
                  v-model="signupForm.password"
                  type="password"
                  required
                  minlength="8"
                  class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                  placeholder="********"
                />
              </div>
              <p v-if="signupErrors.password" class="text-red-600 mt-2 text-sm">
                {{ signupErrors.password }}
              </p>
            </div>
  
            <div>
              <label class="block text-slate-700 text-sm font-medium mb-2">비밀번호 확인</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg class="h-5 w-5 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"></path>
                  </svg>
                </div>
                <input
                  v-model="signupForm.confirmPassword"
                  type="password"
                  required
                  class="w-full pl-10 pr-4 py-3 bg-slate-100/70 border border-slate-200 rounded-lg focus:ring-2 focus:ring-gray-500 focus:border-gray-500 focus:outline-none transition-colors"
                  placeholder="********"
                />
              </div>
              <p v-if="signupErrors.confirmPassword" class="text-red-600 mt-2 text-sm">
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
    </teleport>
  </template>
  
  <script setup>
  import { ref, reactive } from "vue";
  import axios from "axios";
  
  // props 정의
  const props = defineProps({
    showSignupModal: Boolean,
  });
  
  // emit 정의
  const emit = defineEmits(["close", "open-login", "signup-success-and-open-login"]);
  
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
          emit("signup-success-and-open-login"); // 회원가입 성공 후 로그인 모달 열기 이벤트 발생
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
  
  const closeModals = () => {
    emit("close"); // 모달 닫기 이벤트 발생
    signupMessage.value = ""; // 메시지 초기화
    Object.keys(signupForm).forEach((key) => (signupForm[key] = "")); // 폼 초기화
    Object.keys(signupErrors).forEach((key) => (signupErrors[key] = "")); // 에러 초기화
    userIdCheckMessage.value = ""; // 아이디 중복 확인 메시지 초기화
    isUserIdAvailable.value = false; // 아이디 사용 가능 여부 초기화
  };
  
  const openLoginModal = () => {
    emit("open-login"); // 로그인 모달 열기 이벤트 발생
    signupMessage.value = ""; // 현재 모달의 메시지 초기화
    Object.keys(signupForm).forEach((key) => (signupForm[key] = "")); // 현재 모달 폼 초기화
    Object.keys(signupErrors).forEach((key) => (signupErrors[key] = "")); // 에러 초기화
    userIdCheckMessage.value = ""; // 아이디 중복 확인 메시지 초기화
    isUserIdAvailable.value = false; // 아이디 사용 가능 여부 초기화
  };
  
  // v-focus 디렉티브가 정의되어 있지 않으므로, 사용한다면 별도로 정의해야 합니다.
  // 예시: const vFocus = { mounted: (el) => el.focus() };
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