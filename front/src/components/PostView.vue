<script setup>
import { ref, watch, onMounted } from 'vue'; // ref, watch, onMounted 가져오기
import { useRoute } from 'vue-router'; // useRoute 가져오기
import { defineEmits } from 'vue'; // defineEmits 가져오기

import axios from 'axios'; // axios 가져오기

const route = useRoute(); // route 객체 생성
const posts = ref([]); // 백엔드에서 받아와서 매핑된 게시물 데이터를 저장할 반응형 변수
const loading = ref(false); // 로딩 상태
const error = ref(null); // 에러 상태
const currentCategory = ref(''); // 현재 카테고리

// PostDetail 관련 상태 추가
const showPostDetail = ref(false); // PostDetail 표시 여부
const selectedPostId = ref(null); // 선택된 게시물 ID
const selectedPost = ref(null); // 클릭된 게시물 전체 객체를 저장할 ref

const emit = defineEmits(['open-detail']); // 'open-detail' 이벤트를 정의

const openDetail = (postItem) => {
  emit('open-detail', postItem); // 부모에게 postItem 전체를 전달
};

// 시간 포맷팅 함수 (템플릿의 post.time에 매핑)
const formatTime = (isoString) => {
  if (!isoString) return '';
  const date = new Date(isoString);
  const now = new Date();
  const diffMinutes = Math.floor((now - date) / (1000 * 60)); // 분 차이 계산
  // const diffHours = Math.floor(diffMinutes / 60);
  // const diffDays = Math.floor(diffHours / 24);
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

      return {
        id: item.postId,
        category: item.category,
        time: formatTime(item.createdAt),
        title: item.title,
        youtube: item.videoUrl,
        youtubeTumbnail: youtubeTumbnail, // 수정된 부분
        img: item.imageUrl,
        body: item.content,
        likeCount: item.likeCount || 0,
        comments: item.comments || 0,
        viewcount: item.viewCount || 0
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
}, { immediate: true }); // 즉시 실행
// --- 데이터 로딩 관련 스크립트 끝 ---
</script>

<template>
  <main class="bg-[#f7f9fb] min-h-screen flex flex-col items-start font-pretendard text-[0.75rem] flex-grow w-full m-0 pl-0 pt-[1.9rem]">
    <section>
      <div
        v-for="postItem in posts"
        :key="postItem.id"
        class="bg-white rounded-[0.66rem] shadow-[0_0.094rem_0.75rem_rgba(60,100,170,0.08)] border-[0.07rem] border-[#e3e6ea] max-w-[30rem] w-[30vw] px-[3vw] pt-[1.5rem] pb-[0.94rem] mx-0 my-0 mb-[1.7rem] flex flex-col cursor-pointer
               md:max-w-[74.25vw] md:px-[3vw] md:pt-[0.83rem] md:pb-[0.56rem]"
        @click="openDetail(postItem)"
      >
        <div class="flex items-center gap-[0.38em] text-[#7a8baa] text-[0.76em] mb-[0.75em] font-pretendard">
          <span class="text-[#4f8cff] font-semibold text-[1.15em]">{{ postItem.category }}</span>
          <span class="text-[#b6bec8] text-[1em]">·</span>
          <span class="text-[#a6b0bc] text-[1.15em]">{{ postItem.time }}</span>
        </div>
        <h1 class="text-[#232a33] text-[1rem] font-bold mb-[0.3em] mt-0 tracking-[-0.02em] leading-[1.4]
                   md:text-[0.77rem]">
          {{ postItem.title }}
        </h1>
        <div v-if="postItem.youtubeTumbnail" class="w-full aspect-[16/9] mb-[0.83em] rounded-[0.66rem] overflow-hidden bg-black flex items-center justify-center"> 
          <img :src="postItem.youtubeTumbnail" alt="유튜브 썸네일" />
        </div>
        <div v-else-if="postItem.img" class="w-full aspect-[16/9] mb-[0.83em] rounded-[0.66rem] overflow-hidden flex items-center justify-center">
          <img :src="postItem.img" :alt="postItem.title" class="w-full h-full object-cover" />
        </div>
        <div class="text-[0.81rem] text-[#354052] mb-[0.83em] leading-[1.8] break-keep
                     md:text-[0.73rem]">
          {{ postItem.body }}
        </div>
        <div class="flex gap-[0.53em] mt-[0.11em] justify-start mb-1">
          <button class="bg-[#f4f8fd] text-[#4f8cff] border-[0.07rem] border-[#e7ecf3] rounded-[0.28rem] py-[0.33em] px-[0.94em] text-[0.75rem] font-pretendard cursor-pointer transition-all duration-150 hover:bg-[#4f8cff] hover:text-white hover:border-[#4f8cff]">
            👍 {{ postItem.likeCount }}
          </button>
          <button class="bg-[#f4f8fd] text-[#4f8cff] border-[0.07rem] border-[#e7ecf3] rounded-[0.28rem] py-[0.33em] px-[0.94em] text-[0.75rem] font-pretendard cursor-pointer transition-all duration-150 hover:bg-[#4f8cff] hover:text-white hover:border-[#4f8cff]">
            💬 {{ postItem.comments }}
          </button>
          <button class="bg-[#f4f8fd] text-[#4f8cff] border-[0.07rem] border-[#e7ecf3] rounded-[0.28rem] py-[0.33em] px-[0.94em] text-[0.75rem] font-pretendard cursor-pointer transition-all duration-150 hover:bg-[#4f8cff] hover:text-white hover:border-[#4f8cff]">
            🔗 공유
          </button>
        </div>
      </div>
    </section>

    <div v-if="loading" class="text-center mt-[20px] text-[1rem] text-[#555]">게시물을 불러오는 중...</div>
    <div v-if="error" class="text-center mt-[20px] text-[1rem] text-[#d9534f]">{{ error }}</div>
    <div v-if="!loading && !error && posts.length === 0" class="text-center mt-[20px] text-[1rem] text-[#555]">
      선택된 카테고리에 게시물이 없습니다.
    </div>
  </main>
</template>

<style scoped>
/* No scoped CSS needed as everything is converted to Tailwind */
</style>