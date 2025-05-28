<template>
  <div class="sidebar-container">
    <div class="auth-section">
      <div v-if="isLoggedIn" class="logged-in-section">
        <p class="welcome-message">{{ loggedInUser.userName }}님, 환영합니다!</p>
        <div class="auth-buttons">
          <button @click="router.push('/mypage')" class="auth-btn mypage-btn">마이페이지</button>
          <button @click="handleLogout" class="auth-btn logout-btn">로그아웃</button>
        </div>
      </div>

      <div v-else>
        <div class="auth-tabs">
          <button
            :class="{ 'active': activeTab === 'login' }"
            @click="activeTab = 'login'"
          >
            로그인
          </button>
          <button
            :class="{ 'active': activeTab === 'signup' }"
            @click="activeTab = 'signup'"
          >
            회원가입
          </button>
        </div>

        <div class="auth-content">
          <div v-if="activeTab === 'login'">
            <form @submit.prevent="handleLogin" class="auth-form">
              <div class="form-group">
                <label for="loginUserId">아이디</label>
                <input type="text" id="loginUserId" v-model="loginForm.userId" required>
              </div>
              <div class="form-group">
                <label for="loginPassword">비밀번호</label>
                <input type="password" id="loginPassword" v-model="loginForm.password" required>
              </div>
              <button class="login-button" type="submit" :disabled="isLoginSubmitting">로그인</button>
              <p v-if="loginMessage" :class="{ 'success': isLoginSuccess, 'error': !isLoginSuccess }" class="auth-message">{{ loginMessage }}</p>
            </form>
          </div>

          <div v-if="activeTab === 'signup'">
            <form @submit.prevent="handleSignup" class="auth-form">
              <div class="form-group">
                <label for="signupUserId">아이디</label>
                <div class="input-with-button">
                  <input type="text" id="signupUserId" v-model="signupForm.userId" required minlength="4" maxlength="20">
                  <button type="button" @click="checkUserId" :disabled="isCheckingId">중복 확인</button>
                </div>
                <p v-if="signupErrors.userId" class="error-message">{{ signupErrors.userId }}</p>
                <p v-if="userIdCheckMessage" :class="{ 'success': isUserIdAvailable, 'error': !isUserIdAvailable }" class="check-message">
                  {{ userIdCheckMessage }}
                </p>
              </div>

              <div class="form-group">
                <label for="signupPassword">비밀번호</label>
                <input type="password" id="signupPassword" v-model="signupForm.password" required minlength="8">
                <p v-if="signupErrors.password" class="error-message">{{ signupErrors.password }}</p>
              </div>

              <div class="form-group">
                <label for="signupConfirmPassword">비밀번호 확인</label>
                <input type="password" id="signupConfirmPassword" v-model="signupForm.confirmPassword" required>
                <p v-if="signupErrors.confirmPassword" class="error-message">{{ signupErrors.confirmPassword }}</p>
              </div>

              <div class="form-group">
                <label for="signupUsername">사용자 이름</label>
                <input type="text" id="signupUsername" v-model="signupForm.username" required maxlength="50">
                <p v-if="signupErrors.username" class="error-message">{{ signupErrors.username }}</p>
              </div>
              <button type="submit" :disabled="isSignupSubmitting">회원가입</button>
              <p v-if="signupMessage" :class="{ 'success': isSignupSuccess, 'error': !isSignupSuccess }" class="auth-message">
                {{ signupMessage }}
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'; // onMounted import 추가
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

// 탭 관리
const activeTab = ref('login'); // 'login' 또는 'signup'

// 로그인 폼 상태
const loginForm = reactive({
  userId: '',
  password: '',
});
const isLoginSubmitting = ref(false);
const loginMessage = ref('');
const isLoginSuccess = ref(false); // 로그인 성공 여부 (메시지 색상 변경용)

// --- 새로운 로그인 상태 변수 추가 ---
const isLoggedIn = ref(false); // 사용자 로그인 여부
const loggedInUser = ref(null); // 로그인된 사용자 정보 (username, userId 등)
// --- 추가 끝 ---

// 회원가입 폼 상태
const signupForm = reactive({
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
});

const signupErrors = reactive({ // 프론트엔드 유효성 검사 에러
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
});

const isSignupSubmitting = ref(false);
const signupMessage = ref('');
const isSignupSuccess = ref(false); // 회원가입 성공 여부 (메시지 색상 변경용)

const isCheckingId = ref(false);
const userIdCheckMessage = ref('');
const isUserIdAvailable = ref(false);


