<template>
  <header class="main-header">
    <div class="header-top">
      <div class="header-left">
        <button class="menu-button">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <line x1="3" y1="12" x2="21" y2="12"></line>
            <line x1="3" y1="6" x2="21" y2="6"></line>
            <line x1="3" y1="18" x2="21" y2="18"></line>
          </svg>
        </button>
        <button class="search-button-icon">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </button>
      </div>

      <div class="header-center">
        <router-link to="/" class="logo">
          <span class="logo-main">Under The Korea</span>
          <span class="logo-sub">대한민국의 현재, 대한민국의 미래</span>
        </router-link>
      </div>

      <div class="header-right">
        <button class="subscribe-button">Subscribe</button>
        <button class="sign-in-button">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
          Sign In
        </button>
      </div>
    </div>

    <nav class="header-bottom-nav">
      <router-link
        :to="{ path: '/philosophy', query: { category: '철학' } }"
        class="nav-link"
        >철학</router-link
      >
      <router-link
        :to="{ path: '/politics', query: { category: '정치' } }"
        class="nav-link"
        >정치</router-link
      >
      <router-link
        :to="{ path: '/social', query: { category: '사회문제' } }"
        class="nav-link"
        >사회문제</router-link
      >
      <router-link
        :to="{ path: '/BalanceGame', query: { category: '광장' } }"
        class="nav-link"
        >광장</router-link
      >
      <router-link
        :to="{ path: '/motto', query: { category: '좌우명' } }"
        class="nav-link"
        >좌우명</router-link
      >
    </nav>
  </header>
</template>

<script setup>
// 필요한 경우 여기에 스크립트 로직 추가 (예: 햄버거 메뉴 클릭 이벤트)
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap");

.main-header {
  display: flex;
  flex-direction: column; /* 세로로 정렬 */
  background: #232a33; /* 어두운 배경색 */
  color: #f7f9fb; /* 텍스트 색상 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2.5rem 1.5rem;
  height: 56px; /* 고정된 높이 */
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
  gap: 1rem; /* 요소 간 간격 */
}

.menu-button,
.search-button-icon,
.sign-in-button {
  background: none;
  border: none;
  color: inherit;
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 0.5rem;
  font-size: 1rem;
}

.menu-button svg,
.search-button-icon svg,
.sign-in-button svg {
  width: 20px;
  height: 20px;
  margin-right: 0.25rem;
}

.subscribe-button {
  background: #354052; /* 어두운 배경색에 맞는 버튼 색상 */
  color: #fff;
  border: 1px solid #4f8cff;
  border-radius: 4px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.3s ease, border-color 0.3s ease;
}

.subscribe-button:hover {
  background: #4f8cff;
  border-color: #4f8cff;
}

.header-center {
  flex-grow: 1;
  display: flex;
  justify-content: center; /* 로고 중앙 정렬 */
}

.logo {
  text-decoration: none;
  color: inherit;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.logo-main {
  font-family: "Pretendard", "Noto Sans KR", sans-serif;
  font-size: 1.5rem; /* 'Under The Korea' 크기 */
  font-weight: 700;
  letter-spacing: -0.05em;
  line-height: 1.2;
}

.logo-sub {
  font-family: "Pretendard", "Noto Sans KR", sans-serif;
  font-size: 0.75rem; /* '대한민국의 현재, 대한민국의 미래' 크기 */
  opacity: 0.7;
}

/* --- 하단 내비게이션 바 스타일 --- */
.header-bottom-nav {
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  align-items: center;
  padding: 0.3rem 1.5rem; /* 상하 여백 조정 */
  background-color: #2b3543; /* 상단 헤더보다 약간 밝은 어두운 배경 */
  gap: 1.5rem; /* 링크 간 간격 */
  flex-wrap: wrap; /* 작은 화면에서 줄바꿈 허용 */
}

.search-button-text svg {
  width: 20px;
  height: 20px;
}

.nav-link {
  text-decoration: none;
  color: #f7f9fb;
  font-weight: 500;
  padding: 0.5rem 0.75rem;
  border-radius: 4px;
  position: relative; /* 밑줄 효과를 위해 position 설정 */
  transition: color 0.2s ease;
  font-family: "Pretendard", "Noto Sans KR", sans-serif;
  font-size: 0.95rem;
}

/* 밑줄 효과 */
.nav-link::after {
  content: "";
  position: absolute;
  left: 50%;
  bottom: 0;
  width: 0;
  height: 3px; /* 밑줄 두께 */
  background-color: #4f8cff; /* 밑줄 색상 */
  transform: translateX(-50%);
  transition: width 0.3s ease-in-out; /* 너비 애니메이션 */
}

.nav-link:hover::after,
.nav-link.router-link-active::after {
  width: calc(100% - 1rem); /* padding을 고려한 너비 (좌우 0.5rem씩) */
}

.nav-link:hover {
  color: #fff; /* hover 시 텍스트 색상 변경 */
}

/* 현재 활성화된 링크 스타일 (밑줄 포함) */
.nav-link.router-link-active {
  color: #4f8cff; /* 활성화된 링크 텍스트 색상 */
  font-weight: 700;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .header-top {
    padding: 0.5rem 1rem;
  }
  .header-left,
  .header-right {
    gap: 0.5rem;
  }
  .subscribe-button {
    padding: 0.3rem 0.6rem;
    font-size: 0.8rem;
  }
  .sign-in-button {
    font-size: 0.8rem;
  }
  .sign-in-button svg {
    width: 16px;
    height: 16px;
  }
  .logo-main {
    font-size: 1.2rem;
  }
  .logo-sub {
    font-size: 0.6rem;
  }

  .header-bottom-nav {
    justify-content: flex-start; /* 작은 화면에서 왼쪽 정렬 */
    padding: 0.8rem 1rem;
    gap: 1rem;
  }
  .search-bar-container {
    flex-grow: 1;
    margin-right: 0;
    max-width: none;
  }
  .nav-link {
    padding: 0.4rem 0.6rem;
    font-size: 0.9rem;
  }
}

@media (max-width: 768px) {
  /* 상단 헤더 아이템 재배치 및 일부 숨기기 */
  .header-right .subscribe-button {
    display: none; /* 작은 화면에서 'Subscribe' 버튼 숨기기 */
  }
  .header-right .sign-in-button span {
    display: none; /* 'Sign In' 텍스트 숨기기 */
  }
  .sign-in-button svg {
    margin-right: 0;
  }
  .header-left .search-button-icon {
    display: none; /* 왼쪽 검색 아이콘 숨기기 (하단에 검색 바 있으므로) */
  }
  .header-center {
    justify-content: flex-start; /* 로고를 왼쪽으로 정렬 */
    margin-left: 0.5rem;
  }

  /* 하단 내비게이션 바 */
  .header-bottom-nav {
    flex-direction: column; /* 검색 바와 링크들을 세로로 정렬 */
    align-items: flex-start;
    gap: 0.5rem;
  }
  .search-bar-container {
    width: 100%; /* 검색 바 전체 너비 */
    margin-bottom: 0.5rem;
  }
}
</style>
