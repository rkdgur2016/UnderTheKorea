<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 더미 데이터 (실제로는 백엔드에서 가져와야 함)
const discussionsByCategory = ref({
  '철학': [
    { id: 101, title: '행복은 외부 조건인가, 내면의 상태인가?', totalVotes: 120 },
    { id: 102, title: '인간은 자유 의지를 가지고 있는가, 결정론적인가?', totalVotes: 85 },
    { id: 103, title: '거짓말은 항상 나쁜가?', totalVotes: 150 },
    { id: 104, title: '만약 시간을 되돌릴 수 있다면, 과거를 바꿀 것인가?', totalVotes: 90 },
    { id: 105, title: '인간의 삶의 의미는 무엇인가?', totalVotes: 110 },
    { id: 106, title: '선과 악의 기준은 절대적인가, 상대적인가?', totalVotes: 130 },
  ],
  '정치': [
    { id: 201, title: '최저 임금 인상은 경제에 도움이 되는가, 해가 되는가?', totalVotes: 250 },
    { id: 202, title: '사형제도는 유지되어야 하는가, 폐지되어야 하는가?', totalVotes: 300 },
    { id: 203, title: '국회의원 불체포 특권은 유지되어야 하는가?', totalVotes: 180 },
    { id: 204, title: '대통령 중임제, 단임제 중 어느 것이 더 바람직한가?', totalVotes: 210 },
    { id: 205, title: '탄소 중립 정책, 경제 성장을 저해하는가?', totalVotes: 190 },
    { id: 206, title: '정당 투표에서 거대 양당 외 소수 정당에 투표해야 하는가?', totalVotes: 270 },
  ],
  '사회문제': [
    { id: 301, title: '학생 인권 조례는 교육 현장에 긍정적인가, 부정적인가?', totalVotes: 280 },
    { id: 302, title: '개인의 행복을 위한 비혼주의는 사회에 어떤 영향을 미치는가?', totalVotes: 200 },
    { id: 303, title: 'MZ세대는 개인주의적인가, 공동체 의식이 약한가?', totalVotes: 230 },
    { id: 304, title: '젠더 갈등은 실재하는가, 과장된 것인가?', totalVotes: 350 },
    { id: 305, title: 'AI 기술 발전은 인간의 일자리를 위협하는가?', totalVotes: 260 },
    { id: 306, title: '온라인 익명성은 보장되어야 하는가, 실명제로 전환해야 하는가?', totalVotes: 320 },
  ],
});

// 모든 카테고리 이름을 배열로 가져옵니다.
const categories = ref(['철학', '정치', '사회문제']);

// 토론 주제 클릭 시 상세 페이지로 이동
const goToDiscussionDetail = (id) => {
  router.push(`/balance-game/${id}`);
};
</script>

<template>
  <div class="balance-game-home-simple">
    <h1 class="page-title">오늘의 밸런스 토론</h1>

    <section
      v-for="category in categories"
      :key="category"
      class="discussion-category-section-simple"
    >
      <h2 class="section-title-simple">{{ category }} 토론 주제</h2>
      <div class="table-container">
        <table class="discussion-table">
          <thead>
            <tr>
              <th>번호</th>
              <th>토론 주제</th>
              <th>참여 인원</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(discussion, index) in discussionsByCategory[category]"
              :key="discussion.id"
              @click="goToDiscussionDetail(discussion.id)"
            >
              <td>{{ index + 1 }}</td>
              <td class="discussion-title-cell">{{ discussion.title }}</td>
              <td>{{ discussion.totalVotes }}명</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>

<style scoped>
.balance-game-home-simple {
  padding: 2rem;
  background-color: var(--color-base-light); /* 베이스 컬러 */
  min-height: calc(100vh - 56px); /* 헤더 높이 제외 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 중앙 정렬 */
}

.page-title {
  font-size: 2.5rem;
  color: var(--color-main-heading);
  text-align: center;
  margin-bottom: 3rem;
  font-weight: 800;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.05);
  width: 100%; /* 너비 100%로 설정하여 중앙 정렬에 영향 주지 않음 */
  max-width: 900px; /* 최대 너비 제한 */
}

.discussion-category-section-simple {
  margin-bottom: 2.5rem; /* 각 카테고리 섹션 간의 여백 */
  background-color: var(--color-main-bg); /* 메인 컬러 */
  padding: 2rem;
  border-radius: 0.8rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); /* 그림자 약하게 */
  width: 100%;
  max-width: 900px; /* 최대 너비 제한 */
  box-sizing: border-box; /* 패딩 포함 너비 계산 */
}

.section-title-simple {
  font-size: 1.8rem;
  color: var(--color-point-primary); /* 포인트 컬러로 강조 */
  margin-bottom: 1.5rem; /* 여백 */
  text-align: left; /* 왼쪽 정렬 */
  font-weight: 700;
  padding-bottom: 0.8rem;
  border-bottom: 2px solid var(--color-base-dark); /* 카테고리 제목 아래 구분선 */
  text-align: center; /* 카테고리 제목 중앙 정렬 */
  position: relative;
}

.section-title-simple::after { /* 밑줄 효과 추가 */
  content: '';
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: 0;
  width: 60px; /* 밑줄 길이 */
  height: 3px;
  background-color: var(--color-point-secondary); /* 포인트 컬러 (초록색) */
  border-radius: 2px;
}


.table-container {
  overflow-x: auto; /* 테이블이 너무 넓을 경우 스크롤 허용 */
}

.discussion-table {
  width: 100%;
  border-collapse: collapse; /* 셀 경계선 제거 */
  font-size: 1rem;
  color: var(--color-main-text); /* 메인 텍스트 컬러 */
}

.discussion-table th,
.discussion-table td {
  padding: 1rem 1.2rem;
  text-align: left;
  border-bottom: 1px solid var(--color-base-medium); /* 테이블 구분선 */
}

.discussion-table th {
  background-color: var(--color-base-medium); /* 헤더 배경색 */
  color: var(--color-main-heading); /* 헤더 텍스트 색상 */
  font-weight: 600;
  position: sticky; /* 스크롤 시 헤더 고정 */
  top: 0;
  z-index: 1;
}

.discussion-table tbody tr {
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.discussion-table tbody tr:hover {
  background-color: var(--color-base-dark); /* 호버 시 배경색 변경 */
}

.discussion-table tbody tr:last-child td {
  border-bottom: none; /* 마지막 줄은 아래 경계선 없음 */
}

.discussion-title-cell {
  font-weight: 500;
  color: var(--color-main-text);
  line-height: 1.4;
  word-break: keep-all; /* 단어 단위로 줄바꿈 */
}

/* 모바일 반응형 */
@media (max-width: 768px) {
  .balance-game-home-simple {
    padding: 1rem;
  }
  .page-title {
    font-size: 2rem;
    margin-bottom: 2rem;
  }
  .discussion-category-section-simple {
    padding: 1.2rem;
    margin-bottom: 1.5rem;
    border-radius: 0.6rem;
  }
  .section-title-simple {
    font-size: 1.5rem;
    margin-bottom: 1rem;
    padding-bottom: 0.6rem;
  }
  .section-title-simple::after {
    width: 40px;
    height: 2px;
  }
  .discussion-table {
    font-size: 0.9rem;
  }
  .discussion-table th,
  .discussion-table td {
    padding: 0.8rem 1rem;
  }
}
</style>