<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";

import TheHeader from "@/layout/TheHeader.vue";
import TheFooter from "@/layout/TheFooter.vue";
import PostDetail from "@/components/PostDetail.vue";
import LoginModal from "@/components/LoginModal.vue";
import SignupModal from "@/components/SignupModal.vue";
import { useLoginStore } from "@/stores/LoginStore";
import { useModalStore } from "@/stores/modalStore";
import { onMounted } from "vue";

const selectedPostId = ref(null);
const selectedPost = ref(null); // Add this line to store the entire post object

const loginStore = useLoginStore();
const modalStore = useModalStore();

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

watch(
  () => route.path,
  () => {
    if (selectedPostId.value !== null) {
      selectedPostId.value = null;
      selectedPost.value = null;
      console.log("App.vue: Router path changed, PostDetail closed.");
    }
  }
);

onMounted(() => {
  loginStore.initializeAuth();
});
</script>

<template>
  <div class="flex flex-col min-h-screen font-pretendard">
    <TheHeader></TheHeader>
    <LoginModal
      :show-login-modal="modalStore.showLoginModal"
      @close="modalStore.closeAll"
      @open-signup="modalStore.openSignup"
    />
    <SignupModal
      :show-signup-modal="modalStore.showSignupModal"
      @close="modalStore.closeAll"
      @open-login="modalStore.openLogin"
      @signup-success-and-open-login="modalStore.signupSuccessAndOpenLogin"
    />
    <div
      class="flex flex-1 w-full gap-6 pr-6 bg-[#f7f9fb] relative transition-all duration-300 ease-in-out lg:flex-row lg:pr-0"
      :class="{ 'pr-0': selectedPostId !== null }"
    >
      <div
        class="flex-grow min-w-0 bg-[#f7f9fb] pb-8 transition-all duration-300 ease-in-out"
      >
        <router-view @open-detail="openPostDetail"></router-view>
      </div>

      <PostDetail
        v-if="selectedPostId !== null"
        :post-id="selectedPostId"
        :post="selectedPost"
        @close="closePostDetail"
        :class="{ '!translate-x-0': selectedPostId !== null }"
      />
    </div>
    <TheFooter></TheFooter>
  </div>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap");
/* HTML, body에 Pretenard 폰트 적용 (선택 사항) */
html,
body {
  font-family: "Pretendard", "Noto Sans KR", sans-serif;
}
</style>
