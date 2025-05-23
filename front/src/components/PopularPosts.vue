<template>
  <aside class="popular-posts-sidebar">
    <h2 class="sidebar-title">인기 TOP 10</h2>
    <ol class="post-list">
      <li v-for="(post, index) in popularPosts" :key="post.id" class="post-item" @click="openPostDetail(post.id)">
        <span class="rank">{{ index + 1 }}.</span>
        <div class="post-info">
          <span class="post-category">{{ post.category }}</span>
          <p class="post-title">{{ post.title }}</p>
        </div>
      </li>
    </ol>
  </aside>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  // 'likes' 속성을 기준으로 정렬된 게시물 배열을 받습니다.
  posts: {
    type: Array,
    default: () => []
  }
});

const emit = defineEmits(['open-detail']);

// 좋아요 수를 숫자로 변환하여 정렬합니다. '1.2K'와 같은 문자열 처리 포함.
const parseLikes = (likesStr) => {
  if (typeof likesStr === 'number') return likesStr;
  if (!likesStr) return 0;
  likesStr = likesStr.replace(/,/g, '');
  if (likesStr.endsWith('K')) {
    return parseFloat(likesStr) * 1000;
  }
  return parseInt(likesStr);
};

// 상위 10개 인기 게시물 계산
const popularPosts = computed(() => {
  return [...props.posts]
    .sort((a, b) => parseLikes(b.likes) - parseLikes(a.likes))
    .slice(0, 10);
});

const openPostDetail = (postId) => {
  emit('open-detail', postId);
};
</script>

<style scoped>
.popular-posts-sidebar {
  width: 25%; /* 메인 콘텐츠 오른쪽 공간 */
  max-width: 300px; /* 최대 너비 설정 */
  background: #f7f9fb; /* 메인 콘텐츠와 동일한 배경색 */
  padding: 1.5rem 1.5vw; /* 내부 여백 */
  box-sizing: border-box;
  position: sticky; /* 스크롤 시 고정 */
  top: 56px; /* 헤더 높이만큼 아래로 */
  height: calc(100vh - 56px); /* 화면 높이에서 헤더 높이 제외 */
  overflow-y: auto; /* 내용이 많으면 스크롤 */
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  color: #354052;
  /* 기본적으로 우측에 붙어있지만, App.vue에서 flex-grow로 제어될 것임 */
}

.sidebar-title {
  font-size: 1.15rem;
  font-weight: 700;
  color: #232a33;
  margin-bottom: 1.5rem;
  padding-bottom: 0.8rem;
  border-bottom: 1px solid #eef0f3; /* 연한 하단 구분선 */
}

.post-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.post-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 1.2rem;
  cursor: pointer;
  padding: 0.5rem 0; /* 클릭 영역 확보 및 여백 */
  transition: background-color 0.2s;
}

.post-item:hover {
  background-color: #f0f2f5; /* 호버 시 배경색 변경 */
  border-radius: 0.3rem;
}

.rank {
  font-size: 1.1rem;
  font-weight: 700;
  color: #4f8cff;
  margin-right: 0.7rem;
  flex-shrink: 0; /* 순위가 줄어들지 않도록 */
  width: 1.5rem; /* 순위 숫자 너비 고정 */
  text-align: right;
}

.post-info {
  flex-grow: 1;
}

.post-category {
  font-size: 0.75rem;
  color: #7a8baa;
  margin-bottom: 0.2rem;
  display: block;
}

.post-title {
  font-size: 0.9rem;
  font-weight: 500;
  color: #232a33;
  line-height: 1.4;
  margin: 0;
  word-break: keep-all;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .popular-posts-sidebar {
    width: 100%;
    max-width: none;
    position: static; /* 모바일에서는 고정 해제 */
    height: auto;
    margin-top: 1.5rem; /* 메인 콘텐츠 아래에 나타나도록 여백 추가 */
    border-top: 1px solid #eef0f3; /* 메인 콘텐츠와의 구분선 */
    padding-top: 1.5rem;
  }
}
</style>