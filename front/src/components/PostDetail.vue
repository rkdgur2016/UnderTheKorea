<template>
  <div v-if="post" class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-md"
    @click.self="closeDetail">
    <div :class="[
      'bg-white rounded-xl shadow-2xl max-w-4xl w-full max-h-[90vh] overflow-hidden transition-all duration-300 mx-auto',
      showDetail ? 'opacity-100 scale-100' : 'opacity-0 scale-95',
    ]">
      <div class="flex items-center justify-between p-6 border-b border-slate-200 bg-slate-50">
        <div class="flex items-center space-x-3">
          <div class="bg-slate-800 text-white px-3 py-1 rounded-full text-sm font-medium">
            {{ post.category }}
          </div>
          <span class="text-sm text-slate-500">{{ post.time }}</span>
        </div>
        <button @click="closeDetail" class="text-slate-400 hover:text-slate-600 transition-colors">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>

      <div class="overflow-y-auto max-h-[calc(90vh-80px)]">
        <div class="flex">
          <div class="flex-1 p-6 border-r border-slate-200">
            <h1 class="text-2xl font-bold text-slate-800 mb-4 leading-tight">
              {{ post.title }}
            </h1>

            <div class="container mx-auto px-4 py-8">
              <div v-if="post.youtubeTumbnail" class="relative w-full aspect-video rounded-lg shadow-md overflow-hidden">
                <iframe :src="youtubeEmbedUrl" frameborder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowfullscreen class="absolute top-0 left-0 w-full h-full"></iframe>
              </div>
              <div v-else-if="post.shortsThumbnail"
                class="relative w-full aspect-video rounded-lg shadow-md overflow-hidden">
                <iframe :src="shortsEmbedUrl" frameborder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowfullscreen class="absolute top-0 left-0 w-full h-full"></iframe>
              </div>
              <div v-else-if="post.img || post.youtubeTumbnail" class="mb-6">
                <img v-if="post.img" :src="post.img" :alt="post.title" class="w-full rounded-lg shadow-md" />
              </div>
            </div>
            <div class="prose max-w-none mb-6">
              <p class="text-slate-700 leading-relaxed whitespace-pre-wrap">
                {{ post.body }}
              </p>
            </div>

            <div class="flex items-center justify-between mb-8 p-4 bg-slate-50 rounded-lg">
              <div class="flex items-center space-x-4">
                <button
          @click="handleToggleLike"
          :disabled="isLiking"
          :class="[
            'relative flex items-center justify-center space-x-2 px-4 py-2 rounded-lg transition-colors overflow-hidden group',
            'border border-slate-200',
            // 버튼 자체의 배경색은 좋아요 상태에 따라 변경됩니다.
            // 물결 애니메이션이 위에 오버레이되므로, 이 배경색은 물결이 채워지지 않은 부분에 보입니다.
            // 좋아요를 눌렀을 때는 연한 파란색 계열, 누르지 않았을 때는 흰색 유지
            post?.isLiked ? 'bg-blue-100' : 'bg-white', // <-- 색상 변경: bg-red-500 -> bg-blue-100
          ]"
          ref="likeButtonRef"
        >
          <div
            class="wave-container absolute inset-0 z-0 m-0"
            :style="{
              // 물결 아래 배경색. 좋아요 상태에 따라 미묘하게 변경될 수 있습니다.
              // 좋아요를 눌렀을 때는 더 투명한 파란색 오버레이
              backgroundColor: post?.isLiked ? 'rgba(0, 123, 255, 0.2)' : 'transparent', // <-- 색상 변경: rgba(255, 255, 255, 0.4) -> rgba(0, 123, 255, 0.2)
              transition: 'background-color 0.3s ease-in-out',
            }"
          >
            <div
              class="wave"
              :style="{
                top: waveTop,
                transition: `top ${animationDuration / 1000}s ease-in-out`,
              }"
            ></div>
            <div
              class="wave layer_1"
              :style="{
                top: waveTop,
                transition: `top ${animationDuration / 1000}s ease-in-out`,
              }"
            ></div>
          </div>

          <svg
            :class="[
              'w-5 h-5 relative z-10 transition-colors duration-300',
              // 아이콘 색상도 post?.isLiked에 따라 변경
              // 좋아요를 눌렀을 때는 진한 파란색, 누르지 않았을 때는 슬레이트 그레이
              post?.isLiked ? 'text-blue-600' : 'text-slate-600', // <-- 색상 변경: text-white -> text-blue-600
            ]"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5"
            />
          </svg>

          <span
            :class="[
              'text-sm font-medium relative z-10 transition-colors duration-300',
              // 텍스트 색상도 post?.isLiked에 따라 변경
              // 좋아요를 눌렀을 때는 진한 파란색, 누르지 않았을 때는 슬레이트 그레이
              post?.isLiked ? 'text-blue-700' : 'text-slate-700', // <-- 색상 변경: text-white -> text-blue-700
            ]"
          >
            추천 {{ post?.likeCount || 0 }}
          </span>

        </button>

                <div class="flex items-center space-x-2 text-slate-500">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                  <span class="text-sm">{{ post.viewcount }}</span>
                </div>
              </div>

              <button v-if="!currentVote" @click="startVote"
                class="px-6 py-2 bg-slate-800 text-white rounded-lg hover:bg-slate-700 transition-colors font-medium">
                투표 시작하기
              </button>
            </div>

            <div v-if="showVoteCreation" class="mb-8 p-6 bg-blue-50 border border-blue-200 rounded-lg">
              <h3 class="text-lg font-semibold text-slate-800 mb-4">
                투표 생성
              </h3>

              <div class="mb-4">
                <label class="block text-sm font-medium text-slate-700 mb-2">투표 제목</label>
                <input :value="post.title" readonly
                  class="w-full px-3 py-2 bg-slate-100 border border-slate-300 rounded-md text-slate-600" />
              </div>

              <div class="mb-4">
                <label class="block text-sm font-medium text-slate-700 mb-2">선택지</label>
                <div class="space-y-3">
                  <div class="flex items-center space-x-3">
                    <span class="text-sm font-medium text-slate-600 w-12">선택 1</span>
                    <input v-model="voteOptions.agreeTitle" type="text" placeholder="첫 번째 선택지를 입력하세요"
                      class="flex-1 px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
                  </div>
                  <div class="flex items-center space-x-3">
                    <span class="text-sm font-medium text-slate-600 w-12">선택 2</span>
                    <input v-model="voteOptions.disagreeTitle" type="text" placeholder="두 번째 선택지를 입력하세요"
                      class="flex-1 px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
                  </div>
                </div>
              </div>

              <div class="flex justify-end space-x-3">
                <button @click="showVoteCreation = false"
                  class="px-4 py-2 text-slate-600 border border-slate-300 rounded-md hover:bg-slate-50 transition-colors">
                  취소
                </button>
                <button @click="handleCreateVote"
                  class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
                  투표 생성
                </button>
              </div>
              <div v-if="showCreateVoteSuccessMessage" class="mt-4 p-3 bg-green-100 text-green-700 rounded-md text-sm">
                {{ showCreateVoteSuccessMessage }}
              </div>
              <div v-if="showCreateVoteErrorMessage" class="mt-4 p-3 bg-red-100 text-red-700 rounded-md text-sm">
                {{ showCreateVoteErrorMessage }}
              </div>
            </div>

            <div v-if="currentVote" class="mb-8 p-6 bg-slate-50 border border-slate-200 rounded-lg">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-slate-800">투표</h3>
                <div class="text-sm text-slate-500">
                  총 {{ currentVote.totalCount }}명 참여
                </div>
              </div>

              <div v-if="!currentVote.isVoted" class="space-y-3">
                <button @click="castVote('agree', props.postId)"
                  class="w-full p-3 text-left border border-slate-300 rounded-lg hover:bg-white hover:border-blue-300 transition-colors">
                  {{ currentVote.agreeTitle }}
                </button>
                <button @click="castVote('disagree', props.postId)"
                  class="w-full p-3 text-left border border-slate-300 rounded-lg hover:bg-white hover:border-blue-300 transition-colors">
                  {{ currentVote.disagreeTitle }}
                </button>
                <div v-if="showCreateVoteSuccessMessage" class="mt-4 p-3 bg-green-100 text-green-700 rounded-md text-sm">
                  {{ showCreateVoteSuccessMessage }}
                </div>
                <div v-if="showCreateVoteErrorMessage" class="mt-4 p-3 bg-red-100 text-red-700 rounded-md text-sm">
                  {{ showCreateVoteErrorMessage }}
                </div>
              </div>

              <div v-else class="space-y-4">
                <div class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-slate-700">{{
                      currentVote.agreeTitle
                    }}</span>
                    <span class="text-sm font-semibold text-slate-800">{{ agreePercentage.toFixed(1) }}%</span>
                  </div>
                  <div class="h-3 bg-slate-200 rounded-full overflow-hidden">
                    <div class="h-full bg-blue-500 rounded-full transition-all duration-500"
                      :style="{ width: `${agreePercentage}%` }"></div>
                  </div>
                  <div class="text-xs text-slate-500">
                    {{ currentVote.agreeCount }}명
                  </div>
                </div>

                <div class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-slate-700">{{
                      currentVote.disagreeTitle
                    }}</span>
                    <span class="text-sm font-semibold text-slate-800">{{ disagreePercentage.toFixed(1) }}%</span>
                  </div>
                  <div class="h-3 bg-slate-200 rounded-full overflow-hidden">
                    <div class="h-full bg-red-500 rounded-full transition-all duration-500"
                      :style="{ width: `${disagreePercentage}%` }"></div>
                  </div>
                  <div class="text-xs text-slate-500">
                    {{ currentVote.disagreeCount }}명
                  </div>
                </div>

                <div class="pt-3 border-t border-slate-200 text-xs text-slate-500">
                  투표 시작일 : {{ formatDateTime(currentVote.createAt) }}
                </div>
              </div>
            </div>
            <div v-if="showNoPollMessage"
              class="text-center py-10 mb-8 p-6 bg-slate-50 border border-slate-200 rounded-lg">
              <svg class="mx-auto h-12 w-12 text-slate-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
              <h3 class="mt-2 text-sm font-medium text-slate-900">
                투표가 없습니다
              </h3>
              <p class="mt-1 text-sm text-slate-500">
                새로운 투표를 시작해보세요.
              </p>
            </div>
          </div>

          <div class="w-96 p-6 bg-slate-50">
            <h3 class="text-lg font-semibold text-slate-800 mb-4">
              댓글
              <span class="text-slate-500 font-normal">({{ comments?.length || 0 }})</span>
            </h3>

            <div class="mb-6">
              <div class="space-y-3">
                <textarea v-model="newCommentText" placeholder="댓글을 입력하세요..." rows="3"
                  class="w-full px-3 py-2 border border-slate-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500 resize-none text-sm"></textarea>
                <div class="flex justify-end">
                  <button @click="handleCommentSubmit" :disabled="isSubmitting"
                    class="px-4 py-2 bg-slate-800 text-white rounded-md hover:bg-slate-700 transition-colors text-sm">
                    {{ isSubmitting ? "작성 중..." : "댓글 작성" }}
                  </button>
                </div>
                <p v-if="submitError" class="text-red-500 text-sm mt-2">
                  {{ submitError }}
                </p>
                <p v-if="submitSuccess" class="text-green-500 text-sm mt-2">
                  댓글이 성공적으로 작성되었습니다!
                </p>
              </div>
            </div>

            <div v-if="commentsLoading" class="text-center text-slate-500">
              댓글을 불러오는 중...
            </div>
            <div v-else-if="commentsError" class="text-center text-red-500">
              {{ commentsError }}
            </div>
            <div v-else-if="!comments || comments.length === 0" class="text-center text-slate-500">
              아직 댓글이 없습니다.
            </div>
            <div v-else class="space-y-4 max-h-[60vh] overflow-y-auto">
              <div v-for="comment in comments" :key="comment.commentId" class="pb-4 last:border-b-0"
                :class="{ 'pl-8': comment.level > 0 }">
                <div class="space-y-2">
                  <div class="flex items-start space-x-2">
                    <div class="flex-shrink-0">
                      <div class="w-6 h-6 bg-slate-300 rounded-full flex items-center justify-center">
                        <svg class="w-3 h-3 text-slate-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
                      </div>
                    </div>
                    <div class="flex-1 min-w-0">
                      <div class="flex items-center space-x-2 mb-1">
                        <span class="font-medium text-slate-800 text-sm">{{
                          comment.authorId
                        }}</span>
                        <span class="text-xs text-slate-500">{{
                          formatDateTime(comment.createdAt)
                        }}</span>
                      </div>
                      <p class="text-slate-700 text-sm leading-relaxed">
                        {{ comment.content }}
                      </p>
                      <div class="flex items-center space-x-3 mt-2">
                        <button class="flex items-center space-x-1 text-xs text-slate-500 hover:text-slate-700">
                          <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5" />
                          </svg>
                          <span>{{ comment.likes }}</span>
                        </button>
                        <button class="text-xs text-slate-500 hover:text-slate-700">
                          답글
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  ref,
  watch,
  defineProps,
  defineEmits,
  computed,
  onMounted,
  onUpdated,
} from "vue";
import { useComments } from "@/util/PostDetail/useComments";
import { useAddComment } from "@/util/PostDetail/useAddComment";
import { useVote } from "@/util/PostDetail/useVote"; // useVote 훅 임포트
import { useLike } from "@/util/PostDetail/useLike";
import { useLoginStore } from "@/stores/loginStore"; // useLoginStore 임포트

