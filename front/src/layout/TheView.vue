<template>
  <div
    class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50"
  >
    <!-- Hero Section -->
    <section
      class="bg-gradient-to-r from-slate-800 to-slate-900 text-white py-20 px-4"
    >
      <div class="container mx-auto text-center">
        <div class="mb-6">
          <span class="text-6xl">📢</span>
        </div>
        <h1 class="text-4xl md:text-6xl font-bold mb-6">
          당신의 목소리가 세상을 바꿉니다. ✨
        </h1>
        <p class="text-xl md:text-2xl mb-8 text-blue-100">
          우리 사회의 미래는 당신의 관심과 참여로 시작됩니다. 중요한 이슈 앞에서
          더 이상 침묵하지 마세요. 당신의 목소리가 모여 더 나은 공동체를
          만듭니다. 지금 바로 참여하여 변화의 주역이 되세요! 🌟
        </p>
      </div>
    </section>

    <div class="container mx-auto px-4 py-12">
      <div class="grid lg:grid-cols-4 gap-8">
        <!-- Main Content -->
        <div class="lg:col-span-3 space-y-8">
          <!-- Recent Posts - Single Issue View -->
          <section>
            <div class="flex items-center justify-between mb-8">
              <div>
                <h2 class="text-3xl font-bold text-gray-800 mb-2">
                  📰 오늘의 핫이슈
                </h2>
                <p class="text-gray-600">
                  시민 여러분이 관심 가져야 할 이슈들을 전해드려요
                </p>
              </div>
              <div class="flex items-center gap-3">
                <span
                  class="text-sm text-gray-500 bg-gray-100 px-3 py-1 rounded-full"
                >
                  {{ currentIssueIndex + 1 }} / {{ recentPosts.length }}
                </span>
                <button
                  @click="prevIssue"
                  class="border border-gray-300 rounded-full p-2 hover:bg-gray-50 transition-colors"
                >
                  <ChevronLeftIcon class="h-4 w-4" />
                </button>
                <button
                  @click="nextIssue"
                  class="border border-gray-300 rounded-full p-2 hover:bg-gray-50 transition-colors"
                >
                  <ChevronRightIcon class="h-4 w-4" />
                </button>
              </div>
            </div>

            <div
              class="shadow-xl border-0 overflow-hidden bg-white/80 backdrop-blur rounded-lg"
            >
              <!-- Header -->
              <div
                class="bg-gradient-to-r from-slate-800 to-slate-900 text-white p-6"
              >
                <div class="flex items-start justify-between mb-4">
                  <div class="flex-1">
                    <div class="flex items-center gap-2 mb-3">
                      <span
                        class="bg-white/20 text-white border border-white/30 rounded-full px-3 py-1 text-sm"
                      >
                        {{ currentIssue.category }}
                      </span>
                      <span
                        :class="[
                          'rounded-full px-3 py-1 text-sm',
                          currentIssue.urgency === '높음'
                            ? 'bg-red-500/20 text-red-100 border border-red-300/30'
                            : 'bg-white/20 text-white border border-white/30',
                        ]"
                      >
                        {{
                          currentIssue.urgency === "높음"
                            ? "🚨 중요"
                            : "📝 일반"
                        }}
                      </span>
                      <span class="text-sm text-blue-100">{{
                        currentIssue.time
                      }}</span>
                    </div>
                    <h3 class="text-2xl md:text-3xl font-bold mb-3">
                      {{ currentIssue.title }}
                    </h3>
                    <p class="text-blue-100 text-lg mb-4">
                      {{ currentIssue.summary }}
                    </p>
                    <div class="flex items-center gap-4 text-sm text-blue-100">
                      <span>✍️ {{ currentIssue.author }}</span>
                      <span>📍 {{ currentIssue.location }}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="p-8">
                <!-- Tags -->
                <div class="flex flex-wrap gap-2 mb-6">
                  <span
                    v-for="tag in currentIssue.tags"
                    :key="tag"
                    class="text-xs rounded-full bg-blue-50 text-blue-600 border border-blue-200 px-3 py-1"
                  >
                    #{{ tag }}
                  </span>
                </div>

                <!-- Content -->
                <div class="prose max-w-none mb-8">
                  <div
                    class="text-gray-700 leading-relaxed text-lg whitespace-pre-line"
                  >
                    {{ currentIssue.fullContent }}
                  </div>
                </div>

                <!-- Images -->
                <div
                  v-if="currentIssue.images && currentIssue.images.length > 0"
                  class="mb-8"
                >
                  <h4
                    class="font-semibold mb-4 text-lg flex items-center gap-2"
                  >
                    📸 관련 자료
                  </h4>
                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <img
                      v-for="(image, index) in currentIssue.images"
                      :key="index"
                      :src="image"
                      :alt="`관련 자료 ${index + 1}`"
                      class="rounded-xl border shadow-sm hover:shadow-lg transition-all cursor-pointer"
                    />
                  </div>
                </div>

                <!-- Related Links -->
                <div
                  v-if="
                    currentIssue.relatedLinks &&
                    currentIssue.relatedLinks.length > 0
                  "
                  class="mb-8"
                >
                  <h4
                    class="font-semibold mb-4 text-lg flex items-center gap-2"
                  >
                    🔗 더 알아보기
                  </h4>
                  <div class="flex flex-wrap gap-3">
                    <button
                      v-for="(link, index) in currentIssue.relatedLinks"
                      :key="index"
                      class="rounded-full bg-blue-50 text-blue-600 border border-blue-200 hover:bg-blue-100 px-4 py-2 text-sm transition-colors"
                    >
                      {{ link }}
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Navigation Dots -->
            <div class="flex justify-center gap-3 mt-6">
              <button
                v-for="(_, index) in recentPosts"
                :key="index"
                @click="setCurrentIssue(index)"
                :class="[
                  'w-3 h-3 rounded-full transition-all',
                  index === currentIssueIndex
                    ? 'bg-gradient-to-r from-blue-500 to-purple-600 scale-125'
                    : 'bg-gray-300 hover:bg-gray-400',
                ]"
              />
            </div>
          </section>
        </div>

        <!-- Sidebar -->
        <div class="space-y-6">
          <!-- Upcoming Events -->
          <section>
            <h3
              class="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2"
            >
              📅 다가오는 일정
            </h3>
            <div class="space-y-4">
              <div
                v-for="event in upcomingEvents"
                :key="event.id"
                class="border-0 shadow-lg bg-white/80 backdrop-blur overflow-hidden rounded-lg"
              >
                <div class="p-4">
                  <div class="flex items-start gap-3">
                    <div class="text-2xl">{{ event.emoji }}</div>
                    <div class="flex-1">
                      <div class="flex items-center gap-2 mb-2">
                        <span
                          class="bg-green-100 text-green-700 border border-green-200 rounded-full text-xs px-2 py-1"
                        >
                          {{ event.status }}
                        </span>
                      </div>
                      <h4 class="font-semibold text-gray-900 mb-2 text-sm">
                        {{ event.title }}
                      </h4>
                      <div class="space-y-1 text-xs text-gray-600">
                        <div class="flex items-center gap-2">
                          <ClockIcon class="h-3 w-3" />
                          <span>{{ event.date }} {{ event.time }}</span>
                        </div>
                        <div class="flex items-center gap-2">
                          <MapPinIcon class="h-3 w-3" />
                          <span>{{ event.location }}</span>
                        </div>
                        <div class="flex items-center gap-2">
                          <UsersIcon class="h-3 w-3" />
                          <span>{{ event.participants }}명 참여예정</span>
                        </div>
                      </div>
                      <button
                        class="w-full mt-3 rounded-full bg-gradient-to-r from-slate-800 to-slate-900 text-white py-2 text-sm font-semibold transition-all hover:shadow-lg"
                      >
                        참여하기 🙋‍♀️
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <!-- Quick Actions -->
          <section>
            <h3
              class="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2"
            >
              ⚡ 빠른 참여
            </h3>
            <div class="space-y-3">
              <button
                class="w-full justify-start rounded-full bg-blue-50 text-blue-600 border border-blue-200 hover:bg-blue-100 py-3 px-4 text-sm transition-colors flex items-center gap-2"
              >
                <MessageSquareIcon class="h-4 w-4" />새 이야기 올리기 ✍️
              </button>
              <button
                class="w-full justify-start rounded-full bg-purple-50 text-purple-600 border border-purple-200 hover:bg-purple-100 py-3 px-4 text-sm transition-colors flex items-center gap-2"
              >
                <CalendarIcon class="h-4 w-4" />일정 등록하기 📝
              </button>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import {
  MessageSquare as MessageSquareIcon,
  Calendar as CalendarIcon,
  ChevronLeft as ChevronLeftIcon,
  ChevronRight as ChevronRightIcon,
  Heart as HeartIcon,
  Share2 as Share2Icon,
  Bookmark as BookmarkIcon,
  Clock as ClockIcon,
  MapPin as MapPinIcon,
  Users as UsersIcon,
  Vote as VoteIcon,
} from "lucide-vue-next";

