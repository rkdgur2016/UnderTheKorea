import { ref } from 'vue';
import axios from 'axios';

export function useLike() {
  const isLiking = ref(false); // 좋아요 요청 중인지 나타내는 상태

  const toggleLike = async (postId, userId, postRef) => {
    if (isLiking.value){
        console.warn("useLike.js: 이미 좋아요 요청 진행 중. 중복 요청 방지.");
        return false; // 이미 요청 중이면 중복 요청 방지
    }
    if (!postRef.value){
        console.error("useLike.js: postRef.value 객체가 null입니다. 좋아요 요청 중단.");
        return false;
    }
    
    if (!userId) {
      alert("로그인 후 추천할 수 있습니다.");
      return false;
    }

    isLiking.value = true; // 좋아요 요청 시작

    try {
      let response;
      if (postRef.value.isLiked) {
        // 이미 좋아요를 눌렀다면 좋아요 취소 (Dislike API 호출)
        response = await axios.post('/like/dislike', {
          postId: postId,
          userId: userId
        });
        console.log('추천 취소 성공:', response.data);
        postRef.value.likeCount--; // 좋아요 카운트 즉시 감소
        postRef.value.isLiked = false; // 상태 업데이트
      } else {
        // 좋아요를 누르지 않았다면 좋아요 (Like API 호출)
        response = await axios.post('/like/like', {
          postId: postId,
          userId: userId
        });
        console.log('추천 성공:', response.data);
        postRef.value.likeCount++; // 좋아요 카운트 즉시 증가
        postRef.value.isLiked = true; // 상태 업데이트
      }
      return true; // 성공적으로 처리됨을 반환
    } catch (error) {
      console.error('추천/추천 취소 실패:', error);
      alert('추천 상태 변경에 실패했습니다. 다시 시도해주세요.');
      return false; // 실패했음을 반환
    } finally {
      isLiking.value = false; // 좋아요 요청 종료
    }
  };

  return {
    toggleLike,
    isLiking
  };
}