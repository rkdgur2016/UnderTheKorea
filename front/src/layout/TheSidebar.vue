<template>
  <aside class="site-sidebar">
    <div class="sidebar-inner">
      <div v-if="userSession.isLoggedIn" class="user-profile">
        <img :src="userSession.avatar" alt="User Avatar" class="avatar" />
        <div class="user-info">
          <span class="username">{{ userSession.nickname }}</span>
          <span class="email">{{ userSession.email }}</span>
        </div>
        <button @click="logout" class="logout-btn">로그아웃</button>
      </div>

      <div v-else class="login-form-area">
        <p class="login-prompt-text">로그인하여 더 많은 기능을 이용해보세요!</p>
        <div class="input-group">
          <input type="text" v-model="loginForm.userId" placeholder="아이디" class="login-input" />
        </div>
        <div class="input-group">
          <input type="password" v-model="loginForm.password" placeholder="비밀번호" class="login-input" />
        </div>
        <button @click="handleLogin" class="login-submit-btn">로그인</button>
        <button @click="goToRegister" class="register-btn-in-sidebar">회원가입</button>
      </div>

      <nav class="sidebar-nav">
        <ul>
          <li><router-link to="/mypage">마이페이지</router-link></li>
          <li><router-link to="/settings">설정</router-link></li>
          <li><router-link to="/messages">메시지</router-link></li>
          <li><router-link to="/bookmarks">북마크</router-link></li>
        </ul>
      </nav>

      <div class="sidebar-footer">
        <p>&copy; 2023 UnderTheKorea</p>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios'; // axios 임포트

const router = useRouter();

// 사용자 세션 정보 (백엔드 UserDTO 또는 Users 객체 필드와 매핑)
const userSession = ref({
  isLoggedIn: false,
  serialNo: null,
  userId: '', // 백엔드의 userId
  username: '', // 백엔드의 userName 필드를 여기에 매핑
  email: '',
  avatar: 'https://via.placeholder.com/60' // 기본 아바타
});

// 로그인 폼 데이터
const loginForm = reactive({
  userId: '', // 템플릿의 loginForm.id -> loginForm.userId로 변경
  password: ''
});

// UI 상태
const isSubmitting = ref(false); // 로그인 요청 중 상태
const loginMessage = ref('');     // 로그인 성공/실패 메시지
const isLoginSuccess = ref(false); // 로그인 성공 여부

// 컴포넌트 마운트 시 로컬 스토리지에서 세션 복원
onMounted(() => {
  const storedUser = localStorage.getItem('currentUser');
  if (storedUser) {
    try {
      const userData = JSON.parse(storedUser);
      // 백엔드 Users 또는 UserDTO 필드에 맞게 매핑
      userSession.value = {
        isLoggedIn: true,
        serialNo: userData.serialNo,
        userId: userData.userId,
        username: userData.userName || userData.username, // userName 또는 username 둘 다 고려
        email: userData.email,
        avatar: userData.avatar || 'https://via.placeholder.com/60' // 저장된 아바타가 있다면 사용, 아니면 기본
      };
      // 로그인 폼 초기화
      loginForm.userId = '';
      loginForm.password = '';
    } catch (e) {
      console.error('Failed to parse user data from localStorage', e);
      localStorage.removeItem('currentUser'); // 파싱 실패 시 데이터 삭제
    }
  }
});

// 로그인 처리 함수
const handleLogin = async () => {
  // 간단한 클라이언트 측 유효성 검사
  if (!loginForm.userId || !loginForm.password) {
    loginMessage.value = '아이디와 비밀번호를 모두 입력해주세요.';
    isLoginSuccess.value = false;
    return;
  }

  isSubmitting.value = true;
  loginMessage.value = ''; // 메시지 초기화

  try {
    // 백엔드 로그인 API 호출
    // 컨트롤러의 @RequestBody Users loginRequest 에 매핑될 필드명을 사용
    const response = await axios.post('/api/users/login', {
      userId: loginForm.userId,
      password: loginForm.password
    });

    if (response.status === 200 && response.data) {
      isLoginSuccess.value = true;
      loginMessage.value = `로그인 성공! 환영합니다, ${response.data.userName}님!`; // 백엔드 Users의 userName 사용

      // 세션 정보 업데이트
      userSession.value = {
        isLoggedIn: true,
        serialNo: response.data.serialNo,
        userId: response.data.userId,
        username: response.data.userName, // 백엔드 응답의 userName 필드 사용
        email: response.data.email,
        avatar: userSession.value.avatar // 기존 아바타 유지 또는 서버에서 받아온 값으로 업데이트
      };

      // 로컬 스토리지에 사용자 정보 저장 (새로고침 시 로그인 상태 유지)
      localStorage.setItem('currentUser', JSON.stringify(response.data));

      // 로그인 폼 초기화
      loginForm.userId = '';
      loginForm.password = '';
      
      // 필요에 따라 페이지 리로드 또는 라우팅 (현재 사이드바이므로 라우팅은 안 함)
      // router.push('/'); 
    } else {
      isLoginSuccess.value = false;
      loginMessage.value = '로그인 실패: 알 수 없는 응답입니다.';
    }

  } catch (error) {
    console.error('로그인 요청 실패:', error);
    isLoginSuccess.value = false;
    if (error.response) {
      // 서버에서 에러 응답이 온 경우
      if (error.response.status === 401 || error.response.status === 404) {
        loginMessage.value = '아이디 또는 비밀번호가 일치하지 않습니다.';
      } else if (error.response.data && error.response.data.message) {
        loginMessage.value = '로그인 실패: ' + error.response.data.message;
      } else {
        loginMessage.value = `로그인 실패: 서버 오류 (${error.response.status})`;
      }
    } else if (error.request) {
      // 요청은 보냈으나 응답을 받지 못한 경우 (네트워크 문제)
      loginMessage.value = '로그인 실패: 서버에 연결할 수 없습니다.';
    } else {
      // 요청 설정 중 발생한 오류
      loginMessage.value = '로그인 실패: 요청 오류입니다.';
    }
  } finally {
    isSubmitting.value = false;
  }
};

