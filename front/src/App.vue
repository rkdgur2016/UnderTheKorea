<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

import TheHeader from '@/layout/TheHeader.vue'; 
import LoginLayer from '@/layout/TheSidebar.vue'; // TheSidebar.vue로 임포트된 LoginLayer 
import PostDetail from '@/components/PostDetail.vue'; 

const selectedPostId = ref(null); 
const selectedPost = ref(null);  // Add this line to store the entire post object

const openPostDetail = (postItem) => {
  selectedPostId.value = postItem.id; 
  selectedPost.value = postItem; 
  console.log("게시글 아이디 :", postItem.id); 
};
const closePostDetail = () => {
  selectedPostId.value = null; 
  selectedPost.value = null; 
  console.log("App.vue: PostDetail closed"); 
};

const route = useRoute(); 

watch(() => route.path, () => {
  if (selectedPostId.value !== null) {
    selectedPostId.value = null; 
    selectedPost.value = null; 
    console.log("App.vue: Router path changed, PostDetail closed."); 
  }
});
</script> 

<template>
  <div class="flex flex-col min-h-screen font-pretendard">
    <TheHeader></TheHeader>
    <div
      class="flex flex-1 w-full gap-6 pr-6 bg-[#f7f9fb] relative
             transition-all duration-300 ease-in-out
             lg:flex-row lg:pr-6
             "
      :class="{ 'pr-0': selectedPostId !== null }"
    >
      <LoginLayer class="
        hidden md:block
        lg:w-64 xl:w-72
        sticky top-14 h-[calc(100vh-56px)] overflow-y-auto
        flex-shrink-0 bg-white p-6 shadow-md rounded-lg

        /* 모바일/태블릿 반응형: sticky 해제 및 너비/높이 조정 */
        md:static md:w-full md:h-auto md:mb-4
        lg:sticky lg:top-14 lg:h-[calc(100vh-56px)]
      "></LoginLayer>

      <div class="flex-grow min-w-0 bg-[#f7f9fb] pb-8 transition-all duration-300 ease-in-out">
        <router-view @open-detail="openPostDetail"></router-view>
      </div>

      <PostDetail
        v-if="selectedPostId !== null"
        :post-id="selectedPostId"
        :post="selectedPost"
        @close="closePostDetail"
        class="
          w-full max-w-[700px] bg-transparent overflow-y-auto p-8 box-border
          fixed top-0 right-0 h-full 
          transform -translate-x-full transition-transform duration-300 ease-in-out
          z-40 lg:z-30  

          /* 모바일 반응형: 헤더 아래부터 시작하도록 top-14 설정 및 오른쪽 정렬 유지 */
          lg:fixed lg:top-14 lg:left-0 lg:h-[calc(100vh-56px)] lg:border-none =
          lg:shadow-lg lg:bg-white lg:p-6 lg:z-50 /* 모바일에서 최상단으로 */
        "
        :class="{ '!translate-x-0': selectedPostId !== null }"
      />
    </div>
  </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap'); 
/* HTML, body에 Pretenard 폰트 적용 (선택 사항) */ 
html, body {
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif; 
}
</style>