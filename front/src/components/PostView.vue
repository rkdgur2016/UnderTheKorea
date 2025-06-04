<template>
  <div class="bg-slate-50 min-h-screen">
    <!-- 헤더 영역 -->
    <div class="bg-white border-b border-slate-200 sticky top-0 z-10">
      <div class="max-w-7xl mx-auto px-4 lg:px-8">
        <div class="py-4">
          <h1 class="text-2xl font-bold text-slate-800">{{ currentCategory }}</h1>
          <p class="text-sm text-slate-500 mt-1">{{ categoryDescription }}</p>
        </div>
      </div>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 로딩 상태 -->
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-slate-800"></div>
      </div>

      <!-- 에러 메시지 -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-md">
        {{ error }}
      </div>

      <!-- 게시물이 없을 때 -->
      <div v-else-if="posts.length === 0" class="text-center py-20">
        <svg class="mx-auto h-12 w-12 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
        </svg>
        <h3 class="mt-2 text-sm font-medium text-slate-900">게시물이 없습니다</h3>
        <p class="mt-1 text-sm text-slate-500">이 카테고리에 첫 번째 게시물을 작성해보세요.</p>
      </div>

      <!-- 게시물 목록 -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div 
          v-for="post in posts" 
          :key="post.id" 
          class="bg-white rounded-lg shadow-sm border border-slate-200 overflow-hidden hover:shadow-md transition-shadow duration-200 cursor-pointer"
          @click="openDetail(post)"
        >
          <!-- 게시물 썸네일 -->
          <div class="relative aspect-[16/9] bg-slate-100 overflow-hidden">
            <img 
              v-if="post.img" 
              :src="post.img" 
              :alt="post.title"
              class="w-full h-full object-cover"
            >
            <img 
              v-else-if="post.youtubeTumbnail" 
              :src="post.youtubeTumbnail" 
              :alt="post.title"
              class="w-full h-full object-cover"
            >
            <div v-else class="w-full h-full flex items-center justify-center bg-slate-200">
              <svg class="h-12 w-12 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.587a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
            </div>
            
            <!-- 유튜브 아이콘 -->
            <div v-if="post.youtube" class="absolute top-2 right-2 bg-red-600 text-white p-1 rounded-md">
              <svg class="h-4 w-4" fill="currentColor" viewBox="0 0 24 24">
                <path d="M19.615 3.184c-3.604-.246-11.631-.245-15.23 0-3.897.266-4.356 2.62-4.385 8.816.029 6.185.484 8.549 4.385 8.816 3.6.245 11.626.246 15.23 0 3.897-.266 4.356-2.62 4.385-8.816-.029-6.185-.484-8.549-4.385-8.816zm-10.615 12.816v-8l8 3.993-8 4.007z"/>
              </svg>
            </div>
            
            <!-- 카테고리 태그 -->
            <div class="absolute bottom-2 left-2 bg-slate-800/70 text-white text-xs px-2 py-1 rounded-md backdrop-blur-sm">
              {{ post.category }}
            </div>
          </div>
          
          <!-- 게시물 내용 -->
          <div class="p-4">
            <h3 class="font-medium text-lg text-slate-800 line-clamp-2">{{ post.title }}</h3>
            <p class="mt-2 text-sm text-slate-600 line-clamp-2">{{ post.body }}</p>
            
            <!-- 투표 결과 (있는 경우) -->
            <div v-if="post.votes && post.votes.length > 0" class="mt-3 bg-slate-50 p-3 rounded-md">
              <p class="text-xs font-medium text-slate-700 mb-2">투표 결과</p>
              <div v-for="(option, index) in post.votes" :key="index" class="mb-2">
                <div class="flex justify-between text-xs mb-1">
                  <span class="text-slate-700">{{ option.text }}</span>
                  <span class="font-medium text-slate-800">{{ option.percentage }}%</span>
                </div>
                <div class="h-2 bg-slate-200 rounded-full overflow-hidden">
                  <div 
                    class="h-full rounded-full" 
                    :style="{ width: `${option.percentage}%`, backgroundColor: getVoteColor(index) }"
                  ></div>
                </div>
              </div>
            </div>
            
            <!-- 게시물 메타 정보 -->
            <div class="mt-4 flex items-center justify-between text-xs text-slate-500">
              <span>{{ post.time }}</span>
              <div class="flex items-center space-x-3">
                <div class="flex items-center">
                  <svg class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                  <span>{{ post.viewcount }}</span>
                </div>
                <div class="flex items-center">
                  <svg class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
                  </svg>
                  <span>{{ post.likeCount }}</span>
                </div>
                <div class="flex items-center">
                  <svg class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 002-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                  <span>{{ post.comments }}</span>
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
import { ref, watch, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { defineEmits } from 'vue';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const posts = ref([]);
const loading = ref(false);
const error = ref(null);
const currentCategory = ref('');

// PostDetail 관련 상태 추가
const showPostDetail = ref(false);
const selectedPostId = ref(null);
const selectedPost = ref(null);

const emit = defineEmits(['open-detail']);

const openDetail = (postItem) => {
  emit('open-detail', postItem);
};

// 시간 포맷팅 함수 (템플릿의 post.time에 매핑)
const formatTime = (isoString) => {
  if (!isoString) return '';
  const date = new Date(isoString);
  const now = new Date();
  const diffMinutes = Math.floor((now - date) / (1000 * 60)); // 분 차이 계산
  if (diffMinutes < 1) return '방금 전';
  if (diffMinutes < 60) return `${diffMinutes}분 전`;
  const diffHours = Math.floor(diffMinutes / 60);
  if (diffHours < 24) return `${diffHours}시간 전`;
  const diffDays = Math.floor(diffHours / 24);
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
      const videoId = item.videoUrl ? item.videoUrl.split('?')[0] : '';
      const youtubeTumbnail = videoId ? "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg" : '';

      // 예시 투표 데이터 (실제로는 백엔드에서 받아와야 함)
      const sampleVotes = item.votes ? item.votes : (Math.random() > 0.7 ? [
        { text: '찬성', count: Math.floor(Math.random() * 100), percentage: Math.floor(Math.random() * 100) },
        { text: '반대', count: Math.floor(Math.random() * 100), percentage: 100 - Math.floor(Math.random() * 100) }
      ] : null);

      return {
        id: item.postId,
        category: item.category,
        time: formatTime(item.createdAt),
        title: item.title,
        youtube: item.videoUrl,
        youtubeTumbnail: youtubeTumbnail,
        img: item.imageUrl,
        body: item.content,
        likeCount: item.likeCount || 0,
        comments: item.comments || 0,
        viewcount: item.viewCount || 0,
        votes: sampleVotes // 투표 데이터 추가
      };
    });
    console.log('게시물 데이터:', posts.value);

  } catch (err) {
    console.error('게시물 로드 중 오류 발생:', err);
    error.value = '게시물을 불러오는데 실패했습니다. 서버 상태를 확인해주세요.';
  } finally {
    loading.value = false;
  }
};