const props = defineProps({
  postId: {
    type: Number,
    default: null,
  },
  post: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["close"]);

const post = ref(props.post);
const showDetail = ref(false);

const newCommentText = ref("");

// useLoginStore를 PostDetail에서만 호출하고, computed로 currentUserId를 정의합니다.
const loginStore = useLoginStore();
const currentUserId = computed(() => loginStore.getUserId);

const {
  comments,
  isLoading: commentsLoading,
  error: commentsError,
  fetchCommentsForPost,
} = useComments(computed(() => props.postId || props.post?.id));

const formatDateTime = (isoString) => {
  if (!isoString) return "";
  try {
    const date = new Date(isoString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  } catch (e) {
    console.error("날짜 포맷팅 오류:", e);
    return isoString;
  }
};

// useVote 훅 사용 시 currentUserId를 두 번째 인자로 전달합니다.
const {
  showVoteCreation,
  voteOptions,
  currentVote,
  totalVotes,
  agreePercentage,
  disagreePercentage,
  startVote,
  createVote: useVoteCreateVote,
  castVote: useVoteCastVote,
  resetVote,
  loadPoll,
  showNoPollMessage,
  showVoteErrorMessage,
  showCreateVoteSuccessMessage,
  showCreateVoteErrorMessage,
} = useVote(
  computed(() => props.postId || props.post?.id),
  currentUserId
);

const handleStartVoteClick = () => {
  startVote();
};

const handleCreateVote = () => {
  useVoteCreateVote(props.post.title);
};

const castVote = (optionType, postId) => {
  useVoteCastVote(optionType, postId);
};

const { isSubmitting, submitError, submitSuccess, addComment } =
  useAddComment();

const { toggleLike, isLiking } = useLike();

const likeButtonRef = ref(null);
const showEmojiAnimation = ref(false);
const animationDuration = ref(700);

// 이 부분이 가장 중요합니다. waveTop이 post.value.isLiked에 반응하도록
const waveTop = computed(() => {
  if (post.value?.isLiked) { // <= 이 부분이 post.value?.isLiked를 참조해야 합니다.
    return '0%'; // 물결이 맨 위에 있어 버튼이 채워진 상태
  } else {
    return '100%'; // 물결이 맨 아래에 있어 버튼이 비어있는 상태
  }
});

const handleToggleLike = async () => {
  const postId = props.postId || props.post?.id;

  if (!postId) {
    console.error("게시물 ID를 찾을 수 없습니다.");
    return;
  }

  const wasLiked = post.value?.isLiked;

  if (wasLiked === undefined || post.value === null) {
    console.error("게시물의 좋아요 상태를 확인할 수 없습니다.");
    return;
  }
  console.log("PostDetail.vue: 좋아요/싫어요 토글 시도", {
    postId,
    currentUserId: currentUserId.value,
    wasLiked,
  });

  const success = await toggleLike(postId, currentUserId.value, post);

  console.log("PostDetail.vue: toggleLike 호출 결과 success:", success);
  console.log("PostDetail.vue: handleToggleLike - post.value (after toggleLike call):", post.value);
  console.log("PostDetail.vue: handleToggleLike - post.value.isLiked (after toggleLike call):", post.value?.isLiked); // !!! 추가 !!!

  if (success) {
    if (post.value.isLiked && !wasLiked) {
    } else if (!post.value.isLiked && wasLiked) {
    }
  } else {
    console.log("PostDetail.vue: 좋아요/싫어요 토글 실패");
  }
};


const handleCommentSubmit = async () => {
  const postId = props.postId || post.value?.id;
  const authorId = currentUserId.value;
  const content = newCommentText.value;

  if (!postId) {
    submitError.value = "게시물 ID를 찾을 수 없습니다.";
    return;
  }
  if (!authorId) {
    submitError.value = "로그인된 사용자만 댓글을 작성할 수 있습니다.";
    isSubmitting.value = false;
    return;
  }
  if (!content.trim()) {
    submitError.value = "댓글 내용을 입력해주세요.";
    isSubmitting.value = false;
    return;
  }

  await addComment(postId, content, authorId);
  if (submitSuccess.value) {
    newCommentText.value = "";
    submitError.value = null;
    await fetchCommentsForPost(postId);
  }
};

const youtubeEmbedUrl = computed(() => {
  if (post.value && post.value.youtubeId) {
    console.log("유튜브 : " + post.value.youtubeId);
    return `https://www.youtube-nocookie.com/embed/${post.value.youtubeId}`;
  }
  return "";
});

const shortsEmbedUrl = computed(() => {
  if (post.value && post.value.shortsId) {
    console.log("쇼츠 : " + post.value.shortsId);
    return `https://www.youtube-nocookie.com/embed/${post.value.shortsId}`;
  }
  return "";
});

const closeDetail = () => {
  showDetail.value = false;
  setTimeout(() => {
    emit("close");
  }, 300);
};

watch(currentVote, (newValue) => {
  if (newValue && typeof newValue.isLiked === 'boolean') {
    if (post.value) {
      post.value.isLiked = newValue.isLiked;
      console.log("PostDetail.vue: currentVote에서 post.value.isLiked 동기화됨:", post.value.isLiked);
    }
  }
}, { immediate: true }); // 컴포넌트 마운트 시 즉시 실행하여 초기 상태 설정


watch(
  () => props.post,
  (newPost) => {
    post.value = newPost;
    if (newPost) {
      resetVote();
      setTimeout(() => {
        showDetail.value = true;
      }, 50);
    } else {
      resetVote();
      showDetail.value = false;
    }
  },
  { immediate: true }
);
</script>

<style scoped>
/* 스크롤바 스타일링 */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f5f9;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}

/* 부드러운 전환 효과 */
.transition-all {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 300ms;
}

.slide-up-enter-from,
.slide-up-leave-to {
  transform: translateY(20px);
  /* 아래에서 위로 올라오도록 시작/끝 위치 설정 */
  opacity: 0;
}
</style>
