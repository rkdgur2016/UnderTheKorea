<template>
  <header class="site-header">
    <div class="header-inner">
      <div class="logo-area">
        <span class="logo-under">Under</span>
        <span class="logo-main">The Korea</span>
        <span class="modern-agora">대한민국의 현재, 대한민국의 미래</span>
      </div>
      <div class="header-right">
        <div class="search-container">
          <input type="text" class="search" placeholder="검색" />
          <span class="search-icon"><i class="fas fa-search"></i></span>
        </div>
        <nav class="main-nav">
          <router-link class="nav_button" to="/philosophy" exact-active-class="active">철학</router-link>
          <router-link class="nav_button" to="/politics" exact-active-class="active">정치</router-link>
          <router-link class="nav_button" to="/social" exact-active-class="active">사회문제</router-link>
          <router-link class="nav_button" to="/suggest" exact-active-class="active">제안</router-link>
          <router-link class="nav_button" to="/motto" exact-active-class="active">좌우명</router-link>
        </nav>
      </div>
    </div>
  </header>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.site-header {
  background: #22272b;
  border-bottom: 1.5px solid #363b40;
  width: 100vw;
  min-width: 360px;
  position: sticky;
  top: 0;
  z-index: 100;
  padding: 0;
}

.header-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 56px;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2vw;
  gap: 2vw;
}

.logo-area {
  display: flex;
  align-items: center;
  gap: 0.35rem; /* 로고-슬로건 간격 좁게 */
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1.45rem;
  font-weight: 800;
  color: #f8f8f8;
  letter-spacing: 1.2px;
  white-space: nowrap;
}
.logo-under {
  color: #3ea6ff;
  font-size: 1.45rem;
  font-weight: 900;
  letter-spacing: 2px;
  margin-right: 0.15rem;
}
.logo-main {
  color: #fff;
  font-weight: 700;
  font-size: 1.45rem;
  margin-right: 0.5rem;
}
.modern-agora {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  color: #bdbdbd;
  font-size: 1.02rem;
  font-weight: 400;
  margin-left: 0.5rem; /* 로고와 슬로건 사이 좁게 */
  letter-spacing: 0.5px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 1.4vw;
  min-width: 0;
  flex: 1 1 auto;
  justify-content: flex-end;
}

.search-container {
  position: relative;
  display: flex;
  align-items: center;
  margin-right: 1vw;
}
.search {
  background: #181c20;
  border: 1.5px solid #363b40;
  border-radius: 16px;
  padding: 5px 40px 5px 16px;
  font-size: 1.05rem;
  color: #e0e0e0;
  outline: none;
  transition: border 0.2s;
  width: 220px; /* 검색바 길게! */
  max-width: 30vw;
}
.search:focus {
  border: 1.5px solid #3ea6ff;
}
.search-icon {
  position: absolute;
  right: 14px;
  color: #3ea6ff;
  font-size: 1.15rem;
  pointer-events: none;
}

.main-nav {
  display: flex;
  align-items: center;
  gap: 0.6rem;
  background: none;
}
.nav_button {
  color: #d4e1f5;
  background: transparent;
  padding: 3px 16px;
  border-radius: 12px;
  font-size: 1.02rem;
  font-weight: 500;
  text-decoration: none;
  transition: background 0.17s, color 0.17s;
  display: flex;
  align-items: center;
  height: 32px;
  line-height: 32px;
}
.nav_button.active,
.nav_button.router-link-exact-active,
.nav_button:hover {
  background: #3ea6ff;
  color: #fff;
  font-weight: 800;
}

@media (max-width: 900px) {
  .header-inner {
    flex-direction: column;
    align-items: flex-start;
    height: auto;
    padding: 0.5rem 3vw;
    gap: 0.5rem;
  }
  .header-right {
    width: 100%;
    flex-wrap: wrap;
    gap: 0.7rem;
    justify-content: flex-start;
  }
  .main-nav {
    gap: 0.2rem;
  }
  .search {
    width: 120px;
    font-size: 0.93rem;
  }
  .logo-area {
    font-size: 1.1rem;
    gap: 0.2rem;
  }
  .logo-under, .logo-main {
    font-size: 1.1rem;
  }
  .modern-agora {
    font-size: 0.8rem;
    margin-left: 0.3rem;
  }
}

@media (max-width: 600px) {
  .header-inner {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.3rem;
    padding: 0.4rem 2vw;
  }
  .logo-area {
    font-size: 0.95rem;
    gap: 0.13rem;
  }
  .logo-under, .logo-main {
    font-size: 0.95rem;
  }
  .modern-agora {
    font-size: 0.65rem;
    margin-left: 0.12rem;
  }
  .search {
    width: 40%;
    font-size: 0.9rem;
  }
  .main-nav .nav_button {
    font-size: 0.91rem;
    padding: 2px 9px;
    height: 26px;
    line-height: 26px;
  }
}
</style>
