// PostView.txt
<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { defineEmits } from 'vue';

import axios from 'axios';
import PostDetail from './PostDetail.vue';

const route = useRoute();
const posts = ref([]); // 백엔드에서 받아와서 매핑된 게시물 데이터를 저장할 반응형 변수
const loading = ref(false);
const error = ref(null);
const currentCategory = ref('');

// PostDetail 관련 상태 추가
const showPostDetail = ref(false);
const selectedPostId = ref(null);
const selectedPost = ref(null);
// 클릭된 게시물 전체 객체를 저장할 ref


const emit = defineEmits(['open-detail']);

const openDetail = (postItem) => {
  emit('open-detail', postItem); // 부모에게 postItem 전체를 전달
};

// 시간 포맷팅 함수 (템플릿의 post.time에 매핑)
const formatTime = (isoString) => {
  if (!isoString) return '';
  const date = new Date(isoString);
  const now = new Date();
  const diffMinutes = Math.floor((now - date) / (1000 * 60));
  const diffHours = Math.floor(diffMinutes / 60);
  const diffDays = Math.floor(diffHours / 24);

  if (diffMinutes < 1) return '방금 전';
  if (diffMinutes < 60) return `${diffMinutes}분 전`;
  if (diffHours < 24) return `${diffHours}시간 전`;
  if (diffDays < 7) return `${diffDays}일 전`;

  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${year}년 ${month}월 ${day}일`;
};

// 게시물 데이터를 백엔드에서 가져오고 템플릿 형식에 맞게 매핑하는 함수
const fetchPosts = async (category) => {
  loading.value = true;
  error.value = null;
  currentCategory.value = category;

  try {
    const response = await axios.get('/post/loadPosts', {
      params: {
        category: category
      }
    });
    // 받은 데이터를 템플릿이 사용하는 이름으로 매핑합니다.
    posts.value = response.data.map(item => {
      
      return {
        id: item.postId, // postid -> post.id (클릭 이벤트 및 key)
        category: item.category, // category -> post.category
        time: formatTime(item.createdAt), // createdat -> post.time (포맷팅 적용)
        title: item.title, // title -> post.title
        youtube: item.videoUrl, // videourl -> post.youtube (YouTube 비디오 ID만 있다고
        img: item.imageUrl, // imageurl -> post.img (이미지 경로 조정 필요)
        body: item.content, // content -> post.body
        likes: item.likes || 0, // DB에 likes 컬럼이 없으므로 임의로 0, 백엔드에서 전달되면 해당 값 사용
        comments: item.comments || 0, // DB에 comments 컬럼이 없으므로 임의로 0, 백엔드에서 전달되면 해당 값 사용
        viewcount: item.viewCount || 0 // viewcount는 기존 컬럼이므로 그대로 사용
      };
 
    });
    console.log('게시물 데이터:', posts.value); // 디버깅용 로그

  } catch (err) {
    console.error('게시물 로드 중 오류 발생:', err);
    error.value = '게시물을 불러오는데 실패했습니다. 서버 상태를 확인해주세요.';
  } finally {
    loading.value = false;
  }
};
onMounted(() => {
  const initialCategory = route.query.category || '철학';
  fetchPosts(initialCategory);
});
watch(() => route.query.category, (newCategory, oldCategory) => {
  if (newCategory && newCategory !== oldCategory) {
    fetchPosts(newCategory);
  } else if (!newCategory && oldCategory) {
    fetchPosts('철학');
  }
}, { immediate: true });
// --- 데이터 로딩 관련 스크립트 끝 ---
</script>

<template>
  <main class="kr-social-issue">
    <section
      v-for="postItem in posts" :key="postItem.id"
      class="post-card"
      @click="openDetail(postItem)" >
      <div class="post-header">
        <span class="category">{{ postItem.category }}</span>
        <span class="dot">·</span>
        <span class="time">{{ postItem.time }}</span>
      </div>
      <h1 class="post-title">{{ postItem.title }}</h1>
      <div v-if="postItem.youtube" class="post-media">
        <iframe
          :src="`http://www.youtube.com/embed/${postItem.youtube}`" frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
          title="YouTube video player"
        ></iframe>
      </div>
      <div v-else-if="postItem.img" class="post-img">
        <img :src="postItem.img" :alt="postItem.title" />
      </div>
      <div class="post-body">{{ postItem.body }}</div>
      <div class="post-actions">
        <button class="action-btn">👍 {{ postItem.likes }}</button>
        <button class="action-btn">💬 {{ postItem.comments }}</button>
        <button class="action-btn">🔗 공유</button>
      </div>
    </section>

    <div v-if="loading" class="loading-message">게시물을 불러오는 중...</div>
    <div v-if="error" class="error-message">{{ error }}</div>
    <div v-if="!loading && !error && posts.length === 0" class="no-posts-message">
      선택된 카테고리에 게시물이 없습니다.
    </div>
  </main>
</template>

<style scoped>
/* PoliticsView.txt 에 있던 모든 스타일 코드를 여기에 복사합니다. */
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.kr-social-issue {
  background: #f7f9fb; /* 이 배경색은 PostList 내부의 배경색입니다. */
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  padding-top: 1.9rem;
  font-size: 0.75rem;
  flex-grow: 1;
  width: 100%;
  margin: 0;
  padding-left: 0;
}

.post-card {
  background: #fff;
  border-radius: 0.66rem;
  box-shadow: 0 0.094rem 0.75rem rgba(60, 100, 170, 0.08);
  border: 0.07rem solid #e3e6ea;
  max-width: 30rem;
  width: 98vw;
  padding: 1.5rem 3vw 0.94rem 3vw;
  margin: 0 0 1.7rem 0;
  display: flex;
  flex-direction: column;
  cursor: pointer;
}

.post-header {
  color: #7a8baa;
  font-size: 0.76em;
  margin-bottom: 0.38em;
  display: flex;
  align-items: center;
  gap: 0.38em;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
}
.category {
  color: #4f8cff;
  font-weight: 600;
  font-size: 0.77em;
}
.dot {
  font-size: 0.83em;
  color: #b6bec8;
}
.time {
  color: #a6b0bc;
  font-size: 0.74em;
}

.post-title {
  color: #232a33;
  font-size: 0.94rem;
  font-weight: 700;
  margin-bottom: 0.83em;
  margin-top: 0;
  letter-spacing: -0.02em;
  line-height: 1.4;
}

.post-body {
  font-size: 0.81rem;
  color: #354052;
  margin-bottom: 0.83em;
  line-height: 1.8;
  word-break: keep-all;
}

.post-actions {
  display: flex;
  gap: 0.53em;
  margin-top: 0.11em;
  justify-content: flex-end;
}

.action-btn {
  background: #f4f8fd;
  color: #4f8cff;
  border: 0.07rem solid #e7ecf3;
  border-radius: 0.28rem;
  padding: 0.33em 0.94em;
  font-size: 0.75rem;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  cursor: pointer;
  transition: background 0.16s, color 0.16s, border 0.16s;
}
.action-btn:hover {
  background: #4f8cff;
  color: #fff;
  border: 0.07rem solid #4f8cff;
}

.post-media {
  width: 100%;
  aspect-ratio: 16 / 9;
  margin-bottom: 0.83em;
  border-radius: 0.66rem;
  overflow: hidden;
  background: #000;
  display: flex;
  align-items: center;
  justify-content: center;
}
.post-media iframe {
  width: 100%;
  height: 100%;
  border: none;
  display: block;
  background: #000;
}

@media (max-width: 700px) {
  .post-card {
    padding: 0.83rem 3vw 0.56rem 3vw;
    max-width: 74.25vw;
  }
  .post-title {
    font-size: 0.77rem;
  }
  .post-body {
    font-size: 0.73rem;
  }
}

/* 로딩 및 에러 메시지 스타일 (선택 사항) */
.loading-message, .error-message, .no-posts-message {
  text-align: center;
  margin-top: 20px;
  font-size: 1rem;
  color: #555;
}

.error-message {
  color: #d9534f;
}
</style>