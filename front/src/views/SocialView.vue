<template>
  <PostVeiw :posts="socialPosts" @open-detail="$emit('open-detail', $event)"></PostVeiw>
</template>

<script setup>
import { ref, defineEmits } from 'vue';
import PostVeiw from '@/components/PostView.vue'; // PostList 컴포넌트 임포트

const emit = defineEmits(['open-detail']);

const socialPosts = [ 
  // 1. 영상을 포함한 게시글 2개
  {
    id: 201,
    category: '사회문제',
    time: '2시간 전',
    title: '청년 실업률 증가, 사회적 해법 모색',
    body: '경기 침체와 고용 시장의 불안정으로 청년 실업률이 계속해서 증가하고 있습니다. 전문가들은 청년 실업 문제 해결을 위한 다각적인 사회적 해법 마련이 시급하다고 말합니다. 정부와 기업, 그리고 시민 사회의 협력이 더욱 중요해지고 있습니다.',
    likes: '1.2K',
    comments: '280',
    youtube: 'H3_Y0zYy0_A' // 실제 유튜브 영상 ID로 교체 필요
  },
  {
    id: 202,
    category: '사회문제',
    time: '4시간 전',
    title: '고령화 사회, 노인 빈곤 문제 심각성',
    body: '빠르게 진행되는 고령화 속에서 노인 빈곤 문제는 우리 사회의 심각한 과제로 떠오르고 있습니다. 안정적인 노후 보장을 위한 사회 안전망 강화와 일자리 창출 방안이 논의되고 있습니다. 우리 모두의 관심이 필요한 때입니다.',
    likes: '980',
    comments: '190',
    youtube: 'kQx9V7f7y-Q' // 실제 유튜브 영상 ID로 교체 필요
  },

  // 2. 이미지를 포함한 게시글 2개
  {
    id: 203,
    category: '사회문제',
    time: '6시간 전',
    title: '기후 위기 대응, 시민의 역할은?',
    body: '급변하는 기후 위기 속에서 시민들의 적극적인 참여와 실천이 중요해지고 있습니다. 일상 속 작은 변화부터 시작하여 지속 가능한 미래를 위한 노력을 함께 해야 합니다. 환경 보호를 위한 우리의 행동이 세상을 바꿀 수 있습니다.',
    likes: '1.5K',
    comments: '350',
    img: 'https://static.news.zumst.com/images/67/2019/04/14/62d8530272564c35bce961c67f3c0fa7.jpg' // 실제 이미지 URL로 교체 필요
  },
  {
    id: 204,
    category: '사회문제',
    time: '8시간 전',
    title: '디지털 격차 해소, 모두를 위한 기술',
    body: '정보화 시대가 가속화되면서 디지털 기기 및 정보 접근성에서 발생하는 격차가 새로운 사회문제로 부각되고 있습니다. 고령층, 저소득층 등 취약계층의 디지털 접근성을 높이고 교육을 제공하여 디지털 격차를 해소하는 노력이 필요합니다.',
    likes: '720',
    comments: '120',
    img: 'https://www.koit.co.kr/news/photo/202012/80313_31081_418.jpg' // 실제 이미지 URL로 교체 필요
  },

  // 3. 텍스트만 존재하는 게시글 2개
  {
    id: 205,
    category: '사회문제',
    time: '10시간 전',
    title: '주거 불평등, 심화되는 주거난 속 대안은?',
    body: '치솟는 집값과 전세난으로 인해 많은 사람들이 주거 문제로 고통받고 있습니다. 특히 청년층과 신혼부부의 주거 부담이 심각한 수준으로, 공공 주택 확대, 주거 지원 강화 등 현실적인 대안 마련이 시급합니다.',
    likes: '1.1K',
    comments: '250',
    // youtube: 없음, img: 없음
  },
  {
    id: 206,
    category: '사회문제',
    time: '12시간 전',
    title: '학교 폭력 예방, 학교와 가정이 함께',
    body: '최근 학교 폭력의 심각성이 다시 한번 사회적 문제로 대두되고 있습니다. 학교 폭력은 피해 학생에게 깊은 상처를 남기며, 장기적인 영향으로 이어질 수 있습니다. 학교와 가정이 긴밀히 협력하여 예방 교육을 강화하고, 피해 학생 보호 및 가해 학생 선도 방안을 마련해야 합니다.',
    likes: '850',
    comments: '180',
    // youtube: 없음, img: 없음
  }
];
</script>

<style scoped>
/* 이 파일에는 더 이상 스타일이 필요 없습니다. */
</style>