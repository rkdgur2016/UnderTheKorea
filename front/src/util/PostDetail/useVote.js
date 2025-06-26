// useVote.js 파일 (수정 반영됨)
import { ref, watch, computed } from 'vue';
import axios from 'axios';

export function useVote(postIdProp, currentUserIdProp) {
  const showVoteCreation = ref(false);
  const voteOptions = ref({
    agreeTitle: '',
    disagreeTitle: ''
  });
  const currentVote = ref(null); // 투표 데이터가 로드 시 할당
  const currentUserId = currentUserIdProp;

  // 찬성 비율 계산 (bar 그래프에 사용)
  const agreePercentage = computed(() => {
    if (currentVote.value.totalCount === 0) return 50; // 투표가 0개일 때 기본값
    return (currentVote.value.agreeCount / currentVote.value.totalCount) * 100;
  });
  // 반대 비율 계산
  const disagreePercentage = computed(() => {
    if (currentVote.value.totalCount === 0) return 50; // 투표가 0개일 때 기본값
    return (currentVote.value.disagreeCount / currentVote.value.totalCount) * 100;
  });

  // 투표 생성 폼 열기
  const startVote = () => {
    showVoteCreation.value = true;
    showNoPollMessage.value = false;  
    showCreateVoteSuccessMessage.value = null; 
    showCreateVoteErrorMessage.value = null;
  };

  // 투표 생성 제출
  const createVote = async (postTitle) => {

    console.log('투표 생성 요청 : createVote', {
      postId: postIdProp.value,
      userId: currentUserId.value,
      title: postTitle,
      option1: voteOptions.value.agreeTitle,
      option2: voteOptions.value.disagreeTitle
    }); ;

    showCreateVoteSuccessMessage.value = null;
    showCreateVoteErrorMessage.value = null;

    if (!voteOptions.value.agreeTitle || !voteOptions.value.disagreeTitle) {
      showCreateVoteErrorMessage.value ='소제목을 모두 입력해주세요.';
      return;
    }

    const postId = postIdProp.value; // 현재 게시글의 ID를 가져옵니다.

    if (!currentUserId.value) {
      showCreateVoteErrorMessage.value = '로그인 후 투표를 생성할 수 있습니다.';
      return;
    }
    if (!postId) {
      showCreateVoteErrorMessage.value = '게시글 정보를 불러올 수 없습니다. 투표를 생성할 수 없습니다.';
      return;
    }

    try {
      const response = await axios.post('/poll/createPoll', {
        postId: postId,
        userId: currentUserId.value,
        title: postTitle,
        option1: voteOptions.value.agreeTitle,
        option2: voteOptions.value.disagreeTitle
      });

      showCreateVoteSuccessMessage.value = '투표가 성공적으로 생성되었습니다!';
      showVoteCreation.value = false; // 폼 닫기
      voteOptions.value = { agreeTitle: '', disagreeTitle: '' }; // 입력 필드 초기화
      showNoPollMessage.value = false; 
      showVoteErrorMessage.value = null; 

      await loadPoll(postId);

    } catch (error) {
      console.error('투표 생성 실패:', error);
      showCreateVoteErrorMessage.value = '투표 생성에 실패했습니다. 다시 시도해주세요.';
    }
  };

  // 투표하기
  const castVote = async (selectedOptionType, postIdValue) => {

    console.log("castVote 호출 selectedOptionType:", selectedOptionType);

    if (!currentVote.value) return; // 투표 정보가 없으면 리턴
    if (currentVote.value.isVoted) {
      showCreateVoteErrorMessage.value = '이미 투표하셨습니다.';
      return;
    }

    if (!currentUserId.value) {
      showCreateVoteErrorMessage.value =  '로그인 후 투표에 참여할 수 있습니다.';
      return;
    }

    let selectedOptionNumber;
    if (selectedOptionType === 'agree') {
      selectedOptionNumber = 1;
    } else if (selectedOptionType === 'disagree') {
      selectedOptionNumber = 2;
    } else {
      console.error('유효하지 않은 옵션 타입:', selectedOptionType);
      return;
    }

    try {
      // 투표 참여 API 호출
      const response = await axios.post('/vote/castVote', {
        pollId: currentVote.value.vote_id,
        userId: currentUserId.value,
        selectedOption: selectedOptionNumber
      });

      // 프론트엔드에서 투표 결과 즉시 업데이트
      if (selectedOptionType === 'agree') {
        currentVote.value.agree++;
      } else if (selectedOptionType === 'disagree') {
        currentVote.value.disagree++;
      }
      
      console.log("투표 참여 성공, 응답 데이터:", response.data);
      console.log("투표 성공(1), 현재 투표 상태:", currentVote.value);

      showCreateVoteSuccessMessage.value = '투표가 완료되었습니다!';
      
      await loadPoll(postIdValue);
      console.log("투표 성공(2), 현재 투표 상태:", currentVote.value);
      showVoteErrorMessage.value = null; // 성공 시 에러 메시지 초기화
      
    } catch (error) {
      console.error('투표 참여 실패:', error);
      if (error.response && error.response.status === 409 || (response.data === 0)) { // 409 Conflict는 보통 중복 투표
        showCreateVoteErrorMessage.value = '이미 이 투표에 참여하셨습니다.';
        currentVote.value.isVoted = true; // 서버에서 이미 투표했다고 알려주면 상태 업데이트
      } else {
        showCreateVoteErrorMessage.value = '투표 참여에 실패했습니다. 다시 시도해주세요.';
      }
    }
  };

  const loadPoll = async (postIdValue) => {
    console.log("loadPoll 호출, postIdValue:", postIdValue);

    if (!postIdValue) {
      currentVote.value = null;
      return;
    }
  
    const apiUrl = `/poll/loadPoll?post_id=${postIdValue}`;
    const fullApiUrl = currentUserId.value
      ? `${apiUrl}&user_id=${currentUserId.value}`
      : apiUrl;
  
    try {
      const pollResponse = await axios.get(fullApiUrl);
      console.log('투표 불러오기 성공:', pollResponse.data);
  
      if (pollResponse.status !== 200) {
        console.warn('예상치 못한 응답 상태 코드:', pollResponse.status);
        currentVote.value = null;
        showNoPollMessage.value = true;
        return;
      }
  
      const pollData = pollResponse.data.poll;
      const voteData = pollResponse.data.vote || {};
      const hasVoted = currentUserId.value ? pollResponse.data.hasVoted || false : false;
      const hasLiked = currentUserId.value ? pollResponse.data.hasLiked || false : false;

      if (!pollData) {
        console.log('pollData가 없음 : ', pollData);
        currentVote.value = {
          isLiked: hasLiked,
          voteData : null,
        };
        
        showNoPollMessage.value = true;
        return;
      }

  
      currentVote.value = {
        vote_id: pollData.pollId,
        agreeTitle: pollData.option1,
        disagreeTitle: pollData.option2,
        agreeCount: voteData.agreeCount ?? 0,
        disagreeCount: voteData.disagreeCount ?? 0,
        totalCount: voteData.totalCount ?? 0,
        isVoted: hasVoted,
        isLiked: hasLiked,
        voteTitle: pollData.title,
        createAt: pollData.createdAt,
      };

      console.log('loadPoll() 함수 return 값:', currentVote.value);
  
      showNoPollMessage.value = false;
  
    } catch (error) {
      console.error('투표 상태 불러오기 실패:', error);
      currentVote.value = null;
      showNoPollMessage.value = true;
  
      if (error.response) {
        showVoteErrorMessage.value = error.response.data.message || "투표 정보를 불러오는 중 오류가 발생했습니다.";
      } else if (error.request) {
        showVoteErrorMessage.value = "서버에 연결할 수 없습니다. 네트워크 상태를 확인해주세요.";
      } else {
        showVoteErrorMessage.value = "요청 설정 중 오류가 발생했습니다.";
      }
    }
    console.log("loadPoll() 함수에서 pollData가 없을 때 currentVote : ", currentVote.value);
  };
  

// 투표 관련 상태 초기화 함수
const resetVote = () => {
  currentVote.value = null;
  showVoteCreation.value = false;
  voteOptions.value = { agreeTitle: '', disagreeTitle: '' };
  showNoPollMessage.value = false;
  showVoteErrorMessage.value = null; // 에러 메시지도 초기화
  showCreateVoteSuccessMessage.value = null; 
  showCreateVoteErrorMessage.value = null; 
};

 const showNoPollMessage = ref(false);
 const showVoteErrorMessage = ref(null);
 const showCreateVoteSuccessMessage = ref(null);
 const showCreateVoteErrorMessage = ref(null);

  // postIdProp이 변경될 때마다 투표 정보를 자동으로 불러옵니다.
  // PostDetail 컴포넌트 마운트 시에도 즉시 실행됩니다.
  watch(postIdProp, async (newPostId) => {
    if (newPostId) {
      await loadPoll(newPostId);
    } else {
      resetVote(); // postId가 없으면 투표 상태 초기화
    }
  }, { immediate: true });


  return {
    showVoteCreation,
    voteOptions,
    currentVote,
    agreePercentage,
    disagreePercentage,
    startVote,
    createVote,
    castVote,
    resetVote,
    loadPoll,
    showNoPollMessage,
    showVoteErrorMessage,
    showCreateVoteSuccessMessage, 
    showCreateVoteErrorMessage
  };
}