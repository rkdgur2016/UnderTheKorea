<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-50 to-gray-100">
    <!-- 헤더 -->
    <div class="bg-white border-b border-slate-200">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-2xl font-bold text-slate-800">새 게시물 작성</h1>
            <p class="text-sm text-slate-500 mt-1">
              대한민국의 미래를 함께 논의해보세요
            </p>
          </div>
          <router-link
            to="/"
            class="text-slate-500 hover:text-slate-700 transition-colors"
          >
            <svg
              class="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
          </router-link>
        </div>
      </div>
    </div>

    <!-- 메인 폼 -->
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <form @submit.prevent="submitPost" class="space-y-8">
        <!-- 기본 정보 섹션 -->
        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
          <h2
            class="text-lg font-semibold text-slate-800 mb-6 flex items-center"
          >
            <svg
              class="w-5 h-5 mr-2 text-slate-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
              />
            </svg>
            기본 정보
          </h2>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- 카테고리 선택 -->
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-2">
                카테고리 <span class="text-red-500">*</span>
              </label>
              <select
                v-model="formData.category"
                required
                class="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-slate-500 focus:border-slate-500 transition-colors"
              >
                <option value="">카테고리를 선택하세요</option>
                <option value="철학">철학</option>
                <option value="정치">정치</option>
                <option value="경제">경제</option>
                <option value="사회">사회</option>
              </select>
            </div>

            <!-- 작성자 정보 (읽기 전용) -->
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-2"
                >작성자</label
              >
              <input
                :value="loginStore.getUserId"
                readonly
                class="w-full px-4 py-3 bg-slate-100 border border-slate-300 rounded-lg text-slate-600"
              />
              <p class="text-xs text-slate-500 mt-1">
                사용자가 수동으로 변경할 수 없습니다.
              </p>
            </div>
          </div>

          <!-- 제목 -->
          <div class="mt-6">
            <label class="block text-sm font-medium text-slate-700 mb-2">
              제목 <span class="text-red-500">*</span>
            </label>
            <input
              v-model="formData.title"
              type="text"
              required
              maxlength="100"
              placeholder="게시물 제목을 입력하세요"
              class="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-slate-500 focus:border-slate-500 transition-colors"
            />
            <div class="flex justify-between mt-1">
              <p class="text-xs text-slate-500">
                과한 어그로성 제목은 삼가해주세요
              </p>
              <span class="text-xs text-slate-500"
                >{{ formData.title.length }}/100</span
              >
            </div>
          </div>
        </div>

        <!-- 내용 섹션 -->
        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
          <h2
            class="text-lg font-semibold text-slate-800 mb-6 flex items-center"
          >
            <svg
              class="w-5 h-5 mr-2 text-slate-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h16M4 18h7"
              />
            </svg>
            내용 작성
          </h2>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2">
              본문 <span class="text-red-500">*</span>
            </label>
            <QuillEditor
              ref="quill"
              theme="snow"
              v-model:content="formData.content"
              contentType="delta"
              class="h-72"
            />
            <div class="flex justify-between mt-1">
              <p class="text-xs text-slate-500">
                자유롭게 게시물을 작성해주세요.
              </p>
              <pre> {{ content }} </pre>
            </div>
          </div>
        </div>

        <!-- 미디어 섹션 -->
        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
          <h2
            class="text-lg font-semibold text-slate-800 mb-6 flex items-center"
          >
            <svg
              class="w-5 h-5 mr-2 text-slate-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.587a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"
              />
            </svg>
            미디어 첨부 (선택사항)
          </h2>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- 이미지 업로드 -->
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-2"
                >이미지</label
              >

              <!-- 파일 업로드 -->
              <div class="mb-4">
                <div
                  class="border-2 border-dashed border-slate-300 rounded-lg p-6 text-center hover:border-slate-400 transition-colors"
                >
                  <input
                    ref="fileInput"
                    type="file"
                    accept="image/*"
                    @change="handleFileUpload"
                    class="hidden"
                    :disabled="isAnyMediaFieldPopulated && !imagePreview"
                  />
                  <div
                    v-if="!imagePreview"
                    @click="
                      (!isAnyMediaFieldPopulated || !imagePreview) &&
                        $refs.fileInput.click()
                    "
                    :class="{
                      'cursor-pointer':
                        !isAnyMediaFieldPopulated || !imagePreview,
                      'cursor-not-allowed opacity-50':
                        isAnyMediaFieldPopulated && !imagePreview,
                    }"
                  >
                    <svg
                      class="mx-auto h-12 w-12 text-slate-400"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
                      />
                    </svg>
                    <p class="mt-2 text-sm text-slate-600">
                      클릭하여 이미지 업로드
                    </p>
                    <p class="text-xs text-slate-500">
                      PNG, JPG, GIF (최대 5MB)
                    </p>
                  </div>
                  <div v-else class="relative">
                    <img
                      :src="imagePreview"
                      alt="미리보기"
                      class="max-h-32 mx-auto rounded"
                    />
                    <button
                      @click="removeImage"
                      type="button"
                      class="absolute top-0 right-0 bg-red-500 text-white rounded-full p-1 transform translate-x-2 -translate-y-2"
                    >
                      <svg
                        class="w-4 h-4"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M6 18L18 6M6 6l12 12"
                        ></path>
                      </svg>
                    </button>
                  </div>
                </div>
              </div>

              <!-- 또는 URL 입력 -->
              <div>
                <label class="block text-xs text-slate-500 mb-1"
                  >또는 이미지 URL 입력</label
                >
                <input
                  v-model="formData.imageUrl"
                  type="url"
                  placeholder="https://example.com/image.jpg"
                  class="w-full px-3 py-2 text-sm border border-slate-300 rounded-md focus:ring-2 focus:ring-slate-500 focus:border-slate-500 transition-colors"
                  :disabled="
                    isAnyMediaFieldPopulated && formData.imageUrl.trim() === ''
                  "
                />
              </div>
            </div>

            <!-- 유튜브 링크 -->
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-2"
                >유튜브 동영상</label
              >
              <input
                v-model="formData.videoUrl"
                type="url"
                placeholder="https://www.youtube.com/watch?v=..."
                class="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-slate-500 focus:border-slate-500 transition-colors"
                :disabled="
                  isAnyMediaFieldPopulated && formData.videoUrl.trim() === ''
                "
              />
              <p class="text-xs text-slate-500 mt-1">
                유튜브 동영상 URL을 입력하세요
              </p>

              <!-- 유튜브 미리보기 -->
              <div v-if="youtubePreview" class="mt-4">
                <div
                  class="aspect-video bg-slate-100 rounded-lg overflow-hidden"
                >
                  <iframe
                    :src="youtubePreview"
                    class="w-full h-full"
                    allowfullscreen
                  ></iframe>
                </div>
              </div>
              <div class="mt-6">
                <label class="block text-sm font-medium text-slate-700 mb-2"
                  >유튜브 쇼츠</label
                >
                <input
                  v-model="formData.shortsUrl"
                  type="url"
                  placeholder="https://www.youtube.com/shorts/..."
                  class="w-full px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-slate-500 focus:border-slate-500 transition-colors"
                  :disabled="
                    isAnyMediaFieldPopulated && formData.shortsUrl.trim() === ''
                  "
                />
                <p class="text-xs text-slate-500 mt-1">
                  유튜브 쇼츠 URL을 입력하세요
                </p>

                <div v-if="youtubeShortsPreview" class="mt-4">
                  <div
                    class="aspect-video bg-slate-100 rounded-lg overflow-hidden"
                  >
                    <iframe
                      :src="youtubeShortsPreview"
                      class="w-full h-full"
                      frameborder="0"
                      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                      allowfullscreen
                    ></iframe>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 제출 버튼 -->
        <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
          <div class="flex items-center justify-between">
            <div class="text-sm text-slate-600">
              <p>• 작성된 게시물은 검토 후 게시됩니다</p>
              <p>• 부적절한 내용은 삭제될 수 있습니다</p>
            </div>

            <div class="flex space-x-4">
              <button
                type="submit"
                :disabled="!isFormValid || isSubmitting"
                :class="[
                  'px-8 py-3 rounded-lg font-medium transition-all duration-200',
                  isFormValid && !isSubmitting
                    ? 'bg-slate-800 hover:bg-slate-700 text-white shadow-md hover:shadow-lg'
                    : 'bg-slate-300 text-slate-500 cursor-not-allowed',
                ]"
              >
                <span v-if="isSubmitting" class="flex items-center">
                  <svg
                    class="animate-spin -ml-1 mr-2 h-4 w-4"
                    fill="none"
                    viewBox="0 0 24 24"
                  >
                    <circle
                      class="opacity-25"
                      cx="12"
                      cy="12"
                      r="10"
                      stroke="currentColor"
                      stroke-width="4"
                    ></circle>
                    <path
                      class="opacity-75"
                      fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                    ></path>
                  </svg>
                  게시 중...
                </span>
                <span v-else>게시하기</span>
              </button>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useLoginStore } from "@/stores/loginStore.js";
