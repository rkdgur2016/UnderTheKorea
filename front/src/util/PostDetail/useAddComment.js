// useAddComment.js (수정된 내용)
import { ref } from 'vue'; // ref 임포트 확인 
import axios from 'axios'; // axios 임포트 확인 

export function useAddComment() {
  // ✅ 새로운 ref 두 개 추가 및 초기화
  const newCommentText = ref(''); // 댓글 내용 입력 필드와 바인딩될 ref
  const commentAuthor = ref('익명'); // 작성자 입력 필드와 바인딩될 ref 또는 기본값

  const isSubmitting = ref(false); // 제출 중 상태 
  const submitError = ref(null);   // 제출 오류 상태 
  const submitSuccess = ref(false); // 제출 성공 상태 

  /**
   * 새 댓글을 서버에 전송하는 함수 
   * @param {number} postId - 댓글을 작성할 게시물의 ID 
   * @param {string} content - 댓글 내용 
   * @param {string} authorId - 댓글 작성자 (예: 사용자 ID 또는 이름) 
   */
  const addComment = async (postId, content, authorId) => {
    isSubmitting.value = true;
    submitError.value = null; 
    submitSuccess.value = false; 

    if (!postId || !content || !authorId) { // 유효성 검사 수정 
      submitError.value = '게시물 ID, 댓글 내용, 작성자는 필수입니다.'; 
      isSubmitting.value = false; 
      return;
    }
    // ----------------------------

    try {
      const response = await axios.post(`/comment/addComment`, {
        postId: postId,
        content: content, // 함수 파라미터 'content' 사용 
        authorId: authorId, // 함수 파라미터 'authorId' 사용 
        // 필요하다면 다른 필드 (예: createdAt, parentCommentId 등)를 추가
      });

      if (response.status === 200 || response.status === 201) { // 201 Created도 성공으로 간주 
        submitSuccess.value = true; 
        // ✅ 성공 시 입력 필드 초기화 (옵션)
        newCommentText.value = ''; // 댓글 내용 초기화
        // commentAuthor.value = '익명'; // 작성자 초기화 (필요하다면)

        return response.data; 
      } else {
        submitError.value = '댓글 작성에 실패했습니다.'; 
      }
    } catch (err) {
      console.error('댓글 작성 중 오류 발생:', err); 
      submitError.value = err.response?.data?.message || '서버 오류로 댓글 작성에 실패했습니다.'; 
    } finally {
      isSubmitting.value = false; 
    }
  };

  // ✅ 반환 값에 newCommentText와 commentAuthor 추가
  return {
    newCommentText, // 추가
    commentAuthor,  // 추가
    isSubmitting, 
    submitError, 
    submitSuccess, 
    addComment, 
  };
}