// --- 로그인 관련 함수 ---
const handleLogin = async () => {
  isLoginSubmitting.value = true;
  loginMessage.value = '';
  isLoginSuccess.value = false;

  if (!loginForm.userId || !loginForm.password) {
    loginMessage.value = '아이디와 비밀번호를 모두 입력해주세요.';
    isLoginSubmitting.value = false;
    return;
  }

  try {
    const response = await axios.post('/api/users/login', {
      userId: loginForm.userId,
      password: loginForm.password,
    });
    console.log(response);

    if (response.data && response.data.userId) {
      // 로그인 성공!
      loginMessage.value = `${response.data.userName}님, 로그인되었습니다.`;
      isLoginSuccess.value = true;
      
      // --- 로그인 상태 업데이트 ---
      isLoggedIn.value = true;
      loggedInUser.value = response.data; // 서버에서 받은 사용자 정보 저장
      // --- 로그인 상태 업데이트 끝 ---

      // 로그인 성공 시 폼 초기화 (선택 사항: UI를 위해 ID/PW를 비우는 대신 유지할 수도 있음)
      loginForm.userId = '';
      loginForm.password = '';
      
      // 로컬 스토리지에 사용자 정보 저장 (페이지 새로고침 시 로그인 상태 유지)
      localStorage.setItem('loggedInUser', JSON.stringify(response.data));

    } else {
      // 서버에서 Users 객체가 넘어왔으나, 예상치 못한 경우 (예: 비밀번호 불일치 시 null 또는 빈 객체)
      loginMessage.value = '아이디 또는 비밀번호가 일치하지 않습니다.';
      isLoginSuccess.value = false;
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    isLoginSuccess.value = false;
    if (error.response) {
      if (error.response.status === 401) {
        loginMessage.value = '아이디 또는 비밀번호가 일치하지 않습니다.';
      } else if (error.response.status === 404) {
        loginMessage.value = '존재하지 않는 아이디입니다.';
      } else {
        loginMessage.value = `로그인 실패: ${error.response.status} 오류가 발생했습니다.`;
      }
    } else if (error.request) {
      loginMessage.value = '로그인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.';
    } else {
      loginMessage.value = '로그인 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isLoginSubmitting.value = false;
  }
};

// --- 로그아웃 관련 함수 ---
const handleLogout = async () => {
  // 실제 백엔드 로그아웃 API 호출 (선택 사항)
  try {
    // await axios.post('/api/users/logout'); // 백엔드에 로그아웃 요청 (세션 무효화 등)
    console.log('로그아웃 되었습니다.');
  } catch (error) {
    console.error('로그아웃 처리 중 오류 발생:', error);
  } finally {
    // 프론트엔드 로그인 상태 초기화
    isLoggedIn.value = false;
    loggedInUser.value = null;
    loginMessage.value = '로그아웃되었습니다.'; // 로그아웃 메시지 표시 (필요시)
    isLoginSuccess.value = true; // 성공 메시지 스타일 적용
    
    // 로컬 스토리지에서 사용자 정보 제거
    localStorage.removeItem('loggedInUser');
    
    // (선택 사항) 로그인 탭으로 전환
    activeTab.value = 'login';
    
    // (선택 사항) 라우터 이동
    // router.push('/'); 
  }
};


// --- 컴포넌트 마운트 시 로컬 스토리지에서 로그인 상태 확인 ---
onMounted(() => {
  try {
    const storedUser = localStorage.getItem('loggedInUser');
    if (storedUser) {
      const user = JSON.parse(storedUser);
      isLoggedIn.value = true;
      loggedInUser.value = user;
      loginMessage.value = `${user.userName}님, 로그인되었습니다.`;
      isLoginSuccess.value = true;
    }
  } catch (e) {
    console.error("Failed to parse user from localStorage", e);
    // localStorage 데이터가 손상되었을 경우 처리
    localStorage.removeItem('loggedInUser'); 
  }
});


// --- 회원가입 관련 함수 (기존 코드 유지) ---

const validateSignupForm = () => {
  let isValid = true;
  Object.keys(signupErrors).forEach(key => signupErrors[key] = '');

  if (!signupForm.userId) {
    signupErrors.userId = '아이디를 입력해주세요.';
    isValid = false;
  } else if (signupForm.userId.length < 4 || signupForm.userId.length > 20) {
    signupErrors.userId = '아이디는 4자 이상 20자 이하로 입력해주세요.';
    isValid = false;
  }

  if (!signupForm.password) {
    signupErrors.password = '비밀번호를 입력해주세요.';
    isValid = false;
  } else if (signupForm.password.length < 8) {
    signupErrors.password = '비밀번호는 최소 8자 이상이어야 합니다.';
    isValid = false;
  }

  if (signupForm.password !== signupForm.confirmPassword) {
    signupErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    isValid = false;
  }

  if (!signupForm.username) {
    signupErrors.username = '사용자 이름을 입력해주세요.';
    isValid = false;
  } else if (signupForm.username.length > 50) {
    signupErrors.username = '사용자 이름은 50자를 초과할 수 없습니다.';
    isValid = false;
  }
  return isValid;
};

const checkUserId = async () => {
  signupErrors.userId = '';
  userIdCheckMessage.value = '';
  isUserIdAvailable.value = false;

  if (!signupForm.userId) {
    signupErrors.userId = '아이디를 입력해주세요.';
    return;
  }
  if (signupForm.userId.length < 4 || signupForm.userId.length > 20) {
    signupErrors.userId = '아이디는 4자 이상 20자 이하로 입력해주세요.';
    return;
  }

  isCheckingId.value = true;
  try {
    const response = await axios.get(`/api/users/idConfirm?userId=${signupForm.userId}`);

    console.log(response);

    if (response.data === 0) {
      userIdCheckMessage.value = `사용 가능한 아이디입니다.`;
      isUserIdAvailable.value = true;
    } else if (response.data !== 0) {
      userIdCheckMessage.value = `이미 사용 중인 아이디입니다.`;
      isUserIdAvailable.value = false;
    } else {
      userIdCheckMessage.value = '아이디 확인 중 알 수 없는 응답이 발생했습니다.';
      isUserIdAvailable.value = false;
    }
  } catch (error) {
    console.error('아이디 중복 확인 실패:', error);
    isUserIdAvailable.value = false;
    if (error.response) {
      userIdCheckMessage.value = `아이디 확인 실패: ${error.response.status} 오류`;
    } else if (error.request) {
      userIdCheckMessage.value = '아이디 확인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.';
    } else {
      userIdCheckMessage.value = '아이디 확인 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isCheckingId.value = false;
  }
};

const handleSignup = async () => {
  if (!validateSignupForm()) {
    signupMessage.value = '입력값을 확인해주세요.';
    isSignupSuccess.value = false;
    return;
  }

  if (!isUserIdAvailable.value) {
      signupMessage.value = '아이디 중복 확인을 완료하고 사용 가능한 아이디를 선택해주세요.';
      isSignupSuccess.value = false;
      return;
  }

  isSignupSubmitting.value = true;
  signupMessage.value = '';
  userIdCheckMessage.value = '';
  isUserIdAvailable.value = false;

  try {
    const response = await axios.post('/api/users/register', {
      userId: signupForm.userId,
      password: signupForm.password,
      userName: signupForm.username,
    });

    if (response.data === 1) {
      signupMessage.value = '회원가입이 완료되었습니다!';
      isSignupSuccess.value = true;

      Object.keys(signupForm).forEach(key => signupForm[key] = '');
      Object.keys(signupErrors).forEach(key => signupErrors[key] = '');

      setTimeout(() => {
        activeTab.value = 'login';
        loginMessage.value = '회원가입이 완료되었습니다. 이제 로그인해주세요.';
        isLoginSuccess.value = true;
        signupMessage.value = '';
      }, 1500);

    } else if (response.data === 0) {
      signupMessage.value = '회원가입에 실패했습니다. 이미 사용 중인 아이디일 수 있습니다.';
      isSignupSuccess.value = false;
    } else {
        signupMessage.value = '회원가입 중 알 수 없는 응답이 발생했습니다.';
        isSignupSuccess.value = false;
    }
  } catch (error) {
    console.error('회원가입 실패:', error);
    isSignupSuccess.value = false;

    if (error.response) {
      if (error.response.status === 409) {
        signupErrors.userId = '이미 사용 중인 아이디입니다.';
        signupMessage.value = '회원가입 실패: 이미 사용 중인 아이디입니다.';
      } else if (error.response.status === 400) {
        signupMessage.value = '입력값을 확인해주세요.';
      } else {
        signupMessage.value = `회원가입 실패: ${error.response.status} 오류가 발생했습니다.`;
      }
    } else if (error.request) {
      signupMessage.value = '회원가입 실패: 서버 응답이 없습니다. 네트워크 연결을 확인해주세요.';
    } else {
      signupMessage.value = '회원가입 실패: 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isSignupSubmitting.value = false;
  }
};
</script>

<style scoped>
/* 기존 스타일은 그대로 유지하며, 로그인 상태에 따른 UI를 위한 스타일을 추가합니다. */
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.sidebar-container {
  width: 280px; /* 사이드바 너비 */
  background-color: #f8f9fa; /* 사이드바 배경색 */
  padding: 20px;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  height: 100vh; /* 전체 높이 */
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤 */
  font-family: 'Pretendard', sans-serif;
}

/* 추가된 로그인 상태 섹션 스타일 */
.logged-in-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 0;
  border-top: 1px solid #eee; /* 로그인 섹션 위 구분선 */
}

.welcome-message {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px; /* 버튼과의 간격 */
  text-align: center;
}

.auth-buttons {
  display: flex;
  gap: 10px; /* 버튼 사이 간격 */
  width: 100%;
  justify-content: center; /* 버튼 중앙 정렬 */
}

.auth-btn {
  padding: 10px 15px;
  border: 1px solid #007bff;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  font-size: 0.95rem;
  cursor: pointer;
  transition: background-color 0.2s ease, border-color 0.2s ease;
  flex-grow: 1; /* 버튼이 공간을 채우도록 */
  max-width: 120px; /* 버튼 최대 너비 제한 (선택 사항) */
}

.auth-btn.mypage-btn {
  background-color: #f8f9fa;
  color: #007bff;
  border-color: #e2e6ea;
}

.auth-btn.mypage-btn:hover {
  background-color: #e2e6ea;
  color: #0056b3;
}

.auth-btn.logout-btn:hover {
  background-color: #0056b3;
}


/* 기존 스타일은 그대로 유지 */
.main-nav ul {
  list-style: none;
  padding: 0;
  margin-bottom: 30px;
}

.main-nav li {
  margin-bottom: 10px;
}

.main-nav a {
  display: block;
  padding: 10px 15px;
  text-decoration: none;
  color: #333;
  border-radius: 5px;
  transition: background-color 0.2s ease;
}

.main-nav a:hover,
.main-nav a.router-link-active {
  background-color: #e2e6ea;
  color: #007bff;
}

/* 인증 섹션 (로그인/회원가입) */
.auth-section {
  border-top: 1px solid #eee;
  padding-top: 20px;
  margin-bottom: 20px;
}

.auth-tabs {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 2px solid #eee;
}

.auth-tabs button {
  flex: 1;
  padding: 10px 0;
  border: none;
  background-color: transparent;
  cursor: pointer;
  font-size: 1.05rem;
  font-weight: 600;
  color: #666;
  position: relative;
  transition: color 0.2s ease;
}

.auth-tabs button.active {
  color: #354052;
}

.auth-tabs button.active::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #007bff;
}

.auth-content h3 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.auth-form .form-group {
  margin-bottom: 15px;
}

.auth-form label {
  display: block;
  margin-bottom: 5px;
  font-size: 0.95rem;
  color: #555;
}

.auth-form input[type="text"],
.auth-form input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 0.9rem;
}

.auth-form input[type="text"]:focus,
.auth-form input[type="password"]:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.input-with-button {
  display: flex;
  gap: 8px;
  align-items: center;
}

.input-with-button input[type="text"]#signupUserId {
  flex-grow: 1;
  width: auto;
}