const recentPosts = ref([
  {
    id: 1,
    title: "🔍 개표 과정에서 발견된 의혹사항들",
    author: "시민감시단",
    time: "2시간 전",
    votes: 1247,
    comments: 89,
    category: "증거자료",
    location: "서울시 전 지역",
    urgency: "높음",
    tags: ["개표", "CCTV", "투명성", "감시"],
    summary:
      "개표소 CCTV 영상을 분석한 결과, 여러 의혹스러운 상황들이 발견되었어요. 함께 살펴보시죠! 🤔",
    fullContent: `안녕하세요, 시민 여러분! 👋

최근 개표소 CCTV 영상을 꼼꼼히 분석해본 결과, 몇 가지 궁금한 점들이 발견되었어요.

🔎 주요 발견사항

1. 개표 과정이 조금씩 달랐어요
- 투표용지를 분류하는 기준이 개표소마다 달랐어요
- 일부 개표소에서는 참관인들이 가까이 가기 어려웠어요
- 개표가 예상보다 오래 걸린 곳들이 있었어요

2. 전자개표기에 대한 궁금증
- 몇몇 기기에서 이상한 동작이 포착되었어요
- 기기 점검 과정을 자세히 볼 수 없었어요
- 백업 시스템이 제대로 작동했는지 확실하지 않아요

3. 수기 검표 과정의 아쉬운 점들
- 검표 기준이 명확하지 않았어요
- 이의제기를 했을 때 처리 과정이 투명하지 않았어요
- 최종 집계할 때 다시 한 번 확인하는 과정이 부족했어요

📋 우리가 확인한 자료들
- 15개 개표소의 CCTV 영상 분석
- 개표참관인분들의 생생한 증언
- 개표 과정의 시간대별 분석

이런 문제점들 때문에 선거가 정말 공정했는지 의문이 들어요.
더 투명하고 신뢰할 수 있는 선거를 위해 개선이 필요해 보여요! 💪

여러분은 어떻게 생각하시나요? 댓글로 의견을 나눠주세요! 💬`,
    images: [
      "/placeholder.svg?height=300&width=500",
      "/placeholder.svg?height=200&width=400",
    ],
    relatedLinks: ["📍 개표소 위치 정보", "📜 관련 법령", "🌍 해외 사례"],
  },
  {
    id: 2,
    title: "🌍 해외는 어떻게 선거를 감시할까요?",
    author: "선거연구소",
    time: "4시간 전",
    votes: 892,
    comments: 56,
    category: "분석",
    location: "해외 사례",
    urgency: "보통",
    tags: ["해외사례", "독일", "프랑스", "투명성"],
    summary:
      "독일과 프랑스의 선거 감시 시스템을 살펴보니 우리와 많이 달랐어요. 어떤 점이 다른지 알아볼까요? ✈️",
    fullContent: `안녕하세요! 😊

독일과 프랑스 같은 나라들은 선거를 어떻게 감시하는지 궁금해서 조사해봤어요!
우리나라와 비교해보니 정말 많이 달랐어요.

🇩🇪 독일의 투명한 선거 시스템

완전히 공개해요!
- 개표하는 모든 과정을 누구나 볼 수 있어요
- 시민 누구나 개표소에 가서 지켜볼 수 있어요
- 개표 현황을 인터넷으로 실시간 확인할 수 있어요

시민 참여를 적극 환영해요
- 개표참관인이 되기 위한 조건이 까다롭지 않아요
- 시민감시단을 공식적으로 인정해줘요
- 이상한 점을 발견하면 쉽게 문제제기할 수 있어요

🇫🇷 프랑스의 시민 중심 시스템

처음부터 끝까지 시민과 함께
- 투표용지를 만들 때부터 시민감시단이 참여해요
- 실시간으로 모든 과정을 모니터링해요
- 개표 결과를 바로바로 공개해요

최신 기술로 더 안전하게
- 블록체인으로 투표 기록을 관리해요
- AI가 이상한 패턴을 자동으로 찾아내요
- 여러 번 검증하는 시스템이 있어요

🚀 우리도 이렇게 개선하면 어떨까요?

1. 시민 참여 늘리기 - 더 많은 분들이 참여할 수 있게
2. 기술로 투명성 높이기 - 최신 기술 활용
3. 법과 제도 개선하기 - 더 나은 규칙 만들기
4. 국제 기준 따라가기 - 세계 수준에 맞추기

해외 사례를 보니 우리도 더 투명하고 믿을 수 있는 선거 시스템을 만들 수 있을 것 같아요!

여러분 생각은 어떠세요? 🤗`,
    images: ["/placeholder.svg?height=250&width=400"],
    relatedLinks: [
      "🇩🇪 독일 선거법",
      "🇫🇷 프랑스 선거 시스템",
      "🇪🇺 EU 선거 기준",
    ],
  },
  {
    id: 3,
    title: "💻 투표용지 관리, 이대로 괜찮을까요?",
    author: "IT전문가그룹",
    time: "6시간 전",
    votes: 634,
    comments: 43,
    category: "기술분석",
    location: "전국",
    urgency: "높음",
    tags: ["투표용지", "보안", "블록체인", "시스템"],
    summary:
      "현재 투표용지를 관리하는 시스템에 몇 가지 문제점이 있어요. IT 전문가들이 쉽게 설명해드릴게요! 🔧",
    fullContent: `안녕하세요, IT 전문가 그룹입니다! 👨‍💻👩‍💻

투표용지를 어떻게 관리하는지 살펴보니 몇 가지 걱정되는 부분들이 있어서 여러분과 공유하려고 해요.

😰 현재 시스템의 문제점들

1. 투표용지 번호 관리가 애매해요
- 투표용지마다 있는 일련번호를 제대로 추적하기 어려워요
- 인쇄한 양과 실제 사용한 양이 맞는지 확인하기 어려워요
- 여분으로 준비한 투표용지 관리가 허술해요

2. 인쇄할 때 보안이 부족해요
- 투표용지를 인쇄하는 업체의 보안 검사가 충분하지 않아요
- 인쇄 과정을 지켜보는 시스템이 없어요
- 품질을 확인하는 기준이 명확하지 않아요

3. 배송하고 보관할 때도 문제가 있어요
- 투표용지를 운송할 때 어디에 있는지 추적하기 어려워요
- 보관하는 곳의 보안 기준이 부족해요
- 누가 접근할 수 있는지 관리가 허술해요

💡 이렇게 개선하면 어떨까요?

1. 블록체인으로 완벽하게 관리하기
- 투표용지가 만들어져서 버려질 때까지 모든 과정을 기록해요
- 누구도 조작할 수 없는 디지털 서명을 사용해요
- 언제든지 실시간으로 확인하고 검증할 수 있어요

2. AI가 이상한 점을 찾아내요
- 패턴을 분석해서 이상한 징후를 자동으로 발견해요
- 품질 검사를 자동화해서 더 정확하게 해요
- 문제가 생기면 즉시 알림을 보내고 대응해요

3. 시민이 직접 확인할 수 있게 해요
- QR 코드로 시민들이 직접 검증할 수 있어요
- 스마트폰 앱으로 실시간 확인이 가능해요
- 모든 데이터를 투명하게 공개해요

📅 단계별 도입 계획

- 1단계 (3개월): 작은 규모로 테스트해보기
- 2단계 (6개월): 시스템 만들고 테스트하기
- 3단계 (12개월): 전국에 적용하고 운영하기

이런 기술적 개선을 통해 투표용지 관리를 훨씬 더 투명하고 믿을 수 있게 만들 수 있어요!

궁금한 점이 있으시면 언제든 댓글로 물어보세요! 😊`,
    images: [
      "/placeholder.svg?height=300&width=600",
      "/placeholder.svg?height=200&width=300",
    ],
    relatedLinks: [
      "🔗 블록체인 기술",
      "🛡️ 선거 보안 기준",
      "🌐 해외 기술 사례",
    ],
  },
]);

