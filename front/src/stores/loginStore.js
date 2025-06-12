// src/stores/LoginStore.js
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import axios from "axios";

export const useLoginStore = defineStore("login", () => {
  const isLoggedIn = ref(false);
  const loggedInUser = ref(null);

  // 액션 (Actions)
  function setLoginState(user) {
    if (user) {
      loggedInUser.value = user;
      isLoggedIn.value = true;
      // 로그인 성공 시 sessionStorage에 사용자 정보를 저장 (탭/창 유지)
      sessionStorage.setItem("user", JSON.stringify(user));
    } else {
      loggedInUser.value = null;
      isLoggedIn.value = false;
      // 로그아웃 시 sessionStorage에서도 제거
      sessionStorage.removeItem("user");
    }
  }

  // Pinia 스토어 초기화 시 인증 상태 확인
  const initializeAuth = async () => {
    try {
      // 1. 먼저 sessionStorage에서 사용자 정보를 확인 (현재 탭/창에서 로그인한 경우)
      const storedUser = sessionStorage.getItem("user");
      if (storedUser) {
        const user = JSON.parse(storedUser);
        loggedInUser.value = user;
        isLoggedIn.value = true;
        console.log("세션 스토리지에서 사용자 정보 로드:", user);
        return; // sessionStorage에 정보가 있으면 서버 요청 건너뛰기
      }

      // 2. sessionStorage에 정보가 없으면, 서버에 인증 상태 확인 요청 (쿠키를 통한 로그인 유지 여부)
      console.log("세션 스토리지에 정보 없음. 서버 인증 상태 확인 시도...");
      const response = await axios.get("/api/users/me");
      if (response.data && response.data.userId) {
        // 서버에서 사용자 정보가 넘어오면 로그인 상태 설정
        loggedInUser.value = response.data;
        isLoggedIn.value = true;
        // 서버를 통해 로그인 상태가 확인되었으므로, sessionStorage에도 저장 (새로고침/새 탭에서 유지)
        sessionStorage.setItem("user", JSON.stringify(response.data));
        console.log("서버를 통해 사용자 정보 로드:", response.data);
      } else {
        // 서버에서도 인증 정보가 없으면 로그인 상태 초기화
        loggedInUser.value = null;
        isLoggedIn.value = false;
        sessionStorage.removeItem("user"); // 혹시 모를 잔여 데이터 제거
        console.log("서버 인증 상태 확인 결과: 로그인되지 않음.");
      }
    } catch (e) {
      // 에러 발생 시 (예: 401 Unauthorized, 네트워크 오류 등) 로그인 상태 초기화
      console.error("인증 상태 확인 실패:", e);
      loggedInUser.value = null;
      isLoggedIn.value = false;
      sessionStorage.removeItem("user"); // 파싱 오류나 에러 시 데이터 제거
    }
  };

  // 로그아웃 시 서버에 로그아웃 요청
  const logout = async () => {
    try {
      await axios.post("/api/users/logout"); // 서버에 로그아웃 요청
      setLoginState(null); // 클라이언트 스토어 상태 초기화 및 sessionStorage 제거
      console.log("로그아웃 되었습니다.");
    } catch (error) {
      console.error("로그아웃 처리 중 오류 발생:", error);
      // 서버에서 로그아웃 실패해도 클라이언트 상태는 초기화 (사용자 경험 고려)
      setLoginState(null);
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

  return {
    isLoggedIn,
    loggedInUser,
    setLoginState,
    initializeAuth,
    logout,
    getIsLoggedIn,
    getLoggedInUser,
    getUserName,
    getUserId,
  };
});