.auth-form button[type="submit"],
.auth-form button[type="button"] {
  width: 100%;
  padding: 10px 15px;
  background: #354052; 
  color: #fff;
  border: 2px solid #4f8cff;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s ease, border-color 0.3s ease;
  margin-top: 5px;
}

.auth-form button[type="submit"]:hover,
.auth-form button[type="button"]:hover {
  background: #4f8cff;
  border-color: #4f8cff;
}

.input-with-button button[type="button"] {
  width: auto;
  margin-top: 0;
  padding: 8px 12px;
  font-size: 0.9rem;
}


.auth-form button[type="submit"]:hover:not(:disabled),
.auth-form button[type="button"]:hover:not(:disabled) {
  background-color: #0056b3;
}

.auth-form button[type="submit"]:disabled,
.auth-form button[type="button"]:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #dc3545;
  font-size: 0.8em;
  margin-top: 3px;
  margin-bottom: 0;
}

.check-message {
  font-size: 0.8em;
  margin-top: 3px;
  margin-bottom: 0;
  padding: 0 5px;
}

.check-message.success {
  color: #28a745;
}

.check-message.error {
  color: #dc3545;
}

.auth-message {
  text-align: center;
  margin-top: 15px;
  padding: 8px;
  border-radius: 4px;
  font-weight: 500;
  font-size: 0.9em;
}

.auth-message.success {
  background-color: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.auth-message.error {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>