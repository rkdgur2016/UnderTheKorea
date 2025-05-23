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
      <div v-else class="login-prompt">
        <p>로그인하여 더 많은 기능을 이용해보세요!</p>
        <button @click="login" class="login-btn">로그인</button>
        <button @click="register" class="register-btn">회원가입</button>
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
import { ref, onMounted } from 'vue';

// 세션 데이터를 관리할 반응형 변수
const userSession = ref({
  isLoggedIn: false,
  nickname: '',
  email: '',
  avatar: ''
});

// 실제 환경에서는 서버 API를 통해 세션 정보를 가져와야 합니다.
// 여기서는 더미 데이터를 사용합니다.
onMounted(() => {
  // 예시: 로그인된 사용자 세션 (실제로는 API 호출)
  // userSession.value = {
  //   isLoggedIn: true,
  //   nickname: '김철수',
  //   email: 'kimchulsoo@example.com',
  //   avatar: 'https://via.placeholder.com/60'
  // };

  // 예시: 로그인되지 않은 사용자 세션
  userSession.value = {
    isLoggedIn: false,
    nickname: '',
    email: '',
    avatar: ''
  };
});

const login = () => {
  alert('로그인 페이지로 이동합니다.');
  // 실제 로그인 로직 (예: router.push('/login'))
};

const register = () => {
  alert('회원가입 페이지로 이동합니다.');
  // 실제 회원가입 로직 (예: router.push('/register'))
};

const logout = () => {
  alert('로그아웃 되었습니다.');
  userSession.value = {
    isLoggedIn: false,
    nickname: '',
    email: '',
    avatar: ''
  };
  // 실제 로그아웃 로직 (예: 서버 세션 무효화)
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

.login-prompt {
  text-align: center;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e3e6ea;
}

.login-prompt p {
  color: #354052;
  margin-bottom: 1rem;
  line-height: 1.5;
}

.login-btn, .register-btn {
  background: #e9eff7;
  color: #4f8cff;
  border: 1px solid #d0dbe9;
  border-radius: 0.28rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.9rem;
  margin: 0.3rem;
  width: 45%;
  max-width: 120px;
  display: inline-block;
}

.login-btn:hover {
  background: #4f8cff;
  color: #fff;
  border-color: #4f8cff;
}

.register-btn:hover {
  background: #4f8cff;
  color: #fff;
  border-color: #4f8cff;
}


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