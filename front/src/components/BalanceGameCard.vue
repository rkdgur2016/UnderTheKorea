<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  discussion: {
    type: Object,
    required: true,
    // totalVotes는 투표 수를 나타내기 위한 예시
    // img는 카드에 표시할 이미지 URL
    // { id: Number, title: String, totalVotes: Number, img: String }
  }
});
</script>

<template>
  <div class="balance-game-card">
    <div class="card-image">
      <img :src="discussion.img" :alt="discussion.title" />
    </div>
    <div class="card-content">
      <h3 class="card-title">{{ discussion.title }}</h3>
      <div class="card-footer">
        <span class="total-votes">📊 총 {{ discussion.totalVotes }}명 참여</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.balance-game-card {
  background-color: var(--color-main-bg); /* 메인 컬러 */
  border-radius: 0.8rem;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
}

.balance-game-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.card-image {
  width: 100%;
  height: 180px; /* 이미지 고정 높이 */
  overflow: hidden;
  border-bottom: 1px solid var(--color-base-dark); /* 베이스 컬러 */
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 카드에 꽉 차도록 */
  transition: transform 0.3s ease;
}

.balance-game-card:hover .card-image img {
  transform: scale(1.05);
}

.card-content {
  padding: 1.2rem;
  flex-grow: 1; /* 남은 공간 차지 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-title {
  font-size: 1.2rem;
  color: var(--color-main-heading); /* 메인 컬러 */
  margin-bottom: 0.8rem;
  font-weight: 600;
  line-height: 1.4;
  word-break: keep-all;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 2줄로 제한 */
  -webkit-box-orient: vertical;
}

.card-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
}

.total-votes {
  font-size: 0.9rem;
  color: var(--color-main-subtle-text); /* 메인 컬러 */
  font-weight: 500;
}
</style>