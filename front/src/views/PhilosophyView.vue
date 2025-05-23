<template>
  <PostVeiw :posts="philosophyPosts" @open-detail="$emit('open-detail', $event)"></PostVeiw>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import PostVeiw from '@/components/PostView.vue'; // PostList 컴포넌트 임포트

const emit = defineEmits(['open-detail']);

const philosophyPosts = [ // 철학 페이지 데이터
  {
    id: 101,
    category: '철학',
    time: '2시간 전',
    title: '소크라테스의 변명: 무지의 지혜란 무엇인가?',
    body: '소크라테스는 "나는 내가 아무것도 모른다는 것을 안다"고 말했습니다. 진정한 지혜란 무엇일까요? 현대 사회에서 이 겸손한 태도가 왜 중요한지 생각해봅니다.',
    likes: '1.1K',
    comments: '320',
    youtube: '1J6B_w2J3wA' // 예시 YouTube ID
  },
  {
    id: 102,
    category: '철학',
    time: '5시간 전',
    title: '플라톤의 이데아론, 현실과 이상 사이',
    body: '플라톤의 이데아론은 우리가 보는 세계가 불완전한 그림자에 불과하다고 말합니다. 이상적인 세계를 추구하는 철학적 사유를 탐구합니다.',
    likes: '2.3K',
    comments: '410',
    youtube: 'e6z9v4D0510' // 예시 YouTube ID
  },
  // ...더 많은 철학 관련 포스트
];
</script>

<style scoped>
/* 이 파일에는 더 이상 스타일이 필요 없습니다. */
</style>