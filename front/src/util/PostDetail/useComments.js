import { ref, watch } from 'vue';
import axios from 'axios'; // axios 임포트

export function useComments(postId) {
  const comments = ref([]);
  const isLoading = ref(false);
  const error = ref(null);

  const fetchCommentsForPost = async (id) => {
    isLoading.value = true;
    error.value = null;
    comments.value = []; // 데이터 페치 전 초기화

    if (!id) {
      comments.value = [];
      isLoading.value = false;
      return;
    }

    try {
      const response = await axios.get(`/comment/loadComment/${id}`);
      comments.value = response.data; // <--- 이 부분에서 실제 할당됨
    } catch (err) {
      console.error('댓글을 불러오는 중 오류 발생:', err);
      error.value = '댓글을 불러오는 데 실패했습니다.';
    } finally {
      isLoading.value = false;
    }
  };
  

  watch(postId, (newId) => {
    fetchCommentsForPost(newId);
  }, { immediate: true });

  
  
  return {
    comments,
    isLoading,
    error,
    fetchCommentsForPost
  };

}