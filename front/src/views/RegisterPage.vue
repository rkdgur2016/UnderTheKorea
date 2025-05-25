<template>
  <div class="register-page">
    <h2>회원가입</h2>
    <p>회원가입 페이지입니다. 여기에 회원가입 폼을 구현해주세요.</p>
    <form @submit.prevent="submitRegister">
      <div class="form-group">
        <label for="reg-id">아이디</label>
        <input type="text" id="reg-id" v-model="registerForm.id" required />
      </div>
      <div class="form-group">
        <label for="reg-password">비밀번호</label>
        <input type="password" id="reg-password" v-model="registerForm.password" required />
      </div>
      <div class="form-group">
        <label for="reg-name">사용할 닉네임</label>
        <input type="name" id="reg-name" v-model="registerForm.name" required />
      </div>
      <button type="submit" class="submit-btn">가입하기</button>
    </form>
    <button @click="goBack" class="back-btn">뒤로 가기</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const registerForm = ref({
  id: '',
  password: '',
  email: ''
});

const submitRegister = () => {
  // 실제 회원가입 API 호출 로직
  alert(`회원가입 요청: ID(${registerForm.value.id}), Email(${registerForm.value.name})`);
  // 성공 시 로그인 페이지나 메인 페이지로 리다이렉트
  router.push('/'); 
};

const goBack = () => {
  router.go(-1); // 이전 페이지로 돌아가기
};
</script>

<style scoped>
.register-page {
  max-width: 500px;
  margin: 50px auto;
  padding: 30px;
  border: 1px solid #e3e6ea;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  background-color: #fff;
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  text-align: center;
}

h2 {
  color: #232a33;
  margin-bottom: 25px;
  font-size: 1.8rem;
}

p {
  color: #555;
  margin-bottom: 20px;
  line-height: 1.5;
}

form {
  margin-top: 20px;
  text-align: left;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
  color: #354052;
}

.form-group input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #d0dbe9;
  border-radius: 5px;
  font-size: 1rem;
  box-sizing: border-box;
}

.form-group input:focus {
  outline: none;
  border-color: #4f8cff;
  box-shadow: 0 0 0 2px rgba(79, 140, 255, 0.2);
}

.submit-btn {
  width: 100%;
  background: #4f8cff;
  color: #fff;
  border: none;
  border-radius: 0.28rem;
  padding: 12px 15px;
  cursor: pointer;
  transition: background 0.2s;
  font-size: 1rem;
  font-weight: 700;
  margin-top: 15px;
}

.submit-btn:hover {
  background: #3a75e0;
}

.back-btn {
  margin-top: 25px;
  background: #e9eff7;
  color: #4f8cff;
  border: 1px solid #d0dbe9;
  border-radius: 0.28rem;
  padding: 10px 20px;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.9rem;
}

.back-btn:hover {
  background: #4f8cff;
  color: #fff;
  border-color: #4f8cff;
}
</style>