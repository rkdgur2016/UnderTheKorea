<template>
  <!-- 오버레이 배경 -->
  <div 
    v-if="post" 
    class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-md"
    @click.self="closeDetail"
  >
    <!-- 메인 모달 -->
    <div 
      :class="[
        'bg-white rounded-xl shadow-2xl max-w-4xl w-full max-h-[90vh] overflow-hidden transition-all duration-300 mx-auto',
        showDetail ? 'opacity-100 scale-100' : 'opacity-0 scale-95'
      ]"
    >
      <!-- 헤더 -->
      <div class="flex items-center justify-between p-6 border-b border-slate-200 bg-slate-50">
        <div class="flex items-center space-x-3">
          <div class="bg-slate-800 text-white px-3 py-1 rounded-full text-sm font-medium">
            {{ post.category }}
          </div>
          <span class="text-sm text-slate-500">{{ post.time }}</span>
        </div>
        <button 
          @click="closeDetail"
          class="text-slate-400 hover:text-slate-600 transition-colors"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>

      <!-- 스크롤 가능한 콘텐츠 영역 -->
      <div class="overflow-y-auto max-h-[calc(90vh-80px)]">
        <!-- 메인 콘텐츠를 2열로 분할 -->
        <div class="flex">
          <!-- 왼쪽: 게시물 내용 -->
          <div class="flex-1 p-6 border-r border-slate-200">
            <!-- 제목 -->
            <h1 class="text-2xl font-bold text-slate-800 mb-4 leading-tight">{{ post.title }}</h1>
            
            <!-- 미디어 콘텐츠 -->
            <div v-if="post.img || post.youtubeTumbnail" class="mb-6">
              <img 
                v-if="post.img" 
                :src="post.img" 
                :alt="post.title"
                class="w-full rounded-lg shadow-md"
              >
              <div v-else-if="post.youtubeTumbnail" class="relative">
                <img 
                  :src="post.youtubeTumbnail" 
                  :alt="post.title"
                  class="w-full rounded-lg shadow-md"
                >
                <div class="absolute inset-0 flex items-center justify-center">
                  <div class="bg-red-600 text-white p-3 rounded-full shadow-lg">
                    <svg class="w-8 h-8" fill="currentColor" viewBox="0 0 24 24">
                      <path d="M8 5v14l11-7z"/>
                    </svg>
                  </div>
                </div>
              </div>
            </div>

            <!-- 본문 -->
            <div class="prose max-w-none mb-6">
              <p class="text-slate-700 leading-relaxed whitespace-pre-wrap">{{ post.body }}</p>
            </div>

            <!-- 게시물 상호작용 버튼들 -->
            <div class="flex items-center justify-between mb-8 p-4 bg-slate-50 rounded-lg">
              <div class="flex items-center space-x-4">
                <!-- 추천 버튼 -->
                <button class="flex items-center space-x-2 px-4 py-2 bg-white border border-slate-200 rounded-lg hover:bg-slate-50 transition-colors">
                  <svg class="w-5 h-5 text-slate-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
                  </svg>
                  <span class="text-sm font-medium text-slate-700">추천 {{ post.likeCount }}</span>
                </button>

                <!-- 조회수 -->
                <div class="flex items-center space-x-2 text-slate-500">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                  <span class="text-sm">{{ post.viewcount }}</span>
                </div>
              </div>

              <!-- 투표 시작 버튼 -->
              <button 
                v-if="!currentVote"
                @click="startVote"
                class="px-6 py-2 bg-slate-800 text-white rounded-lg hover:bg-slate-700 transition-colors font-medium"
              >
                투표 시작하기
              </button>
            </div>

            <!-- 투표 생성 폼 -->
            <div v-if="showVoteCreation" class="mb-8 p-6 bg-blue-50 border border-blue-200 rounded-lg">
              <h3 class="text-lg font-semibold text-slate-800 mb-4">투표 생성</h3>
              
              <!-- 투표 제목 (게시글 제목 자동 입력) -->
              <div class="mb-4">
                <label class="block text-sm font-medium text-slate-700 mb-2">투표 제목</label>
                <input 
                  :value="post.title"
                  readonly
                  class="w-full px-3 py-2 bg-slate-100 border border-slate-300 rounded-md text-slate-600"
                >
              </div>

              <!-- 선택지 입력 -->
              <div class="mb-4">
                <label class="block text-sm font-medium text-slate-700 mb-2">선택지</label>
                <div class="space-y-3">
                  <div class="flex items-center space-x-3">
                    <span class="text-sm font-medium text-slate-600 w-12">선택 1</span>
                    <input 
                      v-model="voteOptions.agreeTitle"
                      type="text" 
                      placeholder="첫 번째 선택지를 입력하세요"
                      class="flex-1 px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    >
                  </div>
                  <div class="flex items-center space-x-3">
                    <span class="text-sm font-medium text-slate-600 w-12">선택 2</span>
                    <input 
                      v-model="voteOptions.disagreeTitle"
                      type="text" 
                      placeholder="두 번째 선택지를 입력하세요"
                      class="flex-1 px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    >
                  </div>
                </div>
              </div>

              <!-- 버튼들 -->
              <div class="flex justify-end space-x-3">
                <button 
                  @click="showVoteCreation = false"
                  class="px-4 py-2 text-slate-600 border border-slate-300 rounded-md hover:bg-slate-50 transition-colors"
                >
                  취소
                </button>
                <button 
                  @click="createVote"
                  class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors"
                >
                  투표 생성
                </button>
              </div>
            </div>

            <!-- 진행 중인 투표 또는 투표 결과 -->
            <div v-if="currentVote" class="mb-8 p-6 bg-slate-50 border border-slate-200 rounded-lg">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-slate-800">투표</h3>
                <div class="text-sm text-slate-500">
                  총 {{ totalVotes }}명 참여
                </div>
              </div>

              <!-- 투표 참여 (아직 투표하지 않은 경우) -->
              <div v-if="!currentVote.isVoted && totalVotes === 0" class="space-y-3">
                <button 
                  @click="castVote('agree')"
                  class="w-full p-3 text-left border border-slate-300 rounded-lg hover:bg-white hover:border-blue-300 transition-colors"
                >
                  {{ currentVote.agreeTitle }}
                </button>
                <button 
                  @click="castVote('disagree')"
                  class="w-full p-3 text-left border border-slate-300 rounded-lg hover:bg-white hover:border-blue-300 transition-colors"
                >
                  {{ currentVote.disagreeTitle }}
                </button>
              </div>

              <!-- 투표 결과 (투표 완료 후) -->
              <div v-else class="space-y-4">
                <!-- 첫 번째 선택지 결과 -->
                <div class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-slate-700">{{ currentVote.agreeTitle }}</span>
                    <span class="text-sm font-semibold text-slate-800">{{ agreePercentage.toFixed(1) }}%</span>
                  </div>
                  <div class="h-3 bg-slate-200 rounded-full overflow-hidden">
                    <div 
                      class="h-full bg-blue-500 rounded-full transition-all duration-500"
                      :style="{ width: `${agreePercentage}%` }"
                    ></div>
                  </div>
                  <div class="text-xs text-slate-500">{{ currentVote.agree }}명</div>
                </div>

                <!-- 두 번째 선택지 결과 -->
                <div class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-slate-700">{{ currentVote.disagreeTitle }}</span>
                    <span class="text-sm font-semibold text-slate-800">{{ disagreePercentage.toFixed(1) }}%</span>
                  </div>
                  <div class="h-3 bg-slate-200 rounded-full overflow-hidden">
                    <div 
                      class="h-full bg-red-500 rounded-full transition-all duration-500"
                      :style="{ width: `${disagreePercentage}%` }"
                    ></div>
                  </div>
                  <div class="text-xs text-slate-500">{{ currentVote.disagree }}명</div>
                </div>

                <!-- 투표 종료 정보 -->
                <div class="pt-3 border-t border-slate-200 text-xs text-slate-500">
                  투표 종료: 2024년 1월 15일 18:00
                </div>
              </div>
            </div>
          </div>

          <!-- 오른쪽: 댓글 섹션 -->
          <div class="w-96 p-6 bg-slate-50">
            <h3 class="text-lg font-semibold text-slate-800 mb-4">
              댓글 <span class="text-slate-500 font-normal">({{ comments.length }})</span>
            </h3>

            <!-- 댓글 입력 -->
            <div class="mb-6">
              <div class="space-y-3">
                <textarea 
                  placeholder="댓글을 입력하세요..."
                  rows="3"
                  class="w-full px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500 resize-none text-sm"
                ></textarea>
                <div class="flex justify-end">
                  <button class="px-4 py-2 bg-slate-800 text-white rounded-md hover:bg-slate-700 transition-colors text-sm">
                    댓글 작성
                  </button>
                </div>
              </div>
            </div>

            <!-- 댓글 목록 -->
            <div class="space-y-4 max-h-[60vh] overflow-y-auto">
              <div v-for="comment in comments" :key="comment.id" class="border-b border-slate-200 pb-4 last:border-b-0">
                <!-- 댓글 -->
                <div class="space-y-2">
                  <div class="flex items-start space-x-2">
                    <div class="flex-shrink-0">
                      <div class="w-6 h-6 bg-slate-300 rounded-full flex items-center justify-center">
                        <svg class="w-3 h-3 text-slate-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
                      </div>
                    </div>
                    <div class="flex-1 min-w-0">
                      <div class="flex items-center space-x-2 mb-1">
                        <span class="font-medium text-slate-800 text-sm">{{ comment.author }}</span>
                        <span class="text-xs text-slate-500">{{ comment.time }}</span>
                      </div>
                      <p class="text-slate-700 text-sm leading-relaxed">{{ comment.body }}</p>
                      <div class="flex items-center space-x-3 mt-2">
                        <button class="flex items-center space-x-1 text-xs text-slate-500 hover:text-slate-700">
                          <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
                          </svg>
                          <span>{{ comment.likes }}</span>
                        </button>
                        <button class="text-xs text-slate-500 hover:text-slate-700">답글</button>
                      </div>
                    </div>
                  </div>

                  <!-- 답글들 -->
                  <div v-if="comment.replies && comment.replies.length > 0" class="ml-8 space-y-2">
                    <div v-for="reply in comment.replies" :key="reply.id" class="flex items-start space-x-2">
                      <div class="flex-shrink-0">
                        <div class="w-5 h-5 bg-slate-200 rounded-full flex items-center justify-center">
                          <svg class="w-2 h-2 text-slate-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                          </svg>
                        </div>
                      </div>
                      <div class="flex-1 min-w-0">
                        <div class="flex items-center space-x-2 mb-1">
                          <span class="font-medium text-slate-800 text-xs">{{ reply.author }}</span>
                          <span class="text-xs text-slate-500">{{ reply.time }}</span>
                        </div>
                        <p class="text-slate-700 text-xs leading-relaxed">{{ reply.body }}</p>
                        <div class="flex items-center space-x-3 mt-1">
                          <button class="flex items-center space-x-1 text-xs text-slate-500 hover:text-slate-700">
                            <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.60L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
                            </svg>
                            <span>{{ reply.likes }}</span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits, computed } from 'vue';

