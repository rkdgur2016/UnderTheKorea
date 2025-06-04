// PostDetail.txt (수정된 내용)
<script setup>
import { ref, watch, defineProps, defineEmits, computed } from 'vue'; // computed 추가

const props = defineProps({
  // postId는 댓글을 위한 데이터 fetching에 필요할 수 있습니다.
  postId: {
    type: Number,
    default: null
  },
  // PostView에서 넘겨받을 게시물 데이터 (핵심)
  post: {
    type: Object,
    default: null
  }
});

const emit = defineEmits(['close']);

const post = ref(props.post);
const comments = ref([]);
const showDetail = ref(false);

// 투표 관련 상태 추가
const showVoteCreation = ref(false); // 투표 생성 폼 표시 여부
const voteOptions = ref({ // 투표 옵션 (찬성/반대 소제목)
  agreeTitle: '',
  disagreeTitle: ''
});
const currentVote = ref(null); // 현재 진행 중인 투표 데이터 { agree: N, disagree: M, agreeTitle: '...', disagreeTitle: '...' }

// 투표 총 개수 계산
const totalVotes = computed(() => {
  return (currentVote.value?.agree || 0) + (currentVote.value?.disagree || 0);
});

// 찬성 비율 계산 (bar 그래프에 사용)
const agreePercentage = computed(() => {
  if (totalVotes.value === 0) return 50; // 투표가 없으면 50%로 초기화 또는 0%로 설정
  return (currentVote.value.agree / totalVotes.value) * 100;
});

// 반대 비율 계산
const disagreePercentage = computed(() => {
  if (totalVotes.value === 0) return 50;
  return (currentVote.value.disagree / totalVotes.value) * 100;
});

const fetchCommentsForPost = async (id) => {
  // 실제 API 호출 로직을 여기에 구현합니다.
  // 여기서는 postId에 맞는 더미 댓글 데이터를 사용합니다.
  if (id === 1) {
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
  } else if (id === 2) {
    comments.value = [
      {
        id: 3,
        author: '시민123',
        time: '1시간 전',
        body: '정책 방향이 명확해서 좋네요.',
        likes: 22,
        replies: []
      }
    ];
  } else {
    comments.value = [];
  }
};

const closeDetail = () => {
  showDetail.value = false;
  setTimeout(() => {
    emit('close');
  }, 300); // 애니메이션 시간과 맞춰서 컴포넌트 언마운트
};

// 투표 생성 폼 열기
const startVote = () => {
  showVoteCreation.value = true;
};

// 투표 생성 제출
const createVote = () => {
  if (!voteOptions.value.agreeTitle || !voteOptions.value.disagreeTitle) {
    alert('찬성과 반대 소제목을 모두 입력해주세요.');
    return;
  }
  // 실제로는 여기서 백엔드로 투표 생성 요청을 보냅니다.
  // 여기서는 프론트엔드에서 더미 데이터로 투표를 시작합니다.
  currentVote.value = {
    agree: 0,
    disagree: 0,
    agreeTitle: voteOptions.value.agreeTitle,
    disagreeTitle: voteOptions.value.disagreeTitle,
    isVoted: false // 사용자가 이 투표에 참여했는지 여부 (더미)
  };
  showVoteCreation.value = false; // 폼 닫기
  // voteOptions 초기화
  voteOptions.value = { agreeTitle: '', disagreeTitle: '' };
};

// 투표하기
const castVote = (type) => {
  if (!currentVote.value) return;
  if (currentVote.value.isVoted) {
    alert('이미 투표하셨습니다.');
    return;
  }

  if (type === 'agree') {
    currentVote.value.agree++;
  } else if (type === 'disagree') {
    currentVote.value.disagree++;
  }
  currentVote.value.isVoted = true; // 투표 완료 처리 (더미)
  // 실제로는 백엔드로 투표 요청을 보내고, 결과를 업데이트합니다.
};

