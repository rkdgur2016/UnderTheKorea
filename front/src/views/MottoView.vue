<template>
  <div class="korean-magazine">
    <div class="spread">
      <div class="page left">
        <div class="logo" v-if="currentSpread.logo">{{ currentSpread.logo }}</div>
        <div class="pagenumber">{{ pageNumber }} / {{ spreads.length * 2 }}</div>
        <div class="content">
          <h2 v-if="currentSpread.title" class="title">{{ currentSpread.title }}</h2>
          <div class="text" v-html="currentSpread.leftText"></div>
        </div>
      </div>
      <div class="page right">
        <div class="content">
          <div class="text" v-html="currentSpread.rightText"></div>
          <div class="video" v-if="currentSpread.video" v-html="currentSpread.video"></div>
        </div>
      </div>
    </div>
    <div class="controls">
      <button @click="prevSpread" :disabled="spreadIndex === 0">이전</button>
      <div class="bars">
        <span
          v-for="(s, i) in spreads"
          :key="i"
          :class="['bar', { active: i === spreadIndex }]"
          @click="goToSpread(i)"
        ></span>
      </div>
      <button @click="nextSpread" :disabled="spreadIndex === spreads.length - 1">다음</button>
    </div>
  </div>
</template>


<script setup>
import { ref, computed } from 'vue'

const spreads = [
  {
    logo: 'POLITICS',
    title: '정치를 외면한 대가',
    leftText: `
      <p>여러분, 정치가 낯설고 불편하다고 외면하고 계십니까?</p>
      <p>바쁘고 복잡한 세상 속에서 정치는 내 삶과 무관하다고 느껴지십니까?</p>
      <p><strong>그러나 분명히 말씀드립니다.</strong></p>
      <p>정치를 외면한 대가는, 결국 가장 저열한 인간들에게 지배당하는 것입니다. 이 말은 철학자 플라톤이 남긴 경고이며, 오늘날 일본의 현실이 그것을 여실히 보여주고 있습니다.</p>
    `,
    rightText: `
      <p>왜 일본은 한 정당이 수십 년간 정권을 유지하고 있을까요? 왜 젊은 세대의 목소리는 들리지 않고, 사회는 점점 고령층의 이익에 맞춰 흘러가는 걸까요?</p>
      <p>그 원인은 명확합니다. 정치에 무관심한 청년 세대가 그 자리를 비운 탓입니다. 정치 참여를 포기하면, 기존 권력은 변하지 않습니다. 그들은 조용히 권력을 움켜쥐고, 표현의 자유를 제한하며, 언론을 통제하고, 국민의 알 권리를 침묵시킵니다. 이것이 정치적 무관심이 낳은 결과입니다.</p>
    `,
    video: `<div class="ratio"><iframe src="https://www.youtube.com/embed/mZDmFo27vM0?si=dIpT8RZd9CRIca3J" title="YouTube video player" allowfullscreen></iframe></div>`
  },
  {
    logo: 'CHAOS',
    title: '정보의 홍수, 그리고 혼란',
    leftText: `
      <p>📡 정보는 넘쳐납니다. 그런데 왜 우리는 더 혼란스러울까요?</p>
      <p>오늘날 우리는 정보의 홍수 속에 살고 있습니다. 신문, 유튜브, 블로그, 소셜미디어까지—정치 정보를 접할 창구는 차고 넘칩니다.</p>
      <p>그럼에도 불구하고, 우리는 갈수록 무기력하고 방향을 잃은 상태로 빠져들고 있습니다. 왜일까요?</p>
    `,
    rightText: `
      <p>그것은 정보가 많아서가 아니라, <strong>판단</strong>이 없기 때문입니다. 정보는 넘치지만, 그 속에서 "무엇이 진짜인가?"를 결정해 줄 사유와 결단이 없습니다. 결정하지 못하는 사람은 결국, 어떤 진실에도 도달하지 못합니다.</p>
      <p>이 시대는 진리를 해체하고, "모두가 옳다"는 말로 결단을 회피하는 시대입니다. 하지만 진리가 없는 세상에서는 아무것도 진실이 아니게 됩니다. 이것이 포스트모던의 위기이며, 우리가 직면한 현실입니다.</p>
    `,
    video: `<div class="ratio"><iframe src="https://www.youtube.com/embed/WR9tn5oEUvs?si=1qNurQR1c8MiYbp4" title="YouTube video player" allowfullscreen></iframe></div>`
  },
  {
    logo: 'PHILOSOPHY',
    title: '결단 없는 시대, 철학이 필요한 이유',
    leftText: `
      <p>🧭 결단 없는 시대, 철학이 필요한 이유</p>
      <p>이 혼란의 시대에 필요한 것은 정보가 아닙니다. 필요한 것은, 철학이며, 나만의 사유와 판단 기준입니다.</p>
      <p>"나는 이 문제를 이렇게 본다." "나는 이 가치에 따라 세상을 바라보겠다." 이러한 자기 선언, 즉 결단이 있을 때, 비로소 정보는 의미를 갖고 체계를 갖춥니다.</p>
    `,
    rightText: `
      <p>철학이란 거창한 학문이 아닙니다. 철학은, 세상을 바라보는 당신만의 기준이자 필터입니다. 그 기준이 없다면, 우리는 결국 가장 큰 목소리나 가장 자극적인 정보에 휘둘리게 됩니다.</p>
    `,
    video: `<div class="ratio"><iframe src="https://www.youtube.com/embed/lMG6h2rVmuM?si=05RSrWXyTDFjkKYS" title="YouTube video player" allowfullscreen></iframe></div>`
  },
  {
    logo: 'DECISION',
    title: '지금, 당신의 결단이 필요합니다',
    leftText: `
      <p>🙋‍♂️ 지금, 당신은 어떤 결단을 내리고 있습니까?</p>
      <p>지금 이 순간에도 누군가는 당신 대신 권력을 휘두르고 있습니다. 표현을 통제하고, 언론을 조종하며, 진실을 선택적으로 가리고 있을 수도 있습니다.</p>
    `,
    rightText: `
      <p>그럼에도 당신이 정치에 무관심하다면, 그 무관심이 이 사회의 고통을 방조하는 셈입니다.</p>
      <p>그러니 이제는 멈춰 서십시오. “모르겠다, 너무 많다”는 말 뒤에 숨지 마십시오. 자신만의 철학으로 세상을 보고, 결단하십시오.</p>
      <p><strong>그것이 자유로운 시민, 책임 있는 세대가 되는 첫걸음입니다.</strong><br>그리고 그것이야말로, 이 시대가 당신에게 요구하는 진짜 정치 참여입니다.</p>
    `,
    video: `<div class="ratio"><iframe src="https://www.youtube.com/embed/kXmQZKfFjF8?si=yZunk4euQIkMaSxv" title="YouTube video player" allowfullscreen></iframe></div>`
  },
]

