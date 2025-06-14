const mockPolls = [
    // 인기 투표
    {
      id: "hot-1",
      title: "2024년 가장 중요한 사회적 이슈는?",
      description: "올해 우리 사회가 가장 집중해야 할 문제에 대해 투표해주세요.",
      category: "인기",
      options: [
        { id: "1", text: "경제 불평등 해소", votes: 2847 },
        { id: "2", text: "환경 보호", votes: 1923 },
        { id: "3", text: "교육 개혁", votes: 1456 },
        { id: "4", text: "의료 시스템 개선", votes: 1234 },
      ],
      totalVotes: 7460,
      timeLeft: "2일 남음",
      isHot: true,
    },
    // 정치 카테고리
    {
      id: "pol-1",
      title: "정치 개혁의 우선순위는?",
      description: "현재 정치 시스템에서 가장 시급한 개혁 과제",
      category: "정치",
      options: [
        { id: "1", text: "선거제도 개혁", votes: 1234 },
        { id: "2", text: "정당 시스템 개선", votes: 987 },
      ],
      totalVotes: 2221,
      timeLeft: "5일 남음",
    },
    {
      id: "pol-2",
      title: "지방자치의 확대 방향",
      description: "지방정부의 권한 확대에 대한 의견",
      category: "정치",
      options: [
        { id: "1", text: "재정 자율권 확대", votes: 856 },
        { id: "2", text: "행정 권한 확대", votes: 743 },
      ],
      totalVotes: 1599,
      timeLeft: "3일 남음",
    },
    {
      id: "pol-3",
      title: "국정감사 제도 개선 방안",
      description: "효과적인 국정감사를 위한 제도 개선",
      category: "정치",
      options: [
        { id: "1", text: "전문성 강화", votes: 654 },
        { id: "2", text: "투명성 확대", votes: 521 },
      ],
      totalVotes: 1175,
      timeLeft: "1주 남음",
    },
    {
      id: "pol-4",
      title: "정치인 윤리 강화 방안",
      description: "정치인의 도덕적 책임 강화 방법",
      category: "정치",
      options: [
        { id: "1", text: "법적 처벌 강화", votes: 789 },
        { id: "2", text: "시민 감시 확대", votes: 456 },
      ],
      totalVotes: 1245,
      timeLeft: "4일 남음",
    },
    {
      id: "pol-5",
      title: "청년 정치 참여 확대 방안",
      description: "젊은 세대의 정치 참여를 늘리는 방법",
      category: "정치",
      options: [
        { id: "1", text: "디지털 플랫폼 활용", votes: 923 },
        { id: "2", text: "교육 프로그램 확대", votes: 567 },
      ],
      totalVotes: 1490,
      timeLeft: "6일 남음",
    },
    // 사회 카테고리
    {
      id: "soc-1",
      title: "고령화 사회 대응 방안",
      description: "급속한 고령화에 대한 사회적 대응책",
      category: "사회",
      options: [
        { id: "1", text: "연금 제도 개혁", votes: 1456 },
        { id: "2", text: "의료 시스템 확충", votes: 1123 },
      ],
      totalVotes: 2579,
      timeLeft: "2일 남음",
    },
    {
      id: "soc-2",
      title: "주거 문제 해결 방안",
      description: "청년층 주거 안정을 위한 정책",
      category: "사회",
      options: [
        { id: "1", text: "공공주택 확대", votes: 1789 },
        { id: "2", text: "임대료 규제", votes: 1234 },
      ],
      totalVotes: 3023,
      timeLeft: "1일 남음",
    },
    {
      id: "soc-3",
      title: "교육 불평등 해소 방안",
      description: "교육 기회의 평등 실현 방법",
      category: "사회",
      options: [
        { id: "1", text: "공교육 강화", votes: 987 },
        { id: "2", text: "사교육 규제", votes: 765 },
      ],
      totalVotes: 1752,
      timeLeft: "3일 남음",
    },
    {
      id: "soc-4",
      title: "일자리 창출 우선 분야",
      description: "미래 일자리 창출이 필요한 분야",
      category: "사회",
      options: [
        { id: "1", text: "IT·디지털", votes: 1345 },
        { id: "2", text: "환경·에너지", votes: 876 },
      ],
      totalVotes: 2221,
      timeLeft: "5일 남음",
    },
    {
      id: "soc-5",
      title: "사회 안전망 강화 방안",
      description: "취약계층 보호를 위한 정책",
      category: "사회",
      options: [
        { id: "1", text: "기본소득 도입", votes: 1567 },
        { id: "2", text: "복지 서비스 확대", votes: 1098 },
      ],
      totalVotes: 2665,
      timeLeft: "1주 남음",
    },
    // 철학 카테고리
    {
      id: "phi-1",
      title: "인공지능 시대의 인간 가치",
      description: "AI 발전 속에서 인간의 고유한 가치는 무엇인가",
      category: "철학",
      options: [
        { id: "1", text: "창의성과 감정", votes: 892 },
        { id: "2", text: "도덕적 판단력", votes: 654 },
      ],
      totalVotes: 1546,
      timeLeft: "4일 남음",
    },
    {
      id: "phi-2",
      title: "개인의 자유 vs 사회적 책임",
      description: "개인의 자유와 사회적 책임 중 무엇이 우선인가",
      category: "철학",
      options: [
        { id: "1", text: "개인의 자유", votes: 743 },
        { id: "2", text: "사회적 책임", votes: 567 },
      ],
      totalVotes: 1310,
      timeLeft: "2일 남음",
    },
    {
      id: "phi-3",
      title: "진리의 본질에 대한 관점",
      description: "절대적 진리는 존재하는가",
      category: "철학",
      options: [
        { id: "1", text: "절대적 진리 존재", votes: 456 },
        { id: "2", text: "상대적 진리만 존재", votes: 389 },
      ],
      totalVotes: 845,
      timeLeft: "6일 남음",
    },
    {
      id: "phi-4",
      title: "행복의 정의",
      description: "진정한 행복이란 무엇인가",
      category: "철학",
      options: [
        { id: "1", text: "내적 만족과 평화", votes: 1234 },
        { id: "2", text: "목표 달성과 성취", votes: 876 },
      ],
      totalVotes: 2110,
      timeLeft: "3일 남음",
    },
    {
      id: "phi-5",
      title: "기술 발전과 인간성",
      description: "기술 발전이 인간성에 미치는 영향",
      category: "철학",
      options: [
        { id: "1", text: "인간성을 향상시킨다", votes: 567 },
        { id: "2", text: "인간성을 훼손한다", votes: 432 },
      ],
      totalVotes: 999,
      timeLeft: "1주 남음",
    },
  ];
export default mockPolls