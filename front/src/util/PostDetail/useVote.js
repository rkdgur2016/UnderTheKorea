import { ref, computed } from 'vue';

export function useVote() {
  const showVoteCreation = ref(false);
  const voteOptions = ref({
    agreeTitle: '',
    disagreeTitle: ''
  });
  const currentVote = ref(null);

  // 투표 총 개수 계산
  const totalVotes = computed(() => {
    return (currentVote.value?.agree || 0) + (currentVote.value?.disagree || 0);
  });

  // 찬성 비율 계산 (bar 그래프에 사용)
  const agreePercentage = computed(() => {
    if (totalVotes.value === 0) return 50;
    return (currentVote.value.agree / totalVotes.value) * 100;
  });

  // 반대 비율 계산
  const disagreePercentage = computed(() => {
    if (totalVotes.value === 0) return 50;
    return (currentVote.value.disagree / totalVotes.value) * 100;
  });

  // 투표 생성 폼 열기
  const startVote = () => {
    showVoteCreation.value = true;
  };

  // 투표 생성 제출
  const createVote = (postTitle) => { // 게시글 제목을 인자로 받도록 수정
    if (!voteOptions.value.agreeTitle || !voteOptions.value.disagreeTitle) {
      alert('찬성과 반대 소제목을 모두 입력해주세요.');
      return;
    }
    
    currentVote.value = {
      agree: 0,
      disagree: 0,
      agreeTitle: voteOptions.value.agreeTitle,
      disagreeTitle: voteOptions.value.disagreeTitle,
      isVoted: false,
      voteTitle: postTitle // 투표 제목을 저장
    };
    showVoteCreation.value = false;
    voteOptions.value = { agreeTitle: '', disagreeTitle: '' };
  };

  // 투표하기
  const castVote = (type) => {
    if (!currentVote.value) return;
    if (currentVote.value.isVoted) {
      alert('이미 투표하셨습니다.');
      return;
    }

    if (type === 'agree') {
      currentVote.value.agree++;
    } else if (type === 'disagree') {
      currentVote.value.disagree++;
    }
    currentVote.value.isVoted = true;
  };

  // 투표 상태를 초기화하는 함수 (게시물이 변경될 때 호출)
  const resetVote = () => {
    currentVote.value = null;
    showVoteCreation.value = false;
    voteOptions.value = { agreeTitle: '', disagreeTitle: '' };
  };


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
    resetVote
  };
}