const spreadIndex = ref(0)
const currentSpread = computed(() => spreads[spreadIndex.value])
const pageNumber = computed(() => spreadIndex.value * 2 + 1)

function prevSpread() {
  if (spreadIndex.value > 0) spreadIndex.value--
}
function nextSpread() {
  if (spreadIndex.value < spreads.length - 1) spreadIndex.value++
}
function goToSpread(i) {
  spreadIndex.value = i
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&family=Pretendard:wght@400;700&display=swap');

.korean-magazine {
  background: #f4f6fa;
  min-height: 100vh;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 32px;
}
.spread {
  display: flex;
  box-shadow: 0 8px 32px -8px #b5beca33;
  border-radius: 16px;
  overflow: hidden;
  margin: 32px 0 16px 0;
  max-width: 900px;
  min-width: 600px;
  background: #fff;
  border: 1.5px solid #e0e5ea;
  position: relative;
}
.page {
  width: 380px;
  min-height: 500px;
  padding: 36px 32px 32px 32px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  background: transparent;
  border-right: 1px solid #e0e5ea;
}
.left {
  background: #f9fafb;
}
.right {
  background: #fff;
  border-left: 1px solid #e0e5ea;
  border-right: none;
}
.logo {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1.15em;
  color: #2d7be5;
  margin-bottom: 12px;
  letter-spacing: 0.06em;
  font-weight: 700;
}
.pagenumber {
  font-size: 0.92em;
  color: #9ba8b5;
  position: absolute;
  bottom: 18px;
  left: 32px;
  letter-spacing: 0.08em;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
}
.title {
  font-family: 'Noto Sans KR', 'Pretendard', Arial, sans-serif;
  color: #222;
  font-size: 1.35em;
  margin-bottom: 1.1em;
  font-weight: 700;
  letter-spacing: 0.01em;
  border-bottom: 2px solid #e0e5ea;
  padding-bottom: 0.2em;
}
.content {
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.text {
  font-size: 1.08em;
  line-height: 1.85;
  color: #323d4d;
  font-family: 'Noto Sans KR', 'Pretendard', Arial, sans-serif;
  margin-bottom: 1.1em;
}
.text strong {
  color: #2d7be5;
  background: #eaf3ff;
  padding: 0.08em 0.22em;
  border-radius: 0.15em;
  font-weight: 700;
}
.video {
  margin-top: 1.5em;
}
.ratio {
  position: relative;
  width: 100%;
  padding-bottom: 56.25%;
  height: 0;
  border-radius: 10px;
  overflow: hidden;
  background: #e9eef3;
  border: 1px solid #e0e5ea;
}
.ratio iframe {
  position: absolute;
  top: 0; left: 0;
  width: 100%;
  height: 100%;
  border: 0;
  border-radius: 10px;
  background: #e9eef3;
}
.controls {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1.2em;
  margin-bottom: 20px;
}
.controls button {
  background: #2d7be5;
  color: #fff;
  border: none;
  border-radius: 8px;
  width: 3.2em;
  height: 2.2em;
  font-size: 1.12em;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  cursor: pointer;
  box-shadow: 0 2px 8px #e0e5ea;
  transition: background 0.18s, color 0.18s, transform 0.14s;
  font-weight: 700;
}
.controls button:hover:not(:disabled) {
  background: #1764c8;
  color: #fff;
  transform: translateY(-2px) scale(1.04);
}
.controls button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.bars {
  display: flex;
  gap: 0.3em;
}
.bar {
  width: 2.2em;
  height: 0.36em;
  border-radius: 0.2em;
  background: #e0e5ea;
  transition: background 0.3s;
  cursor: pointer;
  border: none;
}
.bar.active {
  background: #2d7be5;
}
@media (max-width: 900px) {
  .spread { max-width: 98vw; min-width: 0; }
  .page { width: 48vw; min-width: 0; padding: 18px 6px; }
}
@media (max-width: 600px) {
  .spread { flex-direction: column; }
  .page { width: 98vw; }
}
</style>
