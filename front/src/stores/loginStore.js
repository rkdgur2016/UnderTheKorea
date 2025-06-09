// src/stores/LoginStore.js
import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useLoginStore = defineStore("login", () => {
  // 상태 (State)
  const isLoggedIn = ref(false);
  const loggedInUser = ref(null); 

  // 액션 (Actions)
  // TheHeader에서 데이터를 직접 받아와서 업데이트하는 액션 추가
  function setLoginState(user) {
    if (user) {
      loggedInUser.value = user;
      isLoggedIn.value = true;
      localStorage.setItem("user", JSON.stringify(user)); // 상태 업데이트 시 localStorage 동기화
    } else {
      loggedInUser.value = null;
      isLoggedIn.value = false;
      localStorage.removeItem("user"); // 상태 업데이트 시 localStorage 동기화
    }
  }

  // Pinia 스토어 초기화 시 localStorage에서 데이터 로드
  const initializeAuth = () => {
    try {
      const storedUser = localStorage.getItem("user");
      if (storedUser) {
        const user = JSON.parse(storedUser);
        loggedInUser.value = user;
        isLoggedIn.value = true;
      } else {
        loggedInUser.value = null;
        isLoggedIn.value = false;
      }
    } catch (e) {
      console.error("Failed to parse user from localStorage on init:", e);
      localStorage.removeItem("user"); // 파싱 오류 시 삭제
      loggedInUser.value = null;
      isLoggedIn.value = false;
    }
  };

  // 게터 (Getters)
  const getIsLoggedIn = computed(() => isLoggedIn.value);
  const getLoggedInUser = computed(() => loggedInUser.value);
  
  const getUserName = computed(() =>
    loggedInUser.value ? loggedInUser.value.userName : "게스트"
  );

  const getUserId = computed(() =>
  loggedInUser.value ? loggedInUser.value.userId : null
  );

  // 스토어 반환
  return {
    isLoggedIn, // 직접 접근 가능하도록 노출 (TheHeader에서 업데이트용)
    loggedInUser, // 직접 접근 가능하도록 노출 (TheHeader에서 업데이트용)
    setLoginState,
    initializeAuth,
    getIsLoggedIn,
    getLoggedInUser,
    getUserName,
    getUserId,
  };
});
