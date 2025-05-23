<template>
  <PostVeiw :posts="politicsPosts" @open-detail="$emit('open-detail', $event)"></PostVeiw>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import PostVeiw from '@/components/PostView.vue'; 

const emit = defineEmits(['open-detail']); // App.vue로 이벤트를 전달하기 위함

const politicsPosts = [ // 정치 페이지 데이터
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
  // ...더 많은 정치 관련 포스트
];

// 이 뷰에는 더 이상 openDetail 함수가 필요 없습니다. PostList에서 이벤트를 발생시키고 App.vue가 처리합니다.
</script>

<style scoped>
/* 이 파일에는 더 이상 .kr-social-issue, .post-card 등의 스타일이 필요 없습니다. */
/* PostList.vue에 모두 정의되어 있기 때문입니다. */
/* 만약 이 뷰에만 적용되는 특정 스타일이 있다면 여기에 추가할 수 있습니다. */
</style>