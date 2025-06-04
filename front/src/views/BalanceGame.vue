<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white border-b border-gray-200 sticky top-0 z-50">
      <div class="max-w-6xl mx-auto px-6 py-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-2">
            <TrendingUp class="w-5 h-5 text-rose-500" />
            <h1 class="text-xl font-medium text-gray-900">인기 투표</h1>
            <span class="text-sm text-rose-500 font-medium">실시간</span>
          </div>
          <button v-if="currentView === 'detail'" 
                  @click="currentView = 'main'" 
                  class="text-sm text-gray-600 hover:text-gray-900 transition-colors">
            ← 목록으로
          </button>
        </div>
      </div>
    </header>

    <!-- Main View -->
    <main v-if="currentView === 'main'" class="max-w-6xl mx-auto px-6 py-8">
      <!-- Popular Polls Section (깔끔한 슬라이드) -->
      <section class="mb-12">
        <div class="relative">
          <!-- Header -->
          <div class="flex items-center justify-between mb-6">
            <div class="flex items-center gap-3">
              <h2 class="text-xl font-medium text-gray-900">인기 투표</h2>
              <span class="text-sm text-gray-500">가장 많은 관심을 받고 있는 주제들</span>
            </div>
            
            <!-- Navigation Controls -->
            <div class="flex items-center gap-4">
              <!-- Slide Indicators -->
              <div class="flex gap-2">
                <button v-for="(_, index) in popularPolls" :key="index"
                        @click="goToPopularSlide(index)"
                        class="w-2 h-2 rounded-full transition-all duration-300"
                        :class="currentPopularSlide === index ? 'bg-gray-900' : 'bg-gray-300 hover:bg-gray-400'">
                </button>
              </div>
              
              <!-- Navigation Buttons -->
              <div class="flex gap-2">
                <button @click="previousPopularSlide" 
                        class="p-2 rounded-full bg-white border border-gray-200 hover:bg-gray-50 hover:border-gray-300 transition-all duration-200 shadow-sm"
                        :disabled="currentPopularSlide === 0">
                  <ChevronLeft class="w-5 h-5 text-gray-600" :class="{ 'text-gray-300': currentPopularSlide === 0 }" />
                </button>
                <button @click="nextPopularSlide" 
                        class="p-2 rounded-full bg-white border border-gray-200 hover:bg-gray-50 hover:border-gray-300 transition-all duration-200 shadow-sm"
                        :disabled="currentPopularSlide === popularPolls.length - 1">
                  <ChevronRight class="w-5 h-5 text-gray-600" :class="{ 'text-gray-300': currentPopularSlide === popularPolls.length - 1 }" />
                </button>
              </div>
            </div>
          </div>

          <!-- Sliding Container -->
          <div class="overflow-hidden">
            <div class="flex transition-transform duration-500 ease-in-out" 
                 :style="{ transform: `translateX(-${currentPopularSlide * 100}%)` }">
              <div v-for="poll in popularPolls" :key="poll.id" 
                   class="w-full flex-shrink-0">
                <div class="bg-white rounded-xl border border-gray-200 p-8 shadow-sm hover:shadow-md transition-shadow cursor-pointer"
                     @click="openPollDetail(poll)">
                  <div class="flex items-start justify-between mb-6">
                    <h3 class="text-2xl font-medium text-gray-900 leading-tight pr-4">{{ poll.title }}</h3>
                    <span class="text-xs bg-rose-50 text-rose-600 px-3 py-1.5 rounded-full font-medium whitespace-nowrap">
                      인기
                    </span>
                  </div>
                  
                  <p class="text-gray-600 mb-8 leading-relaxed text-lg">{{ poll.description }}</p>
                  
                  <!-- Vote Options Preview -->
                  <div class="space-y-4 mb-8">
                    <div v-for="option in poll.options" :key="option.id" 
                         class="relative">
                      <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-100">
                        <span class="text-base font-medium text-gray-700">{{ option.text }}</span>
                        <div class="flex items-center gap-3">
                          <span class="text-sm text-gray-500">{{ option.votes.toLocaleString() }}표</span>
                          <span class="text-base font-medium text-gray-900">({{ option.percentage }}%)</span>
                          <ChevronRight class="w-4 h-4 text-gray-400" />
                        </div>
                      </div>
                      <!-- Progress Bar -->
                      <div class="absolute bottom-0 left-0 h-1.5 bg-gray-900 rounded-b-lg transition-all duration-700"
                           :style="{ width: `${option.percentage}%` }"></div>
                    </div>
                  </div>
                  
                  <!-- Poll Stats -->
                  <div class="flex items-center justify-between">
                    <div class="flex items-center gap-6 text-gray-500">
                      <span class="flex items-center gap-2">
                        <Users class="w-4 h-4" />
                        {{ poll.totalVotes.toLocaleString() }}명 참여
                      </span>
                      <span class="flex items-center gap-2">
                        <Clock class="w-4 h-4" />
                        {{ poll.timeLeft }}
                      </span>
                    </div>
                    <span class="text-rose-500 font-medium">투표하기 →</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Categories (가로 2개 레이아웃) -->
      <section class="space-y-10">
        <div v-for="category in categories" :key="category.name" class="space-y-4">
          <!-- Category Header -->
          <div class="flex items-center justify-between">
            <h3 class="text-lg font-medium text-gray-900">{{ category.name }}</h3>
            <div class="flex items-center gap-3">
              <!-- Slide Indicators -->
              <div class="flex gap-1">
                <button v-for="(_, index) in Math.ceil(category.polls.length / 2)" :key="index"
                        @click="goToSlide(category.name, index)"
                        class="w-2 h-2 rounded-full transition-colors"
                        :class="currentSlides[category.name] === index ? 'bg-gray-900' : 'bg-gray-300'">
                </button>
              </div>
              <!-- Navigation Buttons -->
              <div class="flex gap-1">
                <button @click="previousSlide(category.name)" 
                        class="p-1.5 rounded-full hover:bg-gray-100 transition-colors"
                        :disabled="currentSlides[category.name] === 0">
                  <ChevronLeft class="w-4 h-4 text-gray-600" :class="{ 'text-gray-300': currentSlides[category.name] === 0 }" />
                </button>
                <button @click="nextSlide(category.name)" 
                        class="p-1.5 rounded-full hover:bg-gray-100 transition-colors"
                        :disabled="currentSlides[category.name] >= Math.ceil(category.polls.length / 2) - 1">
                  <ChevronRight class="w-4 h-4 text-gray-600" :class="{ 'text-gray-300': currentSlides[category.name] >= Math.ceil(category.polls.length / 2) - 1 }" />
                </button>
              </div>
            </div>
          </div>

          <!-- Sliding Container -->
          <div class="overflow-hidden">
            <div class="flex transition-transform duration-500 ease-in-out" 
                 :style="{ transform: `translateX(-${currentSlides[category.name] * 100}%)` }">
              <div v-for="(slidePolls, slideIndex) in getSlides(category.polls)" :key="slideIndex" 
                   class="w-full flex-shrink-0">
                <div class="grid grid-cols-2 gap-6">
                  <div v-for="poll in slidePolls" :key="poll.id"
                       class="bg-white rounded-lg border border-gray-200 p-5 hover:shadow-sm transition-shadow cursor-pointer"
                       @click="openPollDetail(poll)">
                    <div class="flex items-start justify-between mb-3">
                      <h4 class="font-medium text-gray-900 leading-tight">{{ poll.title }}</h4>
                      <span class="text-xs px-2 py-1 rounded-full font-medium whitespace-nowrap ml-2" :class="poll.statusClass">
                        {{ poll.status }}
                      </span>
                    </div>
                    <p class="text-sm text-gray-600 mb-4 leading-relaxed">{{ poll.description }}</p>
                    
                    <!-- Options with Progress Bars -->
                    <div class="space-y-3 mb-4">
                      <div v-for="option in poll.options" :key="option.id" 
                           class="relative">
                        <div class="flex items-center justify-between p-2.5 bg-gray-50 rounded-lg">
                          <span class="text-sm font-medium text-gray-700">{{ option.text }}</span>
                          <div class="flex items-center gap-2">
                            <span class="text-xs text-gray-500">{{ option.votes.toLocaleString() }}표</span>
                            <span class="text-xs font-medium text-gray-700">({{ option.percentage }}%)</span>
                            <ChevronRight class="w-3 h-3 text-gray-400" />
                          </div>
                        </div>
                        <!-- Colored Progress Bar -->
                        <div class="absolute bottom-0 left-0 h-1 rounded-b-lg transition-all duration-700"
                             :class="getProgressBarColor(option.percentage)"
                             :style="{ width: `${option.percentage}%` }"></div>
                      </div>
                    </div>
                    
                    <!-- Poll Stats -->
                    <div class="flex items-center justify-between text-xs text-gray-500">
                      <div class="flex items-center gap-3">
                        <span class="flex items-center gap-1">
                          <Users class="w-3 h-3" />
                          {{ poll.totalVotes.toLocaleString() }}명 참여
                        </span>
                        <span class="flex items-center gap-1">
                          <Clock class="w-3 h-3" />
                          {{ poll.timeLeft }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- Detail View (기존과 동일) -->
    <main v-else-if="currentView === 'detail'" class="max-w-3xl mx-auto px-6 py-8">
      <div class="bg-white rounded-lg border border-gray-200 p-8">
        <!-- Poll Header -->
        <div class="mb-8">
          <div class="flex items-start justify-between mb-4">
            <h1 class="text-2xl font-medium text-gray-900 leading-tight">{{ selectedPoll.title }}</h1>
            <span class="text-xs bg-rose-50 text-rose-600 px-3 py-1 rounded-full font-medium">
              {{ selectedPoll.status }}
            </span>
          </div>
          
          <!-- Poll Description -->
          <p class="text-gray-600 leading-relaxed mb-6">{{ selectedPoll.description }}</p>
          
          <!-- Poll Content -->
          <div class="prose prose-sm max-w-none text-gray-700 mb-8">
            <p>{{ selectedPoll.content }}</p>
          </div>
        </div>

        <!-- Voting Section -->
        <div class="mb-8">
          <h3 class="text-lg font-medium text-gray-900 mb-4">투표하기</h3>
          <div class="space-y-3">
            <label v-for="option in selectedPoll.options" :key="option.id" 
                   class="relative block cursor-pointer">
              <input type="radio" 
                     :value="option.id" 
                     v-model="selectedOption" 
                     :name="`poll-${selectedPoll.id}`"
                     class="sr-only">
              <div class="p-4 border-2 rounded-lg transition-all"
                   :class="selectedOption === option.id ? 'border-gray-900 bg-gray-50' : 'border-gray-200 hover:border-gray-300'">
                <div class="flex items-center justify-between">
                  <span class="font-medium text-gray-900">{{ option.text }}</span>
                  <div class="flex items-center gap-3">
                    <span class="text-sm text-gray-500">{{ option.votes.toLocaleString() }}표</span>
                    <span class="text-sm font-medium text-gray-700">({{ option.percentage }}%)</span>
                  </div>
                </div>
                <!-- Progress Bar -->
                <div class="mt-3 h-2 bg-gray-100 rounded-full overflow-hidden">
                  <div class="h-full rounded-full transition-all duration-500"
                       :class="getProgressBarColor(option.percentage)"
                       :style="{ width: `${option.percentage}%` }"></div>
                </div>
              </div>
            </label>
          </div>
          
          <button @click="submitVote" 
                  :disabled="!selectedOption || hasVoted"
                  class="mt-6 w-full py-3 px-4 bg-gray-900 text-white rounded-lg font-medium hover:bg-gray-800 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors">
            {{ hasVoted ? '투표 완료' : '투표하기' }}
          </button>
        </div>

        <!-- Poll Stats -->
        <div class="border-t border-gray-200 pt-6 mb-8">
          <div class="flex items-center justify-between text-sm text-gray-600">
            <div class="flex items-center gap-6">
              <span class="flex items-center gap-1">
                <Users class="w-4 h-4" />
                총 {{ selectedPoll.totalVotes.toLocaleString() }}명 참여
              </span>
              <span class="flex items-center gap-1">
                <Clock class="w-4 h-4" />
                {{ selectedPoll.timeLeft }}
              </span>
            </div>
          </div>
        </div>

        <!-- Comments Section -->
        <div class="border-t border-gray-200 pt-8">
          <h3 class="text-lg font-medium text-gray-900 mb-6">댓글 {{ comments.length }}개</h3>
          
          <!-- Comment Form -->
          <div class="mb-6">
            <textarea v-model="newComment" 
                      placeholder="의견을 남겨주세요..."
                      class="w-full p-3 border border-gray-200 rounded-lg resize-none focus:outline-none focus:ring-2 focus:ring-gray-900 focus:border-transparent"
                      rows="3"></textarea>
            <button @click="addComment"
                    :disabled="!newComment.trim()"
                    class="mt-2 px-4 py-2 bg-gray-900 text-white rounded-lg text-sm font-medium hover:bg-gray-800 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors">
              댓글 작성
            </button>
          </div>
          
          <!-- Comments List -->
          <div class="space-y-4">
            <div v-for="comment in comments" :key="comment.id" 
                 class="p-4 bg-gray-50 rounded-lg">
              <div class="flex items-center justify-between mb-2">
                <span class="font-medium text-gray-900">{{ comment.author }}</span>
                <span class="text-xs text-gray-500">{{ comment.time }}</span>
              </div>
              <p class="text-gray-700 text-sm leading-relaxed">{{ comment.text }}</p>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { TrendingUp, Users, Clock, ChevronRight, ChevronLeft } from 'lucide-vue-next'

// Current view state
const currentView = ref('main')
const selectedPoll = ref(null)
const selectedOption = ref(null)
const hasVoted = ref(false)
const newComment = ref('')

// Current slide positions
const currentPopularSlide = ref(0)
const currentSlides = reactive({
  '정치': 0,
  '사회': 0,
  '철학': 0
})

// Popular polls data (5 polls) - 기존과 동일
const popularPolls = ref([
  {
    id: 1,
    title: "2024년 가장 중요한 사회적 이슈는?",
    description: "올해 우리 사회가 가장 집중해야 할 문제에 대해 투표해주세요.",
    content: "2024년은 다양한 사회적 변화와 도전이 예상되는 해입니다. 경제적 불평등, 환경 문제, 디지털 전환, 인구 고령화 등 여러 이슈 중에서 가장 우선적으로 해결해야 할 문제가 무엇인지 여러분의 의견을 들어보고자 합니다.",
    options: [
      { id: 1, text: "경제 불평등 해소", votes: 2847, percentage: 38 },
      { id: 2, text: "환경 보호", votes: 1923, percentage: 26 },
      { id: 3, text: "교육 개혁", votes: 1456, percentage: 20 },
      { id: 4, text: "의료 시스템 개선", votes: 1234, percentage: 17 }
    ],
    totalVotes: 7460,
    timeLeft: "2일 남음",
    status: "인기"
  },
  {
    id: 2,
    title: "원격근무 확산에 대한 의견",
    description: "코로나19 이후 원격근무가 일반화되고 있습니다. 이에 대한 여러분의 생각은?",
    content: "팬데믹을 계기로 원격근무가 급속히 확산되었습니다. 생산성, 워라밸, 협업 등 다양한 측면에서 원격근무의 장단점이 논의되고 있습니다. 향후 근무 형태에 대한 여러분의 선호를 알아보고자 합니다.",
    options: [
      { id: 1, text: "완전 원격근무 선호", votes: 1890, percentage: 35 },
      { id: 2, text: "하이브리드 근무 선호", votes: 2430, percentage: 45 },
      { id: 3, text: "사무실 근무 선호", votes: 1080, percentage: 20 }
    ],
    totalVotes: 5400,
    timeLeft: "5일 남음",
    status: "진행중"
  },
  {
    id: 3,
    title: "기본소득 도입 찬반",
    description: "전 국민 기본소득 제도 도입에 대한 여러분의 의견을 들려주세요.",
    content: "기본소득은 모든 국민에게 조건 없이 정기적으로 지급하는 소득을 의미합니다. 사회보장제도의 새로운 패러다임으로 주목받고 있지만, 재원 마련과 근로 의욕 저하 등의 우려도 제기되고 있습니다.",
    options: [
      { id: 1, text: "적극 찬성", votes: 1650, percentage: 42 },
      { id: 2, text: "조건부 찬성", votes: 1320, percentage: 34 },
      { id: 3, text: "반대", votes: 930, percentage: 24 }
    ],
    totalVotes: 3900,
    timeLeft: "1주일 남음",
    status: "토론중"
  },
  {
    id: 4,
    title: "인공지능 발전과 일자리",
    description: "AI 기술 발전이 일자리에 미치는 영향에 대해 어떻게 생각하시나요?",
    content: "인공지능과 자동화 기술의 급속한 발전으로 많은 일자리가 변화하거나 사라질 것으로 예상됩니다. 동시에 새로운 형태의 일자리도 창출될 것으로 보입니다. 이러한 변화에 대한 여러분의 전망을 듣고 싶습니다.",
    options: [
      { id: 1, text: "일자리 감소가 더 클 것", votes: 1780, percentage: 48 },
      { id: 2, text: "새로운 일자리 창출이 더 클 것", votes: 1150, percentage: 31 },
      { id: 3, text: "균형을 이룰 것", votes: 770, percentage: 21 }
    ],
    totalVotes: 3700,
    timeLeft: "3일 남음",
    status: "마감임박"
  },
  {
    id: 5,
    title: "대중교통 무료화 정책",
    description: "대중교통 완전 무료화에 대한 여러분의 의견은?",
    content: "일부 지역에서 대중교통 무료화 정책을 시행하고 있습니다. 환경 보호와 교통비 부담 완화라는 장점이 있지만, 재정 부담과 서비스 질 저하 우려도 있습니다. 이 정책에 대한 여러분의 생각을 알아보겠습니다.",
    options: [
      { id: 1, text: "전면 무료화 찬성", votes: 1240, percentage: 40 },
      { id: 2, text: "부분 무료화 찬성", votes: 1085, percentage: 35 },
      { id: 3, text: "현행 유지", votes: 775, percentage: 25 }
    ],
    totalVotes: 3100,
    timeLeft: "6일 남음",
    status: "신규"
  }
])

// Categories with more polls for sliding
const categories = ref([
  {
    name: "정치",
    polls: [
      {
        id: 6,
        title: "정치 개혁의 우선순위는?",
        description: "현재 정치 시스템에서 가장 시급한 개혁 과제",
        content: "정치 개혁에 대한 다양한 의견이 제시되고 있습니다.",
        options: [
          { id: 1, text: "선거제도 개혁", votes: 1234, percentage: 56 },
          { id: 2, text: "정당 시스템 개선", votes: 987, percentage: 44 }
        ],
        totalVotes: 2221,
        timeLeft: "5일 남음",
        status: "진행중",
        statusClass: "bg-green-50 text-green-600"
      },
      {
        id: 7,
        title: "지방자치의 확대 방향",
        description: "지방정부의 권한 확대에 대한 의견",
        content: "지방자치 확대에 대한 논의가 활발합니다.",
        options: [
          { id: 1, text: "재정 자치권 확대", votes: 856, percentage: 54 },
          { id: 2, text: "현정 권한 확대", votes: 743, percentage: 46 }
        ],
        totalVotes: 1599,
        timeLeft: "3일 남음",
        status: "토론중",
        statusClass: "bg-yellow-50 text-yellow-600"
      },
      {
        id: 8,
        title: "국정감사 효과성 평가",
        description: "현재 국정감사 시스템이 효과적이라고 생각하시나요?",
        content: "국정감사는 국회의 중요한 견제 기능 중 하나입니다.",
        options: [
          { id: 1, text: "효과적", votes: 420, percentage: 35 },
          { id: 2, text: "비효과적", votes: 780, percentage: 65 }
        ],
        totalVotes: 1200,
        timeLeft: "4일 남음",
        status: "마감임박",
        statusClass: "bg-red-50 text-red-600"
      },
      {
        id: 9,
        title: "정치자금법 강화 방안",
        description: "정치자금 투명성을 위한 법적 규제 강화가 필요할까요?",
        content: "정치자금의 투명성 확보는 민주주의의 기본입니다.",
        options: [
          { id: 1, text: "강화 필요", votes: 1890, percentage: 70 },
          { id: 2, text: "현행 유지", votes: 810, percentage: 30 }
        ],
        totalVotes: 2700,
        timeLeft: "1주일 남음",
        status: "신규",
        statusClass: "bg-blue-50 text-blue-600"
      }
    ]
  },
  {
    name: "사회",
    polls: [
      {
        id: 10,
        title: "주 4일 근무제 도입",
        description: "주 4일 근무제 전면 도입에 대해 어떻게 생각하시나요?",
        content: "일과 삶의 균형을 위한 주 4일 근무제가 전 세계적으로 주목받고 있습니다.",
        options: [
          { id: 1, text: "즉시 도입", votes: 1890, percentage: 45 },
          { id: 2, text: "단계적 도입", votes: 2310, percentage: 55 }
        ],
        totalVotes: 4200,
        timeLeft: "5일 남음",
        status: "인기",
        statusClass: "bg-purple-50 text-purple-600"
      },
      {
        id: 11,
        title: "디지털 격차 해소",
        description: "세대간 디지털 격차 해소를 위한 정부 지원이 충분한가요?",
        content: "디지털 전환 시대에 세대간 격차가 사회 문제로 대두되고 있습니다.",
        options: [
          { id: 1, text: "충분", votes: 700, percentage: 30 },
          { id: 2, text: "부족", votes: 1640, percentage: 70 }
        ],
        totalVotes: 2340,
        timeLeft: "1주일 남음",
        status: "토론중",
        statusClass: "bg-yellow-50 text-yellow-600"
      },
      {
        id: 12,
        title: "환경보호와 경제성장",
        description: "환경보호와 경제성장 중 어느 것이 더 우선되어야 할까요?",
        content: "지속가능한 발전을 위한 균형점을 찾는 것이 중요합니다.",
        options: [
          { id: 1, text: "환경보호 우선", votes: 1890, percentage: 58 },
          { id: 2, text: "경제성장 우선", votes: 1370, percentage: 42 }
        ],
        totalVotes: 3260,
        timeLeft: "2일 남음",
        status: "진행중",
        statusClass: "bg-green-50 text-green-600"
      }
    ]
  },
  {
    name: "철학",
    polls: [
      {
        id: 13,
        title: "인공지능의 윤리적 책임",
        description: "AI가 내린 결정에 대한 책임은 누가 져야 할까요?",
        content: "AI 기술이 발전하면서 AI의 결정에 대한 윤리적, 법적 책임 문제가 중요해지고 있습니다.",
        options: [
          { id: 1, text: "개발자/회사", votes: 1120, percentage: 48 },
          { id: 2, text: "사용자", votes: 1210, percentage: 52 }
        ],
        totalVotes: 2330,
        timeLeft: "3일 남음",
        status: "심화토론",
        statusClass: "bg-indigo-50 text-indigo-600"
      },
      {
        id: 14,
        title: "개인의 자유 vs 집단의 이익",
        description: "개인의 자유와 집단의 이익이 충돌할 때 무엇이 우선일까요?",
        content: "민주주의 사회에서 개인의 자유와 집단의 이익 사이의 균형은 영원한 과제입니다.",
        options: [
          { id: 1, text: "개인의 자유", votes: 980, percentage: 42 },
          { id: 2, text: "집단의 이익", votes: 1350, percentage: 58 }
        ],
        totalVotes: 2330,
        timeLeft: "2일 남음",
        status: "진행중",
        statusClass: "bg-green-50 text-green-600"
      },
      {
        id: 15,
        title: "기술 발전과 인간성",
        description: "기술 발전이 인간의 본질적 가치를 훼손한다고 생각하시나요?",
        content: "급속한 기술 발전 속에서 인간의 정체성과 가치에 대한 고민이 깊어지고 있습니다.",
        options: [
          { id: 1, text: "훼손한다", votes: 1240, percentage: 55 },
          { id: 2, text: "훼손하지 않는다", votes: 1010, percentage: 45 }
        ],
        totalVotes: 2250,
        timeLeft: "4일 남음",
        status: "토론중",
        statusClass: "bg-yellow-50 text-yellow-600"
      }
    ]
  }
])

// Comments data
const comments = ref([
  {
    id: 1,
    author: "시민A",
    text: "경제 불평등 문제가 정말 심각하다고 생각합니다. 정부의 적극적인 정책이 필요해요.",
    time: "2시간 전"
  },
  {
    id: 2,
    author: "관심있는시민",
    text: "환경 문제도 중요하지만, 당장 생계가 어려운 사람들이 많아서 경제 문제가 우선이라고 봅니다.",
    time: "4시간 전"
  },
  {
    id: 3,
    author: "정책연구자",
    text: "이런 이슈들은 서로 연결되어 있어서 종합적인 접근이 필요할 것 같습니다.",
    time: "6시간 전"
  }
])

// Helper function to create slides (2 polls per slide)
const getSlides = (polls) => {
  const slides = []
  for (let i = 0; i < polls.length; i += 2) {
    slides.push(polls.slice(i, i + 2))
  }
  return slides
}

// Progress bar color based on percentage
const getProgressBarColor = (percentage) => {
  if (percentage >= 60) return 'bg-gray-900'
  if (percentage >= 40) return 'bg-gray-700'
  if (percentage >= 20) return 'bg-gray-500'
  return 'bg-gray-400'
}

// Popular polls navigation
const nextPopularSlide = () => {
  if (currentPopularSlide.value < popularPolls.value.length - 1) {
    currentPopularSlide.value++
  }
}

const previousPopularSlide = () => {
  if (currentPopularSlide.value > 0) {
    currentPopularSlide.value--
  }
}

const goToPopularSlide = (slideIndex) => {
  currentPopularSlide.value = slideIndex
}

// Category navigation functions
const nextSlide = (categoryName) => {
  const category = categories.value.find(cat => cat.name === categoryName)
  const maxSlides = Math.ceil(category.polls.length / 2) - 1
  if (currentSlides[categoryName] < maxSlides) {
    currentSlides[categoryName]++
  }
}

const previousSlide = (categoryName) => {
  if (currentSlides[categoryName] > 0) {
    currentSlides[categoryName]--
  }
}

const goToSlide = (categoryName, slideIndex) => {
  currentSlides[categoryName] = slideIndex
}

// Functions
const openPollDetail = (poll) => {
  selectedPoll.value = poll
  currentView.value = 'detail'
  selectedOption.value = null
  hasVoted.value = false
}

const submitVote = () => {
  if (selectedOption.value && !hasVoted.value) {
    hasVoted.value = true
    console.log('Vote submitted:', selectedOption.value)
  }
}

const addComment = () => {
  if (newComment.value.trim()) {
    comments.value.unshift({
      id: Date.now(),
      author: "익명",
      text: newComment.value.trim(),
      time: "방금 전"
    })
    newComment.value = ''
  }
}
</script>

<style scoped>
.prose {
  line-height: 1.6;
}
</style>