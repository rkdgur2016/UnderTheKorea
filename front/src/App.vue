<script setup>
import { ref, watch } from 'vue'; // watch를 임포트
import { useRoute } from 'vue-router'; // useRoute를 임포트

import TheHeader from '@/layout/TheHeader.vue';
import LoginLayer from '@/layout/TheSidebar.vue'; // LoginLayer가 TheSidebar로 변경된 것 반영
import PostDetail from '@/components/PostDetail.vue';

const selectedPostId = ref(null);

const openPostDetail = (postId) => {
  selectedPostId.value = postId;
  console.log("게시글 아이디 :", postId);
};

const closePostDetail = () => {
  selectedPostId.value = null;
  console.log("App.vue: PostDetail closed");
};

// Vue Router의 현재 경로를 가져옵니다.
const route = useRoute();

// route.path (경로)가 변경될 때 selectedPostId를 초기화합니다.
watch(() => route.path, () => {
  if (selectedPostId.value !== null) { // 디테일 페이지가 열려있을 경우에만 초기화
    selectedPostId.value = null;
    console.log("App.vue: Router path changed, PostDetail closed.");
  }
});
</script>

<template>
  <div class="app-container">
    <TheHeader></TheHeader>
    <div class="content-wrapper" :class="{ 'detail-open': selectedPostId !== null }">
      <LoginLayer></LoginLayer>
      <div class="main-content">
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
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content-wrapper {
  display: flex;
  flex: 1; /* 남은 공간을 차지하도록 설정 */
  width: 100%;
  gap: 1.5rem; /* 사이드바와 메인 콘텐츠 사이의 간격 */
  padding-right: 1.5rem; /* 오른쪽 여백 */
  transition: padding-right 0.3s ease-in-out;
  background: #f7f9fb;
  position: relative; /* 자식 요소인 post-detail의 sticky 기준을 위해 */
}

.content-wrapper.detail-open {
  padding-right: 0; /* 디테일 페이지 열렸을 때 오른쪽 여백 제거 */
}

.main-content {
  flex-grow: 1; /* 남은 공간을 모두 차지 */
  transition: margin-right 0.3s ease-in-out;
  min-width: 0; /* flex 아이템의 최소 너비 설정 (스크롤 방지) */
  background: #f7f9fb; /* 메인 콘텐츠 배경색 설정 */
  padding-bottom: 2rem; /* 하단 여백 추가 */
}

.post-detail {
  width: 100%; /* 디테일 페이지의 너비 */
  max-width: 700px; /* 최대 너비 */
  background: transparent;
  overflow-y: auto; /* 내용이 길어지면 스크롤바 생성 */
  padding: 2rem;
  box-sizing: border-box;
  position: sticky; /* 스크롤 시 상단에 고정 */
  top: 56px; /* 헤더 높이만큼 아래로 */
  height: calc(100vh - 56px); /* 전체 뷰포트 높이 - 헤더 높이 */
  transform: translateX(100%); /* 기본적으로 오른쪽으로 숨겨져 있음 */
  transition: transform 0.3s ease-in-out;
  z-index: 999; /* 헤더보다 낮은 z-index */
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
  /* 디테일 페이지가 열렸을 때 모바일에서 */
  .content-wrapper.detail-open .post-detail {
    transform: translateY(0); /* 화면 안으로 완전히 들어오도록 */
  }

  /* 디테일 페이지가 열렸을 때 메인 콘텐츠 스크롤 방지 (선택 사항) */
  .content-wrapper.detail-open {
    overflow: hidden; /* 배경 콘텐츠 스크롤 방지 */
  }
  .post-detail {
    width: 100%;
    /* 모바일에서는 fixed로 상단에 고정합니다. */
    position: fixed;
    top: 56px; /* 헤더 아래에 위치 */
    left: 0;
    height: calc(100vh - 56px); /* 헤더를 제외한 전체 높이 */
    border-left: none;
    box-shadow: 0 -2px 10px rgba(0,0,0,0.1); /* 약간의 그림자 추가 */
    background: #fff; /* 배경색을 흰색으로 변경하여 콘텐츠를 가리도록 */
    transform: translateY(100%); /* 아래에서 위로 올라오도록 초기 위치 설정 */
    transition: transform 0.3s ease-in-out;
    z-index: 1000; /* 헤더(999)보다 높은 z-index로 최상단에 오게 합니다. */
    padding: 1.5rem; /* 모바일 패딩 조정 */
    box-sizing: border-box;
  }

}
</style>