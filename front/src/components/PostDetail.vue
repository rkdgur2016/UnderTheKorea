<template>
  <div class="post-detail-overlay" @click.self="closeDetail">
    <div class="post-detail-card" :class="{ 'slide-in': showDetail }">
      <button class="close-btn" @click="closeDetail">✖</button>
      <div v-if="post" class="detail-content">
        <div class="post-header">
          <span class="category">{{ post.category }}</span>
          <span class="dot">·</span>
          <span class="time">{{ post.time }}</span>
        </div>
        <h1 class="post-title">{{ post.title }}</h1>
        <div v-if="post.youtube" class="post-media">
          <iframe
            :src="`https://www.youtube.com/embed/$${post.youtube}`"
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

        <div class="comments-section">
          <h2>댓글</h2>
          <div class="comment-input-area">
            <textarea placeholder="댓글을 작성하세요..." rows="3"></textarea>
            <button class="submit-comment-btn">댓글 작성</button>
          </div>
          <div v-for="comment in comments" :key="comment.id" class="comment-card">
            <div class="comment-header">
              <span class="comment-author">{{ comment.author }}</span>
              <span class="comment-time">{{ comment.time }}</span>
            </div>
            <p class="comment-body">{{ comment.body }}</p>
            <div class="comment-actions">
              <button class="comment-action-btn">👍 {{ comment.likes }}</button>
              <button class="comment-action-btn">💬 답글</button>
            </div>
            <div v-if="comment.replies && comment.replies.length" class="comment-replies">
              <div v-for="reply in comment.replies" :key="reply.id" class="comment-card reply">
                <div class="comment-header">
                  <span class="comment-author">{{ reply.author }}</span>
                  <span class="comment-time">{{ reply.time }}</span>
                </div>
                <p class="comment-body">{{ reply.body }}</p>
                <div class="comment-actions">
                  <button class="comment-action-btn">👍 {{ reply.likes }}</button>
                  <button class="comment-action-btn">💬 답글</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="loading-message">
        게시물 내용을 불러오는 중...
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';

const props = defineProps({
  postId: {
    type: Number,
    default: null
  }
});

const emit = defineEmits(['close']);

const post = ref(null);
const comments = ref([]);
const showDetail = ref(false);

const fetchPostDetail = async (id) => {
  // 실제 API 호출 로직을 여기에 구현합니다.
  // 여기서는 더미 데이터를 사용합니다.
  const allPosts = [
    {
      id: 1,
      category: '정치',
      time: '1시간 전',
      title: '국회 본회의, 쟁점 법안 표결 현장 중계',
      body: '오늘 국회에서는 주요 쟁점 법안이 표결에 부쳐졌습니다. 여야의 치열한 논쟁과 현장 분위기를 영상으로 확인하세요.',
      likes: '1.1K',
      comments: '320',
      youtube: 'i97Gkcecqd0'
    },
    {
      id: 2,
      category: '정치',
      time: '3시간 전',
      title: '대통령 기자회견, 주요 발언 정리',
      body: '대통령이 오늘 오전 기자회견을 열고 경제 정책과 외교 현안에 대해 입장을 밝혔습니다. 주요 발언을 영상으로 확인하세요.',
      likes: '2.3K',
      comments: '410',
      youtube: 'lwCJOC9HWds'
    },
    // ...PoliticsView.vue의 posts 데이터를 여기에 모두 포함하거나,
    // 실제 백엔드에서 해당 ID의 게시물을 가져오도록 구현합니다.
  ];
  post.value = allPosts.find(p => p.id === id);

  // 더미 댓글 데이터
  comments.value = [
    {
      id: 1,
      author: '익명1',
      time: '5분 전',
      body: '정말 중요한 내용이네요. 시청 잘 했습니다.',
      likes: 15,
      replies: [
        { id: 1.1, author: '관리자', time: '2분 전', body: '관심 가져주셔서 감사합니다.', likes: 3 }
      ]
    },
    {
      id: 2,
      author: '궁금러',
      time: '10분 전',
      body: '다음 논의는 언제쯤 진행될까요?',
      likes: 8,
      replies: []
    }
  ];

  // 애니메이션을 위한 타이머
  setTimeout(() => {
    showDetail.value = true;
  }, 50); // 약간의 딜레이 후 슬라이드 인
};

const closeDetail = () => {
  showDetail.value = false;
  setTimeout(() => {
    emit('close');
  }, 300); // 애니메이션 시간과 맞춰서 컴포넌트 언마운트
};