import { useModalStore } from "@/stores/modalStore";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import axios from "axios";

const router = useRouter();
const loginStore = useLoginStore();
const modalStore = useModalStore();

const quill = ref(null);

const content = ref({
  ops: [{ insert: "\n" }],
});

// 폼 데이터
const formData = ref({
  title: "",
  category: "",
  imageUrl: "",
  videoUrl: "",
  shortsUrl: "",
});

const userInfo = computed(() => loginStore.getLoggedInUser);

onMounted(() => {
  loginStore.initializeAuth();
  if (!userInfo.value) {
    alert(
      "게시물을 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동합니다."
    );
    modalStore.openLogin();
  }
});

// 상태 관리
const isSubmitting = ref(false);
const imagePreview = ref("");
const fileInput = ref(null);

// 폼 유효성 검사
const isFormValid = computed(() => {
  return (
    formData.value.title.trim().length > 0 &&
    formData.value.category.trim().length > 0
  );
});

// 유튜브 미리보기 URL 생성
const youtubePreview = computed(() => {
  if (!formData.value.videoUrl) return "";

  const youtubeRegex = /(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&\n?#]+)/;
  const match = formData.value.videoUrl.match(youtubeRegex);

  if (match) {
    return `https://www.youtube.com/embed/${match[1]}`;
  }
  return "";
});

// 유튜브 쇼츠 미리보기 URL 생성
const youtubeShortsPreview = computed(() => {
  if (!formData.value.shortsUrl) return "";

  // YouTube Shorts URL 패턴: youtube.com/shorts/{videoId} 또는 youtu.be/{videoId}
  const shortsRegex = /(?:youtube\.com\/shorts\/|youtu\.be\/)([a-zA-Z0-9_-]+)/;
  const match = formData.value.shortsUrl.match(shortsRegex);

  if (match) {
    // YouTube Shorts 임베드 URL은 일반 동영상과 동일하게 embed 사용 가능
    return `https://www.youtube.com/embed/${match[1]}`;
  }
  return "";
});
// 파일 업로드 처리
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  // 파일 크기 체크 (5MB)
  if (file.size > 5 * 1024 * 1024) {
    alert("파일 크기는 5MB 이하여야 합니다.");
    // 파일 입력 초기화
    if (fileInput.value) {
      fileInput.value.value = "";
    }
    imagePreview.value = "";
    return;
  }

  // 이미지 미리보기
  const reader = new FileReader();
  reader.onload = (e) => {
    imagePreview.value = e.target.result;
  };
  reader.readAsDataURL(file);

  // 파일이 선택되면 이미지 URL 필드를 비활성화
  formData.value.imageUrl = ""; // 이미지 파일 선택 시 URL 필드 초기화
};

