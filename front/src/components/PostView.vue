<template>
  <main class="kr-social-issue">
    <section
      v-for="post in posts"
      :key="post.id"
      class="post-card"
      @click="openDetail(post.id)"
    >
      <div class="post-header">
        <span class="category">{{ post.category }}</span>
        <span class="dot">·</span>
        <span class="time">{{ post.time }}</span>
      </div>
      <h1 class="post-title">{{ post.title }}</h1>
      <div v-if="post.youtube" class="post-media">
        <iframe
          :src="`https://www.youtube.com/embed/${post.youtube}`"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
          title="YouTube video player"
        ></iframe>
      </div>
      <div v-else-if="post.img" class="post-img">
        <img :src="post.img" :alt="post.title" />
      </div>
      <div class="post-body">{{ post.body }}</div>
      <div class="post-actions">
        <button class="action-btn">👍 {{ post.likes }}</button>
        <button class="action-btn">💬 {{ post.comments }}</button>
        <button class="action-btn">🔗 공유</button>
      </div>
    </section>
  </main>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

// 부모 컴포넌트로부터 posts 데이터를 props로 받습니다.
const props = defineProps({
  posts: {
    type: Array,
    required: true
  }
});

// 게시물 클릭 시 App.vue로 이벤트를 전달하기 위한 defineEmits
const emit = defineEmits(['open-detail']);

const openDetail = (postId) => {
  emit('open-detail', postId); // 클릭된 게시물의 ID를 상위 컴포넌트로 전달
  console.log('PostList: Emitting open-detail with ID:', postId);
};
</script>

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
</style>