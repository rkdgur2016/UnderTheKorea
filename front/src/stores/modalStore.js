// stores/modalStore.js
import { defineStore } from "pinia";
import { ref } from "vue";

export const useModalStore = defineStore("modal", () => {
  const showLoginModal = ref(false);
  const showSignupModal = ref(false);

  function openLogin() {
    showLoginModal.value = true;
    showSignupModal.value = false; // 다른 모달은 닫기
  }

  function openSignup() {
    showSignupModal.value = true;
    showLoginModal.value = false; // 다른 모달은 닫기
  }

  function closeAll() {
    showLoginModal.value = false;
    showSignupModal.value = false;
  }

  // 회원가입 성공 시 로그인 모달을 열기 위한 헬퍼
  function signupSuccessAndOpenLogin() {
    closeAll();
    openLogin();
  }

  return {
    showLoginModal,
    showSignupModal,
    openLogin,
    openSignup,
    closeAll,
    signupSuccessAndOpenLogin,
  };
});