<template>
  <PostVeiw :posts="philosophyPosts" @open-detail="$emit('open-detail', $event)"></PostVeiw>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import PostVeiw from '@/components/PostView.vue'; // PostList 컴포넌트 임포트

const emit = defineEmits(['open-detail']);

</script>

<style scoped>
/* 이 파일에는 더 이상 스타일이 필요 없습니다. */
</style>