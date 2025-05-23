<script setup>
import { ref } from 'vue';
import TheHeader from '@/layout/TheHeader.vue';
import TheSidebar from '@/layout/TheSidebar.vue'; // 가정: 왼쪽 사이드바 컴포넌트가 LoginLayer.vue 라면
import PostDetail from '@/components/PostDetail.vue';

const selectedPostId = ref(null);

const openPostDetail = (postId) => {
  selectedPostId.value = postId;
  console.log("App.vue: PostDetail open with ID:", postId);
};

const closePostDetail = () => {
  selectedPostId.value = null;
  console.log("App.vue: PostDetail closed");
};
</script>

<template>
  <div class="app-container">
    <TheHeader></TheHeader>
    <div class="content-wrapper" :class="{ 'detail-open': selectedPostId !== null }">
      <TheSidebar></TheSidebar> <div class="main-content">
        <router-view @open-detail="openPostDetail"></router-view>
      </div>
      <PostDetail
        v-if="selectedPostId !== null"
        :post-id="selectedPostId"
        @close="closePostDetail"
        class="post-detail"
      />
    </div>
  </div>
</template>

<style scoped>
/* App.vue의 핵심 레이아웃 스타일 */
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content-wrapper {
  display: flex;
  flex: 1;
  width: 100%;
  gap: 1.5rem; /* 사이드바와 메인 콘텐츠 사이의 간격 */
  padding-right: 1.5rem; /* 오른쪽 여백 */
  transition: padding-right 0.3s ease-in-out;
  background:#f7f9fb;
}

.content-wrapper.detail-open {
  padding-right: 0; /* 디테일 페이지 열렸을 때 오른쪽 여백 제거 */
}

.main-content {
  flex-grow: 1; /* 남은 공간을 모두 차지 */
  transition: margin-right 0.3s ease-in-out;
  min-width: 0; /* flex 아이템의 최소 너비 설정 (스크롤 방지) */
}

.content-wrapper.detail-open .main-content {
  margin-right: 35%; /* 디테일 페이지가 열렸을 때 메인 콘텐츠를 왼쪽으로 밀어냄 */
}

.post-detail {
  width: 35%; /* 디테일 페이지의 너비 */
  max-width: 700px; /* 최대 너비 */
  background: #f7f9fb;
  border-left: 1px solid #e3e6ea;
  box-shadow: -5px 0 15px rgba(0, 0, 0, 0.2);
  overflow-y: auto; /* 내용이 길어지면 스크롤바 생성 */
  padding: 2rem;
  box-sizing: border-box;
  position: sticky; /* 스크롤 시 상단에 고정 */
  top: 56px; /* 헤더 높이만큼 아래로 */
  height: calc(100vh - 56px); /* 전체 뷰포트 높이 - 헤더 높이 */
  transform: translateX(100%); /* 기본적으로 오른쪽으로 숨겨져 있음 */
  transition: transform 0.3s ease-in-out;
}

.content-wrapper.detail-open .post-detail {
  transform: translateX(0); /* 디테일 페이지 열리면 화면 안으로 이동 */
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .content-wrapper {
    flex-direction: column; /* 화면이 작아지면 세로로 정렬 */
    gap: 1rem;
    padding-right: 0;
  }
  .content-wrapper.detail-open .main-content {
    margin-right: 0;
  }
  .post-detail {
    width: 100%;
    position: static; /* 스크롤 시 고정 해제 */
    height: auto;
    border-left: none;
    box-shadow: none;
    transform: translateX(0);
    margin-top: 1rem;
    padding: 1.5rem;
  }
}
</style>