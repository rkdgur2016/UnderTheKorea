<template>
  <div class="sidebar-container">
    <div class="auth-section">
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
            <button type="submit" :disabled="isLoginSubmitting">로그인</button>
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
</template>

<script setup>
import { ref, reactive } from 'vue';
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

  // 프론트엔드 로그인 유효성 검사 (필요시 추가)
  if (!loginForm.userId || !loginForm.password) {
    loginMessage.value = '아이디와 비밀번호를 모두 입력해주세요.';
    isLoginSubmitting.value = false;
    return;
  }

  try {
    const response = await axios.post('/api/users/login', { // 백엔드 API 엔드포인트
      userId: loginForm.userId,
      password: loginForm.password,
    });
    console.log(response)
    // 컨트롤러에서 Users 객체를 직접 반환하므로, data가 null이 아니면 성공으로 간주
    if (response.data && response.data.userId) { // userId 필드가 존재하면 로그인 성공으로 간주
      loginMessage.value = `${response.data.userName}님, 로그인되었습니다.`; // 사용자 이름 포함
      isLoginSuccess.value = true;

      loginForm.userId = '';
      loginForm.password = '';
      // TODO: 로그인 성공 후, 사용자 정보를 Vuex Store 또는 로컬 스토리지에 저장하는 로직 추가
      // 예: localStorage.setItem('user', JSON.stringify(response.data));
      // 예: router.push('/dashboard'); // 대시보드 페이지로 이동
    } else {
      // 서버에서 Users 객체가 넘어왔으나, 예상치 못한 경우 (예: 비밀번호 불일치 시 null 또는 빈 객체)
      loginMessage.value = '아이디 또는 비밀번호가 일치하지 않습니다.';
      isLoginSuccess.value = false;
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    isLoginSuccess.value = false;
    if (error.response) {
        // 서버에서 응답이 왔지만, 4xx/5xx 에러인 경우
        if (error.response.status === 401) { // Unauthorized (아이디/비밀번호 불일치)
            loginMessage.value = '아이디 또는 비밀번호가 일치하지 않습니다.';
        } else if (error.response.status === 404) { // Not Found (사용자 없음)
            loginMessage.value = '존재하지 않는 아이디입니다.';
        } else {
            loginMessage.value = `로그인 실패: ${error.response.status} 오류가 발생했습니다.`;
        }
    } else if (error.request) {
      // 요청이 전송되었지만 응답을 받지 못한 경우 (네트워크 문제 등)
      loginMessage.value = '로그인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.';
    } else {
      // 요청 설정 중 문제 발생
      loginMessage.value = '로그인 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isLoginSubmitting.value = false;
  }
};


// --- 회원가입 관련 함수 ---

// 프론트엔드 유효성 검사 (회원가입)
const validateSignupForm = () => {
  let isValid = true;
  Object.keys(signupErrors).forEach(key => signupErrors[key] = ''); // 에러 메시지 초기화

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

// 아이디 중복 확인
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

    console.log(response)

    if (response.data === 0) { // 0: 사용 가능한 ID
      userIdCheckMessage.value = `사용 가능한 아이디입니다.`;
      isUserIdAvailable.value = true;
    } else if (response.data !== 0) { // 1: 이미 존재하는 ID
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
        // 서버에서 4xx/5xx 에러 응답을 보낼 경우
        userIdCheckMessage.value = `아이디 확인 실패: ${error.response.status} 오류`;
        if (error.response.data) {
            // 만약 서버에서 에러 응답 바디에 메시지를 보낸다면 (예: @ExceptionHandler)
            // userIdCheckMessage.value = error.response.data.message || userIdCheckMessage.value;
        }
    } else if (error.request) {
      userIdCheckMessage.value = '아이디 확인 요청 중 오류가 발생했습니다. 네트워크 연결을 확인해주세요.';
    } else {
      userIdCheckMessage.value = '아이디 확인 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isCheckingId.value = false;
  }
};

// 회원가입 처리
const handleSignup = async () => {
  if (!validateSignupForm()) { // 프론트엔드 유효성 검사 통과 못하면 리턴
    signupMessage.value = '입력값을 확인해주세요.';
    isSignupSuccess.value = false;
    return;
  }

  // 아이디 중복 확인을 통과했는지 다시 확인 (선택 사항, 서버에서도 다시 검증)
  if (!isUserIdAvailable.value) {
      signupMessage.value = '아이디 중복 확인을 완료하고 사용 가능한 아이디를 선택해주세요.';
      isSignupSuccess.value = false;
      return;
  }


  isSignupSubmitting.value = true;
  signupMessage.value = '';
  userIdCheckMessage.value = ''; // 회원가입 시도 시 중복 확인 메시지 초기화
  isUserIdAvailable.value = false; // 회원가입 시도 시 중복 확인 상태 초기화

  try {
    const response = await axios.post('/api/users/register', {
      userId: signupForm.userId,
      password: signupForm.password,
      userName: signupForm.username, // 백엔드 Users 객체 필드명과 일치
    });

    // 컨트롤러에서 int (0 또는 1)를 직접 반환하므로, response.data === 1이 성공
    if (response.data === 1) { // 1: 회원가입 성공
      signupMessage.value = '회원가입이 완료되었습니다!';
      isSignupSuccess.value = true;

      // 회원가입 성공 시 폼 초기화
      Object.keys(signupForm).forEach(key => signupForm[key] = '');
      Object.keys(signupErrors).forEach(key => signupErrors[key] = '');

      // 회원가입 성공 후 로그인 탭으로 자동 전환 및 메시지 표시
      setTimeout(() => {
        activeTab.value = 'login';
        loginMessage.value = '회원가입이 완료되었습니다. 이제 로그인해주세요.'; // 로그인 탭에 메시지 표시
        isLoginSuccess.value = true; // 로그인 탭 메시지 성공 색상으로
        signupMessage.value = ''; // 회원가입 탭 메시지는 초기화
      }, 1500); // 1.5초 후 전환

    } else if (response.data === 0) { // 0: 회원가입 실패 (예: DB 오류, 아이디 중복 등)
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
        // 서버에서 4xx/5xx 에러 응답을 보낼 경우
        if (error.response.status === 409) { // Conflict (아이디 중복 등)
            signupErrors.userId = '이미 사용 중인 아이디입니다.';
            signupMessage.value = '회원가입 실패: 이미 사용 중인 아이디입니다.';
        } else if (error.response.status === 400) { // Bad Request (유효성 검사 실패 등)
            signupMessage.value = '입력값을 확인해주세요.';
            // 서버에서 에러 상세 정보(필드별 에러 메시지)를 JSON 형태로 보낸다면 여기에 파싱 로직 추가
            // 예: if (error.response.data.errors) { ... }
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
/* 이전 답변에서 제공된 CSS 스타일을 그대로 사용합니다. */
/* `.sidebar-container`의 `margin-top: auto;` 관련 문제는 해결한 것으로 가정합니다. */
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
  /* 핵심 변경: 여기 margin-top: auto; 를 제거합니다. */
  /* margin-top: auto; */
  margin-bottom: 20px; /* 사이드바 하단 여백을 위해 추가 (선택 사항) */
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
  color: #007bff;
}

.auth-tabs button.active::after {
  content: '';
  position: absolute;
  bottom: -2px; /* border-bottom 두께만큼 */
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
  width: 100%; /* 기본 100% */
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
  gap: 8px; /* 입력 필드와 버튼 사이 간격 */
  align-items: center;
}

/* 아이디 입력 필드 (input-with-button 내부) */
.input-with-button input[type="text"]#signupUserId {
  flex-grow: 1; /* 남은 공간 유연하게 채움 */
  width: auto; /* flex 동작을 위해 auto */
}

.auth-form button[type="submit"],
.auth-form button[type="button"] {
  width: 100%; /* 모든 버튼 100% */
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
  margin-top: 5px;
}

.input-with-button button[type="button"] {
  width: auto; /* 중복 확인 버튼은 내용에 맞게 */
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