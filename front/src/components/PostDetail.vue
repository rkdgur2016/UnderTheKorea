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
const showDetail = ref(false); // 오버레이 애니메이션 제어용 

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
}, { immediate: true }); // 컴포넌트가 처음 마운트될 때도 실행 
</script>

<template>
  <div
    class="fixed inset-0 bg-black bg-opacity-50 flex justify-end z-50"
    @click.self="closeDetail"
  >
    <div
      class="bg-[#f7f9fb] w-full max-w-[700px] h-full overflow-y-auto box-border
             transform translate-x-full transition-transform duration-300 ease-out font-pretendard p-8"
      :class="{ '!translate-x-0': showDetail }"
    >
      <button class="absolute top-6 left-6 bg-none border-none text-2xl text-slate-400 cursor-pointer z-10 hover:text-blue-500" @click="closeDetail">
        ✖
      </button>

      <div v-if="post" class="detail-content bg-white p-6 mb-8 rounded-lg shadow-md border border-gray-200">
        <div class="flex items-center gap-1 text-slate-400 text-xs mb-1">
          <span class="text-blue-500 font-semibold text-[0.77em]">{{ post.category }}</span>
          <span class="text-slate-300 text-sm">·</span>
          <span class="text-slate-400 text-xs">{{ post.time }}</span>
        </div>
        <h1 class="text-gray-900 text-xl font-bold mb-3 leading-tight tracking-tight-2px mt-0">
          {{ post.title }}
        </h1>

        <div v-if="post.youtube" class="w-full aspect-video mb-3 rounded-lg overflow-hidden bg-black flex items-center justify-center">
          <iframe
            :src="`https://www.youtube.com/embed/${post.youtube.split('v=')[1]}`" frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
            allowfullscreen
            title="YouTube video player"
            class="w-full h-full block border-none bg-black"
          ></iframe>
        </div>
        <div v-else-if="post.img" class="w-full aspect-video mb-3 rounded-lg overflow-hidden flex items-center justify-center">
          <img :src="post.img" :alt="post.title" class="w-full h-full object-cover" />
        </div>

        <div class="text-gray-700 text-base leading-relaxed mb-3 break-keep">
          {{ post.body }}
        </div>

        <div class="flex justify-end items-center gap-2 mt-4 pt-4 border-t border-gray-200">
          <button class="bg-blue-50 text-blue-500 border border-blue-100 rounded-md py-1.5 px-4 text-sm cursor-pointer transition-all duration-150 hover:bg-blue-500 hover:text-white hover:border-blue-500">
            👍 {{ post.likes }}
          </button>
          <button class="bg-blue-50 text-blue-500 border border-blue-100 rounded-md py-1.5 px-4 text-sm cursor-pointer transition-all duration-150 hover:bg-blue-500 hover:text-white hover:border-blue-500">
            💬 {{ post.commentsCount }}
          </button>
          <button class="bg-blue-50 text-blue-500 border border-blue-100 rounded-md py-1.5 px-4 text-sm cursor-pointer transition-all duration-150 hover:bg-blue-500 hover:text-white hover:border-blue-500">
            🔗 공유
          </button>
          <button v-if="!currentVote" class="bg-green-500 text-white border border-green-500 py-2 px-4 rounded-md cursor-pointer text-sm transition-all duration-200 hover:bg-green-600" @click="startVote">
            💬 투표 시작하기
          </button>
        </div>

        <div v-if="showVoteCreation" class="bg-white border border-gray-200 rounded-lg p-6 mt-6 shadow-sm">
          <h2 class="text-xl text-gray-900 mb-4 border-b border-gray-200 pb-2">새로운 투표 시작</h2>
          <p class="text-base text-gray-600 mb-4">게시물 제목: <span class="font-bold">{{ post.title }}</span></p> 
          <div class="mb-4">
            <label for="agreeTitle" class="block font-semibold mb-2 text-gray-700">찬성 입장 소제목:</label>
            <input type="text" id="agreeTitle" v-model="voteOptions.agreeTitle" placeholder="예: 법안 발의에 찬성하는 입장" class="w-full p-3 border border-gray-300 rounded-md text-base box-border focus:outline-none focus:ring-2 focus:ring-blue-400" />
          </div>
          <div class="mb-4">
            <label for="disagreeTitle" class="block font-semibold mb-2 text-gray-700">반대 입장 소제목:</label> 
            <input type="text" id="disagreeTitle" v-model="voteOptions.disagreeTitle" placeholder="예: 법안 발의에 반대하는 입장" class="w-full p-3 border border-gray-300 rounded-md text-base box-border focus:outline-none focus:ring-2 focus:ring-blue-400" />
          </div>
          <button class="bg-blue-600 text-white border-none py-3 px-6 rounded-md cursor-pointer text-lg transition-all duration-200 hover:bg-blue-700" @click="createVote">
            투표 생성
          </button>
        </div>

        <div v-if="currentVote" class="bg-white border border-gray-200 rounded-lg p-6 mt-6 shadow-sm">
          <h2 class="text-xl text-gray-900 mb-4 border-b border-gray-200 pb-2">토론 투표: {{ post.title }}</h2>
          <div class="w-full h-5 bg-gray-300 rounded-full overflow-hidden mt-4 mb-2 flex">
            <div class="bg-green-500 h-full transition-all duration-500 flex-shrink-0" :style="{ width: agreePercentage + '%' }"></div>
             <div class="bg-red-500 h-full transition-all duration-500 flex-shrink-0" :style="{ width: disagreePercentage + '%' }"></div> 
          </div>
          <div class="flex justify-between text-base text-gray-600 mb-6">
            <span class="agree-count">{{ currentVote.agreeTitle }}: {{ currentVote.agree }}표</span>
            <span class="disagree-count">{{ currentVote.disagreeTitle }}: {{ currentVote.disagree }}표</span>
          </div>
          <div class="flex gap-4 justify-center">
            <button class="flex-1 py-3 px-4 border-none rounded-md text-lg cursor-pointer transition-all duration-200 font-semibold bg-green-500 text-white hover:bg-green-600 hover:scale-105 disabled:opacity-60 disabled:cursor-not-allowed" @click="castVote('agree')" :disabled="currentVote.isVoted"> 
              {{ currentVote.agreeTitle }} ({{ agreePercentage.toFixed(1) }}%)
            </button>
            <button class="flex-1 py-3 px-4 border-none rounded-md text-lg cursor-pointer transition-all duration-200 font-semibold bg-red-500 text-white hover:bg-red-600 hover:scale-105 disabled:opacity-60 disabled:cursor-not-allowed" @click="castVote('disagree')" :disabled="currentVote.isVoted">
              {{ currentVote.disagreeTitle }} ({{ disagreePercentage.toFixed(1) }}%)
            </button>
          </div>
          <p v-if="currentVote.isVoted" class="text-center mt-4 text-gray-600 italic">이미 투표에 참여하셨습니다.</p> 
        </div>

        <div class="bg-white rounded-lg shadow-md border border-gray-200 p-6 mt-8">
          <h2 class="text-xl text-gray-900 mb-6 border-b border-gray-200 pb-2">댓글</h2>
          <div class="flex flex-col gap-3 mb-8">
            <textarea placeholder="댓글을 작성하세요..." rows="3" class="w-full p-3 border border-gray-300 rounded-md font-pretendard text-base text-gray-700 resize-y min-h-[80px] focus:outline-none focus:border-blue-500"></textarea> 
            <button class="self-end bg-blue-500 text-white border-none rounded-md py-2.5 px-5 text-lg cursor-pointer transition-colors duration-200 hover:bg-blue-600">
              댓글 작성
            </button>
          </div>
          <div v-for="comment in comments" :key="comment.id" class="bg-white rounded-lg border border-gray-100 p-4 mb-4 shadow-sm">
            <div class="flex items-center gap-2 mb-2"> 
              <span class="font-bold text-gray-900 text-base">{{ comment.author }}</span>
              <span class="text-slate-400 text-xs">{{ comment.time }}</span>
            </div>
            <p class="text-gray-700 text-base leading-relaxed mb-3">
              {{ comment.body }}
            </p>
            <div class="flex gap-2 text-xs"> 
              <button class="bg-blue-50 text-slate-500 border border-blue-100 rounded-sm py-1 px-2.5 cursor-pointer transition-colors duration-150 hover:bg-blue-100 hover:text-blue-500 hover:border-blue-200">
                👍 {{ comment.likes }}
              </button>
              <button class="bg-blue-50 text-slate-500 border border-blue-100 rounded-sm py-1 px-2.5 cursor-pointer transition-colors duration-150 hover:bg-blue-100 hover:text-blue-500 hover:border-blue-200"> 
                💬 답글
              </button>
            </div>
            <div v-if="comment.replies && comment.replies.length" class="mt-4 pt-4 border-t border-dashed border-gray-200">
              <div v-for="reply in comment.replies" :key="reply.id" class="ml-8 border-l-4 border-blue-200 bg-gray-50 rounded-lg p-4 mb-4 shadow-sm">
                <div class="flex items-center gap-2 mb-2"> 
                  <span class="font-bold text-gray-900 text-base">{{ reply.author }}</span>
                  <span class="text-slate-400 text-xs">{{ reply.time }}</span>
                </div>
                <p class="text-gray-700 text-base leading-relaxed mb-3">
                  {{ reply.body }}
                </p>
                <div class="flex gap-2 text-xs"> 
                  <button class="bg-blue-50 text-slate-500 border border-blue-100 rounded-sm py-1 px-2.5 cursor-pointer transition-colors duration-150 hover:bg-blue-100 hover:text-blue-500 hover:border-blue-200">
                    👍 {{ reply.likes }}
                  </button>
                  <button class="bg-blue-50 text-slate-500 border border-blue-100 rounded-sm py-1 px-2.5 cursor-pointer transition-colors duration-150 hover:bg-blue-100 hover:text-blue-500 hover:border-blue-200"> 
                    💬 답글
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center text-lg text-gray-600 mt-20">
        게시물 내용을 불러오는 중...
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>