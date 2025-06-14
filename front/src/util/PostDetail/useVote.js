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

  // 투표 총 개수 계산
  const totalVotes = computed(() => {
    return (currentVote.value?.agree || 0) + (currentVote.value?.disagree || 0);
  });
  // 찬성 비율 계산 (bar 그래프에 사용)
  const agreePercentage = computed(() => {
    if (totalVotes.value === 0) return 50; // 투표가 0개일 때 기본값
    return (currentVote.value.agree / totalVotes.value) * 100;
  });
  // 반대 비율 계산
  const disagreePercentage = computed(() => {
    if (totalVotes.value === 0) return 50; // 투표가 0개일 때 기본값
    return (currentVote.value.disagree / totalVotes.value) * 100;
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

    console.log('전송될 데이터:', {
      post_id: postId,
      user_id: currentUserId.value,
      title: postTitle,
      option_1: voteOptions.value.agreeTitle,
      option_2: voteOptions.value.disagreeTitle
    });

    try {
      // 투표 생성 엔드포인트는 `/poll/createPoll`로 유지
      const response = await axios.post('/poll/createPoll', {
        postId: postId,
        userId: currentUserId.value,
        title: postTitle,
        option1: voteOptions.value.agreeTitle,
        option2: voteOptions.value.disagreeTitle
      });

      // 생성된 투표 정보를 currnetVote에 저장하기 위한 로직
      currentVote.value = {
        agree: 0,
        disagree: 0,
        agreeTitle: voteOptions.value.agreeTitle,
        disagreeTitle: voteOptions.value.disagreeTitle,
        isVoted: false, // 새로 생성했으니 아직 투표 안함
        voteTitle: postTitle
      };

      showCreateVoteSuccessMessage.value = '투표가 성공적으로 생성되었습니다!';
      showVoteCreation.value = false; // 폼 닫기
      voteOptions.value = { agreeTitle: '', disagreeTitle: '' }; // 입력 필드 초기화
      showNoPollMessage.value = false; 
      showVoteErrorMessage.value = null; 


    } catch (error) {
      console.error('투표 생성 실패:', error);
      showCreateVoteErrorMessage.value = '투표 생성에 실패했습니다. 다시 시도해주세요.';
    }
  };

  // 투표하기
  const castVote = async (selectedOptionType) => {
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
      await axios.post('/vote/chooseVote', {
        voteId: currentVote.value.vote_id, // 이 값이 정확히 전달되어야 합니다.
        postId : postIdProp.value,
        userId: currentUserId.value,
        selectedOption: selectedOptionNumber
      });

      // 프론트엔드에서 투표 결과 즉시 업데이트
      if (selectedOptionType === 'agree') {
        currentVote.value.agree++;
      } else if (selectedOptionType === 'disagree') {
        currentVote.value.disagree++;
      }
      showCreateVoteSuccessMessage.value = '투표가 완료되었습니다!';
      currentVote.value = response.data;
      showVoteErrorMessage.value = null; // 성공 시 에러 메시지 초기화
    } catch (error) {
      console.error('투표 참여 실패:', error);
      if (error.response && error.response.status === 409) { // 409 Conflict는 보통 중복 투표
        showCreateVoteSuccessMessage.value = '이미 이 투표에 참여하셨습니다.';
        currentVote.value.isVoted = true; // 서버에서 이미 투표했다고 알려주면 상태 업데이트
      } else {
        showCreateVoteSuccessMessage.value = '투표 참여에 실패했습니다. 다시 시도해주세요.';
      }
    }
  };

  const fetchVoteStatusByPostId = async (postIdValue) => { // 매개변수 이름을 명확히 변경
    if (!postIdValue) { // postIdProp.value 대신 매개변수 사용
        currentVote.value = null;
        return;
    }
    try {
        const pollResponse = await axios.get(`/poll/loadPoll?post_id=${postIdValue}`);

        // 응답 상태가 200 OK일 때만 데이터 처리
        if (pollResponse.status === 200) {
            const pollData = pollResponse.data.poll;
            const hasVotedFromServer = pollResponse.data.hasVoted;

            if (!pollData) { // 이 부분은 404를 반환하면 필요 없어지지만, 혹시 모를 경우를 대비
                console.warn('투표 데이터를 찾을 수 없습니다 (응답 데이터에 poll 객체 없음).');
                currentVote.value = null;
                return;
            }

            const userIdForCheck = currentUserId.value;
            let hasVoted = false;
            if (userIdForCheck) {
                hasVoted = hasVotedFromServer;
            }

            currentVote.value = {
                vote_id: pollData.pollId,
                agree: pollData.option1,
                disagree: pollData.option2,
                agreeTitle: '찬성',
                disagreeTitle: '반대',
                isVoted: hasVoted,
                voteTitle: pollData.title
            };
            console.log('투표 불러오기 성공:', currentVote.value);
            // 투표가 존재하지 않는다는 메시지를 보여줄 필요가 없으므로 지움
            showNoPollMessage.value = false; 

        } else {
            // 200 OK가 아닌 다른 성공 상태 코드 (예: 204 No Content)가 올 경우의 처리
            // 현재 백엔드는 200 OK 또는 404/500을 반환하므로 이 부분은 상황에 따라 조정
            console.warn('예상치 못한 응답 상태 코드:', pollResponse.status);
            currentVote.value = null;
            showNoPollMessage.value = true; // 또는 다른 메시지 설정
        }

    } catch (error) {
        console.error('투표 상태 불러오기 실패:', error);
        currentVote.value = null; // 실패 시 투표 상태 초기화

        // HTTP 상태 코드에 따른 메시지 처리
        if (error.response) {
            // 서버가 응답했지만 상태 코드가 2xx 범위 밖인 경우
            if (error.response.status === 404) {
                // 백엔드에서 보낸 메시지를 표시
                showVoteErrorMessage.value = error.response.data.message;
                showNoPollMessage.value = true; 
            } else if (error.response.status === 500) {
              showVoteErrorMessage.value = error.response.data.message || "투표 정보를 불러오는데 실패했습니다.";
              showNoPollMessage.value = true; 
            } else {
              showVoteErrorMessage.value = error.response.data.message || "투표 정보를 불러오는데 실패했습니다.";
              showNoPollMessage.value = true;
            }
        } else if (error.request) {
            // 요청이 전송되었으나 응답을 받지 못한 경우 (네트워크 오류 등)
            showVoteErrorMessage.value = "서버에 연결할 수 없습니다. 네트워크 상태를 확인해주세요.";
            showNoPollMessage.value = true;
        } else {
            // 요청 설정 중 오류 발생
            showVoteErrorMessage.value = "요청 설정 중 오류가 발생했습니다.";
            showNoPollMessage.value = true;
        }
    }
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
      await fetchVoteStatusByPostId(newPostId);
    } else {
      resetVote(); // postId가 없으면 투표 상태 초기화
    }
  }, { immediate: true });


  return {
    showVoteCreation,
    voteOptions,
    currentVote,
    totalVotes,
    agreePercentage,
    disagreePercentage,
    startVote,
    createVote,
    castVote,
    resetVote,
    fetchVoteStatusByPostId, // PostDetail에서 직접 호출할 함수
    showNoPollMessage,
    showVoteErrorMessage,
    showCreateVoteSuccessMessage, 
    showCreateVoteErrorMessage
  };
}