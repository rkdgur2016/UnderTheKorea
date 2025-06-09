<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-gray-100">
    <!-- 헤더 섹션 -->
    <section class="relative py-20 overflow-hidden">
      <div class="absolute inset-0 bg-gradient-to-r from-slate-800 to-slate-900"></div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <div class="mb-8">
          <h1 class="text-4xl md:text-6xl font-bold text-white mb-6 leading-tight">
            Under The Korea
          </h1>
          <p class="text-xl md:text-2xl text-slate-300 max-w-3xl mx-auto leading-relaxed">
            왜 우리는 이 플랫폼을 만들었는가?<br>
            대한민국의 미래를 위한 우리의 철학과 신념
          </p>
        </div>
        <div class="w-24 h-1 bg-gradient-to-r from-blue-500 to-purple-500 mx-auto rounded-full"></div>
      </div>
    </section>

    <!-- 첫 번째 섹션: 정치를 외면한 대가 -->
    <section class="py-20">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <div class="inline-flex items-center px-4 py-2 bg-red-100 text-red-800 rounded-full text-sm font-medium mb-4">
            {{ spreads[0].logo }}
          </div>
          <h2 class="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
            {{ spreads[0].title }}
          </h2>
        </div>
        
        <div class="grid lg:grid-cols-2 gap-12 items-start">
          <!-- 왼쪽 텍스트 -->
          <div class="bg-white rounded-2xl p-8 shadow-lg border border-slate-200">
            <div class="prose prose-slate max-w-none" v-html="spreads[0].leftText"></div>
          </div>
          
          <!-- 오른쪽 텍스트 -->
          <div class="bg-white rounded-2xl p-8 shadow-lg border border-slate-200">
            <div class="prose prose-slate max-w-none" v-html="spreads[0].rightText"></div>
          </div>
        </div>
        
        <!-- 비디오 -->
        <div class="mt-12 max-w-4xl mx-auto">
          <div class="bg-white rounded-2xl p-6 shadow-lg border border-slate-200">
            <div class="aspect-video rounded-lg overflow-hidden" v-html="spreads[0].video"></div>
          </div>
        </div>
      </div>
    </section>

    <!-- 두 번째 섹션: 정보의 홍수 -->
    <section class="py-20 bg-slate-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <div class="inline-flex items-center px-4 py-2 bg-orange-100 text-orange-800 rounded-full text-sm font-medium mb-4">
            {{ spreads[1].logo }}
          </div>
          <h2 class="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
            {{ spreads[1].title }}
          </h2>
        </div>
        
        <div class="grid lg:grid-cols-3 gap-8">
          <!-- 비디오를 먼저 배치 -->
          <div class="lg:col-span-1">
            <div class="bg-white rounded-2xl p-6 shadow-lg border border-slate-200 sticky top-8">
              <div class="aspect-video rounded-lg overflow-hidden" v-html="spreads[1].video"></div>
            </div>
          </div>
          
          <!-- 텍스트 콘텐츠 -->
          <div class="lg:col-span-2 space-y-8">
            <div class="bg-gradient-to-br from-orange-50 to-red-50 rounded-2xl p-8 border border-orange-200">
              <div class="prose prose-slate max-w-none" v-html="spreads[1].leftText"></div>
            </div>
            <div class="bg-gradient-to-br from-red-50 to-pink-50 rounded-2xl p-8 border border-red-200">
              <div class="prose prose-slate max-w-none" v-html="spreads[1].rightText"></div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 세 번째 섹션: 철학의 필요성 -->
    <section class="py-20">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <div class="inline-flex items-center px-4 py-2 bg-purple-100 text-purple-800 rounded-full text-sm font-medium mb-4">
            {{ spreads[2].logo }}
          </div>
          <h2 class="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
            {{ spreads[2].title }}
          </h2>
        </div>
        
        <div class="relative">
          <!-- 중앙 비디오 -->
          <div class="max-w-3xl mx-auto mb-12">
            <div class="bg-white rounded-2xl p-6 shadow-xl border border-slate-200">
              <div class="aspect-video rounded-lg overflow-hidden" v-html="spreads[2].video"></div>
            </div>
          </div>
          
          <!-- 좌우 텍스트 -->
          <div class="grid md:grid-cols-2 gap-8">
            <div class="bg-gradient-to-br from-purple-50 to-indigo-50 rounded-2xl p-8 border border-purple-200 transform md:-rotate-1 hover:rotate-0 transition-transform duration-300">
              <div class="prose prose-slate max-w-none" v-html="spreads[2].leftText"></div>
            </div>
            <div class="bg-gradient-to-br from-indigo-50 to-blue-50 rounded-2xl p-8 border border-indigo-200 transform md:rotate-1 hover:rotate-0 transition-transform duration-300">
              <div class="prose prose-slate max-w-none" v-html="spreads[2].rightText"></div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 네 번째 섹션: 결단의 시간 -->
    <section class="py-20 bg-gradient-to-br from-slate-800 to-slate-900 text-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <div class="inline-flex items-center px-4 py-2 bg-green-500/20 text-green-300 rounded-full text-sm font-medium mb-4">
            {{ spreads[3].logo }}
          </div>
          <h2 class="text-3xl md:text-4xl font-bold mb-6">
            {{ spreads[3].title }}
          </h2>
        </div>
        
        <div class="grid lg:grid-cols-2 gap-12 items-center">
          <!-- 텍스트 콘텐츠 -->
          <div class="space-y-8">
            <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-8 border border-white/20">
              <div class="prose prose-invert max-w-none" v-html="spreads[3].leftText"></div>
            </div>
            <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-8 border border-white/20">
              <div class="prose prose-invert max-w-none" v-html="spreads[3].rightText"></div>
            </div>
          </div>
          
          <!-- 비디오 -->
          <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-6 border border-white/20">
            <div class="aspect-video rounded-lg overflow-hidden" v-html="spreads[3].video"></div>
          </div>
        </div>
      </div>
    </section>

    <!-- 추가 섹션: Under The Korea의 약속 -->
    <section class="py-20 bg-gradient-to-br from-blue-50 to-indigo-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl md:text-4xl font-bold text-slate-800 mb-6">
            Under The Korea의 약속
          </h2>
          <p class="text-xl text-slate-600 max-w-3xl mx-auto">
            우리는 단순한 정보 제공을 넘어, 진정한 시민 의식을 깨우는 플랫폼이 되겠습니다.
          </p>
        </div>
        
        <div class="grid md:grid-cols-3 gap-8">
          <div class="bg-white rounded-2xl p-8 shadow-lg border border-slate-200 text-center group hover:shadow-xl transition-shadow duration-300">
            <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:bg-blue-200 transition-colors">
              <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-slate-800 mb-4">깊이 있는 사유</h3>
            <p class="text-slate-600 leading-relaxed">
              표면적인 정보가 아닌, 본질을 꿰뚫는 철학적 사고를 제공합니다. 
              복잡한 현실을 단순화하지 않고, 진정한 이해를 추구합니다.
            </p>
          </div>
          
          <div class="bg-white rounded-2xl p-8 shadow-lg border border-slate-200 text-center group hover:shadow-xl transition-shadow duration-300">
            <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:bg-green-200 transition-colors">
              <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-slate-800 mb-4">건설적 토론</h3>
            <p class="text-slate-600 leading-relaxed">
              서로 다른 관점을 존중하면서도, 진리를 향한 건설적 대화를 촉진합니다. 
              감정이 아닌 이성으로 소통하는 문화를 만들어갑니다.
            </p>
          </div>
          
          <div class="bg-white rounded-2xl p-8 shadow-lg border border-slate-200 text-center group hover:shadow-xl transition-shadow duration-300">
            <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-6 group-hover:bg-purple-200 transition-colors">
              <svg class="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-slate-800 mb-4">실천적 참여</h3>
            <p class="text-slate-600 leading-relaxed">
              이론에 그치지 않고, 실제 사회 변화로 이어지는 참여를 독려합니다. 
              개인의 결단이 집단의 변화로 이어지도록 돕습니다.
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- 마무리 섹션 -->
    <section class="py-20 bg-slate-800 text-white">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 class="text-3xl md:text-4xl font-bold mb-8">
          이제 시작입니다
        </h2>
        <p class="text-xl text-slate-300 leading-relaxed">
          Under The Korea는 단순한 웹사이트가 아닙니다.<br>
          이는 깨어있는 시민들이 모여 대한민국의 미래를 그려나가는 공간입니다.<br>
          당신의 참여가 이 나라의 변화를 만들어갑니다.
        </p>
        
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <router-link 
            to="/philosophy?category=철학" 
            class="bg-white text-slate-800 px-8 py-4 rounded-lg font-semibold hover:bg-slate-100 transition-colors shadow-lg"
          >
            철학 토론 참여하기
          </router-link>
          <router-link 
            to="/politics?category=정치" 
            class="border-2 border-white text-white px-8 py-4 rounded-lg font-semibold hover:bg-white hover:text-slate-800 transition-all"
          >
            정치 현안 살펴보기
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

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
    video: `<iframe class="w-full h-full" src="https://www.youtube.com/embed/mZDmFo27vM0?si=dIpT8RZd9CRIca3J" title="YouTube video player" allowfullscreen></iframe>`
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
    video: `<iframe class="w-full h-full" src="https://www.youtube.com/embed/WR9tn5oEUvs?si=1qNurQR1c8MiYbp4" title="YouTube video player" allowfullscreen></iframe>`
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
    video: `<iframe class="w-full h-full" src="https://www.youtube.com/embed/lMG6h2rVmuM?si=05RSrWXyTDFjkKYS" title="YouTube video player" allowfullscreen></iframe>`
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
      <p>그러니 이제는 멈춰 서십시오. "모르겠다, 너무 많다"는 말 뒤에 숨지 마십시오. 자신만의 철학으로 세상을 보고, 결단하십시오.</p>
      <p><strong>그것이 자유로운 시민, 책임 있는 세대가 되는 첫걸음입니다.</strong><br>그리고 그것이야말로, 이 시대가 당신에게 요구하는 진짜 정치 참여입니다.</p>
    `,
    video: `<iframe class="w-full h-full" src="https://www.youtube.com/embed/kXmQZKfFjF8?si=yZunk4euQIkMaSxv" title="YouTube video player" allowfullscreen></iframe>`
  },
]
</script>

<style scoped>
/* 프로즈 스타일 커스터마이징 */
body{
  margin:0;
}
.prose p {
  margin-bottom: 1rem;
  line-height: 1.7;
}

.prose strong {
  font-weight: 700;
  color: #1e293b;
}

.prose-invert p {
  color: #e2e8f0;
}

.prose-invert strong {
  color: #f8fafc;
}

/* 부드러운 애니메이션 */
.transition-transform {
  transition: transform 0.3s ease-in-out;
}

/* 스크롤 애니메이션을 위한 준비 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fadeInUp 0.6s ease-out forwards;
}
</style>