// 회원가입 페이지로 이동 함수
const goToRegister = () => {
  router.push('/signup'); // 회원가입 라우트가 '/signup'임을 재확인
};

// 로그아웃 처리 함수
const logout = () => {
  // 실제 백엔드 로그아웃 API가 있다면 여기에 호출
  // 예: axios.post('/api/logout').then(() => { /* ... */ });

  localStorage.removeItem('currentUser'); // 로컬 스토리지에서 사용자 정보 제거
  userSession.value = { // 세션 상태 초기화
    isLoggedIn: false,
    serialNo: null,
    userId: '',
    username: '',
    email: '',
    avatar: 'https://via.placeholder.com/60'
  };
  loginMessage.value = '로그아웃되었습니다.';
  isLoginSuccess.value = false; // 로그아웃 메시지를 에러가 아닌 일반 메시지로 표시하기 위해 false
  // alert('로그아웃 되었습니다.'); // 알림 대신 메시지 영역에 표시
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.site-sidebar {
  width: 250px; /* 사이드바 너비 */
  background: #f7f9fb; /* PoliticsView와 유사한 배경색 */
  border-right: 1px solid #e3e6ea;
  padding: 1.5rem 1rem;
  display: flex;
  flex-direction: column;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.05); /* 부드러운 그림자 */
  position: sticky;
  top: 56px; /* 헤더 아래에 위치 (TheHeader의 height가 56px) */
  height: calc(100vh - 56px); /* 헤더를 제외한 전체 높이 */
  overflow-y: auto; /* 내용이 많아지면 스크롤 */
  font-size: 0.9rem;
}

.sidebar-inner {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 사용자 프로필 섹션 (로그인 시) */
.user-profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e3e6ea;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 0.8rem;
  border: 2px solid #4f8cff;
}

.user-info {
  text-align: center;
  margin-bottom: 0.8rem;
}

.username {
  font-size: 1.1rem;
  font-weight: 700;
  color: #232a33;
  display: block;
}

.email {
  font-size: 0.85rem;
  color: #7a8baa;
}

.logout-btn {
  background: #4f8cff;
  color: #fff;
  border: none;
  border-radius: 0.28rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: background 0.2s;
  font-size: 0.9rem;
  width: 80%;
  max-width: 150px;
}

.logout-btn:hover {
  background: #3a75e0;
}

/* 로그인 폼 섹션 (로그아웃 시) */
.login-form-area {
  text-align: center;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e3e6ea;
}

.login-prompt-text {
  color: #354052;
  margin-bottom: 1rem;
  line-height: 1.5;
  font-size: 0.95rem;
}

.input-group {
  margin-bottom: 0.8rem;
}

.login-input {
  width: calc(100% - 20px); /* 패딩 고려 */
  padding: 0.7rem 10px;
  border: 1px solid #d0dbe9;
  border-radius: 0.28rem;
  font-size: 0.9rem;
  box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
}

.login-input:focus {
  outline: none;
  border-color: #4f8cff;
  box-shadow: 0 0 0 2px rgba(79, 140, 255, 0.2);
}

.login-submit-btn {
  background: #4f8cff;
  color: #fff;
  border: none;
  border-radius: 0.28rem;
  padding: 0.6rem 1rem;
  cursor: pointer;
  transition: background 0.2s;
  font-size: 0.95rem;
  width: calc(100% - 20px);
  max-width: 200px; /* 버튼 최대 너비 */
  margin-bottom: 0.8rem;
}

.login-submit-btn:hover {
  background: #3a75e0;
}

.register-btn-in-sidebar {
  background: #e9eff7;
  color: #4f8cff;
  border: 1px solid #d0dbe9;
  border-radius: 0.28rem;
  padding: 0.6rem 1rem;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.95rem;
  width: calc(100% - 20px);
  max-width: 200px; /* 버튼 최대 너비 */
}

.register-btn-in-sidebar:hover {
  background: #4f8cff;
  color: #fff;
  border-color: #4f8cff;
}


/* 사이드바 네비게이션 */
.sidebar-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar-nav li {
  margin-bottom: 0.5rem;
}

.sidebar-nav a {
  display: block;
  padding: 0.7rem 1rem;
  color: #354052;
  text-decoration: none;
  border-radius: 0.28rem;
  transition: background 0.2s, color 0.2s;
  font-weight: 500;
}

.sidebar-nav a:hover,
.sidebar-nav a.router-link-exact-active {
  background: #e9eff7;
  color: #4f8cff;
}

/* 사이드바 푸터 */
.sidebar-footer {
  margin-top: auto; /* 하단에 고정 */
  padding-top: 1.5rem;
  border-top: 1px solid #e3e6ea;
  text-align: center;
  color: #a6b0bc;
  font-size: 0.75rem;
}

/* 미디어 쿼리: 모바일 환경에서는 사이드바 숨기거나 다른 방식으로 표시 */
@media (max-width: 1024px) {
  .site-sidebar {
    display: none; /* 예시: 특정 해상도 이하에서 사이드바 숨김 */
  }
}
</style>