// props.post가 변경될 때 post.value를 업데이트하고 애니메이션 시작
watch(() => props.post, (newPost) => {
  post.value = newPost;
  if (newPost) {
    // 게시물 데이터가 있을 때만 댓글을 가져오고 애니메이션 시작
    fetchCommentsForPost(newPost.id); // 게시물 ID로 댓글 가져오기
    setTimeout(() => {
      showDetail.value = true;
    }, 50);
    // 새 게시물이 로드될 때 기존 투표 정보 초기화 (필요시)
    currentVote.value = null;
    showVoteCreation.value = false;
  } else {
    // 게시물 데이터가 null이 되면 댓글도 초기화하고 애니메이션 비활성화
    comments.value = [];
    showDetail.value = false;
    currentVote.value = null;
    showVoteCreation.value = false;
  }
}, { immediate: true });
// 컴포넌트가 처음 마운트될 때도 실행
</script>

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
            :src="`https://www.youtube.com/embed/${post.youtube}`" frameborder="0"
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
          <button class="action-btn">💬 {{ post.commentsCount }}</button>
          <button class="action-btn">🔗 공유</button>
          <button v-if="!currentVote" class="action-btn vote-start-btn" @click="startVote">
            💬 투표 시작하기
          </button>
        </div>

        <div v-if="showVoteCreation" class="vote-creation-section">
          <h2>새로운 투표 시작</h2>
          <p>게시물 제목: **{{ post.title }}**</p>
          <div class="input-group">
            <label for="agreeTitle">찬성 입장 소제목:</label>
            <input type="text" id="agreeTitle" v-model="voteOptions.agreeTitle" placeholder="예: 법안 발의에 찬성하는 입장" />
          </div>
          <div class="input-group">
            <label for="disagreeTitle">반대 입장 소제목:</label>
            <input type="text" id="disagreeTitle" v-model="voteOptions.disagreeTitle" placeholder="예: 법안 발의에 반대하는 입장" />
          </div>
          <button class="create-vote-btn" @click="createVote">투표 생성</button>
        </div>

        <div v-if="currentVote" class="vote-section">
          <h2>토론 투표: {{ post.title }}</h2>
          <div class="vote-bar-container">
            <div class="vote-bar-fill agree-fill" :style="{ width: agreePercentage + '%' }"></div>
          </div>
          <div class="vote-counts">
            <span class="agree-count">{{ currentVote.agreeTitle }}: {{ currentVote.agree }}표</span>
            <span class="disagree-count">{{ currentVote.disagreeTitle }}: {{ currentVote.disagree }}표</span>
          </div>
          <div class="vote-buttons">
            <button class="vote-btn agree-btn" @click="castVote('agree')" :disabled="currentVote.isVoted">
              {{ currentVote.agreeTitle }} ({{ agreePercentage.toFixed(1) }}%)
            </button>
            <button class="vote-btn disagree-btn" @click="castVote('disagree')" :disabled="currentVote.isVoted">
              {{ currentVote.disagreeTitle }} ({{ disagreePercentage.toFixed(1) }}%)
            </button>
          </div>
          <p v-if="currentVote.isVoted" class="vote-status-message">이미 투표에 참여하셨습니다.</p>
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
  padding: 2rem; /* 추가 */
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


.post-actions {
  /* 기존 스타일 유지 */
  display: flex;
  justify-content: flex-end; /* 버튼을 오른쪽으로 정렬 */
  align-items: center;
  gap: 0.53em;
  margin-top: 0.11em;
  padding-top: 1rem; /* 댓글 섹션과 분리 */
  border-top: 1px solid #eee; /* 구분선 추가 */
  margin-top: 1rem;
}

.vote-start-btn {
  background: #28a745; /* 투표 시작 버튼 색상 */
  color: #fff;
  border: 1px solid #28a745;
  padding: 0.5em 1em;
  border-radius: 0.3em;
  cursor: pointer;
  font-size: 0.85rem;
  transition: background 0.2s;
}

.vote-start-btn:hover {
  background: #218838;
}

.vote-creation-section,
.vote-section {
  background: #fdfdfd;
  border: 1px solid #f0f2f5;
  border-radius: 0.5rem;
  padding: 1.5rem;
  margin-top: 1.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.03);
}

.vote-creation-section h2,
.vote-section h2 {
  font-size: 1.2rem;
  color: #232a33;
  margin-bottom: 1rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.5rem;
}

.vote-creation-section p {
  font-size: 0.95rem;
  color: #555;
  margin-bottom: 1rem;
}

.input-group {
  margin-bottom: 1rem;
}

.input-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #354052;
}

.input-group input[type="text"] {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #ccc;
  border-radius: 0.4rem;
  font-size: 0.9rem;
  box-sizing: border-box;
}

.create-vote-btn {
  background: #007bff;
  color: #fff;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 0.4rem;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.2s;
}

.create-vote-btn:hover {
  background: #0056b3;
}

.vote-bar-container {
  width: 100%;
  height: 20px;
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  margin-top: 1rem;
  margin-bottom: 0.5rem;
  display: flex; /* 찬성/반대 바를 나란히 배치 */
}

.vote-bar-fill {
  height: 100%;
  transition: width 0.5s ease-in-out;
  flex-shrink: 0; /* 내용이 줄어들 때 줄어들지 않도록 */
}

.agree-fill {
  background-color: #4CAF50; /* 찬성 색상 */
}

/* 반대 바는 따로 flex item으로 만들지 않고, 찬성 바가 채우고 남은 공간이 자동으로 반대 영역이 되도록 합니다. */
/* 또는 아래처럼 명시적으로 반대 바를 추가할 수도 있습니다. */
/* .disagree-fill {
  background-color: #f44336; /* 반대 색상 
} */


.vote-counts {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #555;
  margin-bottom: 1.5rem;
}

.vote-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.vote-btn {
  flex: 1;
  padding: 0.75rem 1rem;
  border: none;
  border-radius: 0.4rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s, transform 0.1s;
  font-weight: 600;
}

.vote-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.agree-btn {
  background-color: #4CAF50;
  color: white;
}

.agree-btn:hover:not(:disabled) {
  background-color: #45a049;
  transform: translateY(-2px);
}

.disagree-btn {
  background-color: #f44336;
  color: white;
}

.disagree-btn:hover:not(:disabled) {
  background-color: #da190b;
  transform: translateY(-2px);
}

.vote-status-message {
  text-align: center;
  margin-top: 1rem;
  color: #666;
  font-style: italic;
}

</style>