const upcomingEvents = ref([
  {
    id: 1,
    title: "🗣️ 선거 투명성 요구 집회",
    date: "1월 15일",
    time: "오후 2시",
    location: "광화문 광장",
    participants: 1250,
    status: "참여 가능",
    emoji: "📢",
  },
  {
    id: 2,
    title: "🔍 부정선거 진상규명 촉구 시위",
    date: "1월 18일",
    time: "오후 6시",
    location: "여의도 국회의사당 앞",
    participants: 890,
    status: "모집중",
    emoji: "✊",
  },
  {
    id: 3,
    title: "👥 시민 감시단 발족식",
    date: "1월 20일",
    time: "오후 3시",
    location: "시민회관 대강당",
    participants: 340,
    status: "신청 가능",
    emoji: "🎉",
  },
]);

// 스케쥴 로드
const loadSchedule = async () => {
  const response = await axios.post("/schedule/loadSchedule");
  console.log("서버 응답:", response.data);
};

const currentIssueIndex = ref(0);

const currentIssue = computed(() => recentPosts.value[currentIssueIndex.value]);

const nextIssue = () => {
  currentIssueIndex.value =
    (currentIssueIndex.value + 1) % recentPosts.value.length;
};

const prevIssue = () => {
  currentIssueIndex.value =
    (currentIssueIndex.value - 1 + recentPosts.value.length) %
    recentPosts.value.length;
};

const setCurrentIssue = (index) => {
  currentIssueIndex.value = index;
};
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.prose {
  max-width: none;
}

.transition-all {
  transition: all 0.3s ease;
}

.transition-colors {
  transition: color 0.3s ease, background-color 0.3s ease;
}

.backdrop-blur {
  backdrop-filter: blur(8px);
}
</style>