// 이미지 제거
const removeImage = () => {
  imagePreview.value = "";
  formData.value.imageUrl = "";
  if (fileInput.value) {
    fileInput.value.value = "";
  }
};

// 미디어 입력 필드 중 하나라도 값이 있는지 확인하는 computed 속성
const isAnyMediaFieldPopulated = computed(() => {
  return (
    imagePreview.value !== "" || // 이미지 파일이 선택된 경우
    formData.value.imageUrl.trim() !== "" ||
    formData.value.videoUrl.trim() !== "" ||
    formData.value.shortsUrl.trim() !== ""
  );
});

// 게시물 제출
const submitPost = async () => {
  if (!isFormValid.value) return;

  isSubmitting.value = true;

  try {
    const postData = {
      authorId: loginStore.getUserId,
      title: formData.value.title.trim(),
      category: formData.value.category,
      content: JSON.stringify(content.value),
      imageUrl: formData.value.imageUrl || null,
      videoUrl: formData.value.videoUrl || null,
      shortUrl: formData.value.shortsUrl || null,
      //createdAt: new Date().toISOString(),
    };

    const response = await axios.post("/post/savePosts", postData);

    console.log("게시물 데이터:", postData);
    console.log("서버 응답:", response.data);

    // 성공 시 임시저장 데이터 삭제
    localStorage.removeItem("postDraft");

    alert("게시물이 성공적으로 등록되었습니다!");
    router.push("/"); // 메인 페이지로 이동
  } catch (error) {
    console.error("게시물 등록 실패:", error);
    alert("게시물 등록에 실패했습니다. 다시 시도해주세요.");
  } finally {
    isSubmitting.value = false;
  }
};