// 카테고리 목록
const categories = [
  { name: '철학', query: '철학' },
  { name: '정치', query: '정치' },
  { name: '경제', query: '경제' },
  { name: '사회', query: '사회문제' },
  { name: '광장', query: '광장' },
  { name: '좌우명', query: '좌우명' }
];

// 카테고리 설명
const categoryDescription = computed(() => {
  switch (currentCategory.value) {
    case '철학': return '인간과 세계에 대한 깊이 있는 사유와 토론';
    case '정치': return '대한민국의 정치 현안과 미래 방향에 대한 논의';
    case '경제': return '경제 정책과 시장 동향에 대한 분석과 전망';
    case '사회문제': return '우리 사회가 직면한 다양한 문제들에 대한 고찰';
    case '광장': return '자유로운 의견 교환과 토론의 장';
    case '좌우명': return '삶의 방향을 제시하는 가치관과 원칙';
    default: return '다양한 주제에 대한 의견을 나누는 공간';
  }
});

// 카테고리 이동 함수
const navigateToCategory = (category) => {
  router.push({ path: route.path, query: { category } });
};

// 투표 결과 바 색상
const getVoteColor = (index) => {
  const colors = ['#4f46e5', '#0ea5e9', '#10b981', '#f59e0b', '#ef4444'];
  return colors[index % colors.length];
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
</script>

<style scoped>
/* 스크롤바 숨기기 */
.scrollbar-hide {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
.scrollbar-hide::-webkit-scrollbar {
  display: none;  /* Chrome, Safari, Opera */
}

/* 부드러운 애니메이션 */
.transition-all {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

/* 라인 클램프 (텍스트 줄 제한) */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>