<template>
    <PostVeiw :posts="philosophyPosts" @open-detail="$emit('open-detail', $event)"></PostVeiw>
  </template>
  
  <script setup>
  import { ref, defineEmits } from 'vue';
  import PostVeiw from '@/components/PostView.vue'; // PostList 컴포넌트 임포트
  
  const emit = defineEmits(['open-detail']);
  
  const economyPosts = [ 
    {
      id: 101,
      category: '경제',
      time: '2시간 전',
      title: '삼성전자 반도체 공장, 새로운 기술 혁신의 중심지로 부상',
      body: '삼성전자가 반도체 공장에서 새로운 기술 혁신을 이루어내며 글로벌 시장에서의 경쟁력을 강화하고 있습니다. 이로 인해 한국 경제에 긍정적인 영향을 미칠 것으로 기대됩니다.',
      likes: '1.1K',
      comments: '320',
      youtube: '1J6B_w2J3wA' // 예시 YouTube ID
    },
    {
      id: 102,
      category: '경제',
      time: '5시간 전',
      title: '체코 원전 수출, 유럽 에너지 시장의 새로운 강자 등장',
      body: '체코가 원자력 발전소를 수출하며 유럽 에너지 시장에서 새로운 강자로 떠오르고 있습니다. 이로 인해 유럽의 에너지 독립성이 강화될 것으로 기대됩니다.',
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