// 미디어 필드 값 변경 감지 및 다른 필드 초기화
watch(
  () => formData.value.imageUrl,
  (newValue) => {
    if (newValue && newValue.trim() !== "") {
      formData.value.videoUrl = "";
      formData.value.shortsUrl = "";
      imagePreview.value = ""; // URL 입력 시 파일 미리보기 제거
      if (fileInput.value) fileInput.value.value = ""; // 파일 입력 초기화
    }
  }
);

watch(
  () => formData.value.videoUrl,
  (newValue) => {
    if (newValue && newValue.trim() !== "") {
      formData.value.imageUrl = "";
      formData.value.shortsUrl = "";
      imagePreview.value = "";
      if (fileInput.value) fileInput.value.value = "";
    }
  }
);

watch(
  () => formData.value.shortsUrl,
  (newValue) => {
    if (newValue && newValue.trim() !== "") {
      formData.value.imageUrl = "";
      formData.value.videoUrl = "";
      imagePreview.value = "";
      if (fileInput.value) fileInput.value.value = "";
    }
  }
);

// `imagePreview` (파일 업로드) 변경 감지
watch(imagePreview, (newValue) => {
  if (newValue !== "") {
    // 이미지가 업로드되면
    formData.value.imageUrl = ""; // URL 입력 필드 초기화
    formData.value.videoUrl = "";
    formData.value.shortsUrl = "";
  }
});

watch(
  () => formData.value.content,
  (newValue) => {
    console.log("Quill Editor content (Delta):", newValue);
    content.value = newValue;
    console.log("content : ", content);
  },
  { deep: true } // 객체 내부 변경 감지를 위해 deep 옵션 필수
);
</script>

<style scoped>
/* 커스텀 스크롤바 */
textarea::-webkit-scrollbar {
  width: 6px;
}

textarea::-webkit-scrollbar-track {
  background: #f1f5f9;
}

textarea::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 3px;
}

textarea::-webkit-scrollbar-thumb:hover {
  background: #94a3b8;
}

/* 애니메이션 */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.animate-spin {
  animation: spin 1s linear infinite;
}

/* 포커스 스타일 개선 */
input:focus,
textarea:focus,
select:focus {
  outline: none;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}
</style>