const props = defineProps({
  postId: {
    type: Number,
    default: null
  },
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
const showVoteCreation = ref(false);
const voteOptions = ref({
  agreeTitle: '',
  disagreeTitle: ''
});
const currentVote = ref(null);

// 투표 총 개수 계산
const totalVotes = computed(() => {
  return (currentVote.value?.agree || 0) + (currentVote.value?.disagree || 0);
});

// 찬성 비율 계산 (bar 그래프에 사용)
const agreePercentage = computed(() => {
  if (totalVotes.value === 0) return 50;
  return (currentVote.value.agree / totalVotes.value) * 100;
});

// 반대 비율 계산
const disagreePercentage = computed(() => {
  if (totalVotes.value === 0) return 50;
  return (currentVote.value.disagree / totalVotes.value) * 100;
});

const fetchCommentsForPost = async (id) => {
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
  }, 300);
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
  
  currentVote.value = {
    agree: 0,
    disagree: 0,
    agreeTitle: voteOptions.value.agreeTitle,
    disagreeTitle: voteOptions.value.disagreeTitle,
    isVoted: false
  };
  showVoteCreation.value = false;
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
  currentVote.value.isVoted = true;
};

// props.post가 변경될 때 post.value를 업데이트하고 애니메이션 시작
watch(() => props.post, (newPost) => {
  post.value = newPost;
  if (newPost) {
    fetchCommentsForPost(newPost.id);
    setTimeout(() => {
      showDetail.value = true;
    }, 50);
    currentVote.value = null;
    showVoteCreation.value = false;
  } else {
    comments.value = [];
    showDetail.value = false;
    currentVote.value = null;
    showVoteCreation.value = false;
  }
}, { immediate: true });
</script>

<style scoped>
/* 스크롤바 스타일링 */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}

/* 부드러운 전환 효과 */
.transition-all {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 300ms;
}
</style>