watch(() => props.postId, (newId) => {
  if (newId) {
    fetchPostDetail(newId);
  } else {
    post.value = null;
    comments.value = [];
    showDetail.value = false;
  }
}, { immediate: true });
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.post-detail-overlay {
  position: fixed; /* 전체 화면 오버레이 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 어둡게 처리 */
  display: flex;
  justify-content: flex-end; /* 오른쪽으로 붙여서 표시 */
  z-index: 1000; /* 헤더보다 위에 오도록 */
}

.post-detail-card {
  background: #f7f9fb; /* PoliticsView 배경색과 유사 */
  width: 100%; /* 오른쪽 공백에 띄울 너비 */
  max-width: 700px; /* 최대 너비 제한 */
  height: 100%;
  overflow-y: auto; /* 내용이 길어지면 스크롤 */
  box-sizing: border-box;
  transform: translateX(100%); /* 초기에는 화면 밖에 위치 */
  transition: transform 0.3s ease-out; /* 슬라이드 인 애니메이션 */
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  position: relative;
}

.post-detail-card.slide-in {
  transform: translateX(0); /* 화면 안으로 슬라이드 인 */
}

.close-btn {
  position: absolute;
  top: 1.5rem;
  right: 1.5rem; /* 오른쪽 상단으로 이동 */
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #7a8baa;
  cursor: pointer;
  z-index: 10; /* 내용 위에 오도록 */
}

.close-btn:hover {
  color: #4f8cff;
}

.detail-content {
  /* PoliticsView와 유사한 스타일 재사용 */
  background: #fff;
  padding: 1.5rem; /* 2rem x 0.75 */ 
  margin-bottom: 2rem; /* 댓글 섹션과 간격 */
  border-radius: 0.66rem;
  box-shadow: 0 0.094rem 0.75rem rgba(60, 100, 170, 0.08);
  border: 0.07rem solid #e3e6ea;  
}

/* PoliticsView의 스타일을 복사하거나, 전역 CSS로 관리하여 재사용 */
/* 예시로 필요한 부분만 복사합니다. */
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

.post-actions {
  display: flex;
  gap: 0.53em; 
  margin-top: 0.11em; 
  justify-content: flex-end; /* 오른쪽 정렬 */
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


/* 댓글 섹션 스타일 */
.comments-section {
  background: #fff;
  border-radius: 0.66rem;
  box-shadow: 0 0.094rem 0.75rem rgba(60, 100, 170, 0.08);
  border: 0.07rem solid #e3e6ea;
  padding: 1.5rem;
}

.comments-section h2 {
  font-size: 1.2rem;
  color: #232a33;
  margin-bottom: 1.5rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.5rem;
}

.comment-input-area {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  margin-bottom: 2rem;
}

.comment-input-area textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #e3e6ea;
  border-radius: 0.28rem;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 0.85rem;
  color: #354052;
  resize: vertical;
  min-height: 80px;
}

.comment-input-area textarea:focus {
  outline: none;
  border-color: #4f8cff;
}

.submit-comment-btn {
  align-self: flex-end; /* 오른쪽 정렬 */
  background: #4f8cff;
  color: #fff;
  border: none;
  border-radius: 0.28rem;
  padding: 0.6rem 1.2rem;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s;
}

.submit-comment-btn:hover {
  background: #3a75e0;
}

.comment-card {
  background: #fdfdfd;
  border-radius: 0.5rem;
  border: 1px solid #f0f2f5;
  padding: 1rem 1.2rem;
  margin-bottom: 1rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.03);
}

.comment-card.reply {
  margin-left: 2rem; /* 답글 들여쓰기 */
  border-left: 3px solid #d4e1f5;
  background: #f7f9fb;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.comment-author {
  font-weight: 700;
  color: #232a33;
  font-size: 0.9rem;
}

.comment-time {
  color: #a6b0bc;
  font-size: 0.75rem;
}

.comment-body {
  color: #354052;
  font-size: 0.85rem;
  line-height: 1.6;
  margin-bottom: 0.8rem;
}

.comment-actions {
  display: flex;
  gap: 0.5rem;
  font-size: 0.75rem;
}

.comment-action-btn {
  background: #f4f8fd;
  color: #7a8baa;
  border: 1px solid #e7ecf3;
  border-radius: 0.2rem;
  padding: 0.3em 0.7em;
  cursor: pointer;
  transition: background 0.16s, color 0.16s, border 0.16s;
  font-size: 0.8em; /* 부모 폰트 사이즈에 비례 */
}

.comment-action-btn:hover {
  background: #e9eff7;
  color: #4f8cff;
  border-color: #d4e1f5;
}

.comment-replies {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px dashed #eee;
}

/* 반응형 디자인 */
@media (max-width: 900px) {
  .post-detail-card {
    width: 100%; /* 모바일에서는 전체 너비 사용 */
    padding: 1.5rem 1rem;
  }
}
</style>