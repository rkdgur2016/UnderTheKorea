<template>
  <div class="signup-container">
    <h2>회원가입</h2>
    <form @submit.prevent="handleSignup" class="signup-form">
      <div class="form-group">
        <label for="userId">아이디</label>
        <input type="text" id="userId" v-model="form.userId" required minlength="4" maxlength="20">
        <p v-if="errors.userId" class="error-message">{{ errors.userId }}</p>
      </div>

      <div class="form-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="form.password" required minlength="8">
        <p v-if="errors.password" class="error-message">{{ errors.password }}</p>
      </div>

      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="form.confirmPassword" required>
        <p v-if="errors.confirmPassword" class="error-message">{{ errors.confirmPassword }}</p>
      </div>

      <div class="form-group">
        <label for="username">사용자 이름</label>
        <input type="text" id="username" v-model="form.username" required maxlength="50">
        <p v-if="errors.username" class="error-message">{{ errors.username }}</p>
      </div>

      <div class="form-group">
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="form.email" required maxlength="100">
        <p v-if="errors.email" class="error-message">{{ errors.email }}</p>
      </div>

      <button type="submit" :disabled="isSubmitting">회원가입</button>

      <p v-if="signupMessage" :class="{ 'success': isSignupSuccess, 'error': !isSignupSuccess }" class="signup-message">
        {{ signupMessage }}
      </p>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from 'axios'; // npm install axios 필요
import { useRouter } from 'vue-router';

const router = useRouter();

const form = reactive({
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
  email: ''
});

const errors = reactive({
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
  email: ''
});

const isSubmitting = ref(false);
const signupMessage = ref('');
const isSignupSuccess = ref(false);

const validateForm = () => {
  let isValid = true;
  // 모든 에러 메시지 초기화
  Object.keys(errors).forEach(key => errors[key] = '');

  if (!form.userId) {
    errors.userId = '아이디를 입력해주세요.';
    isValid = false;
  } else if (form.userId.length < 4 || form.userId.length > 20) {
    errors.userId = '아이디는 4자 이상 20자 이하로 입력해주세요.';
    isValid = false;
  }

  if (!form.password) {
    errors.password = '비밀번호를 입력해주세요.';
    isValid = false;
  } else if (form.password.length < 8) {
    errors.password = '비밀번호는 최소 8자 이상이어야 합니다.';
    isValid = false;
  }

  if (form.password !== form.confirmPassword) {
    errors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    isValid = false;
  }

  if (!form.username) {
    errors.username = '사용자 이름을 입력해주세요.';
    isValid = false;
  } else if (form.username.length > 50) {
    errors.username = '사용자 이름은 50자를 초과할 수 없습니다.';
    isValid = false;
  }

  if (!form.email) {
    errors.email = '이메일을 입력해주세요.';
    isValid = false;
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '유효한 이메일 주소를 입력해주세요.';
    isValid = false;
  } else if (form.email.length > 100) {
    errors.email = '이메일은 100자를 초과할 수 없습니다.';
    isValid = false;
  }

  return isValid;
};

const handleSignup = async () => {
  if (!validateForm()) {
    signupMessage.value = '입력값을 확인해주세요.';
    isSignupSuccess.value = false;
    return;
  }

  isSubmitting.value = true;
  signupMessage.value = '';

  try {
    const response = await axios.post('/api/users', {
      userId: form.userId,
      password: form.password,
      username: form.username,
      email: form.email
    });

    if (response.status === 201) { // HttpStatus.CREATED
      signupMessage.value = '회원가입이 성공적으로 완료되었습니다! 로그인 페이지로 이동합니다.';
      isSignupSuccess.value = true;
      // 성공 후 2초 뒤 로그인 페이지로 리다이렉트
      setTimeout(() => {
        router.push('/login'); // 로그인 페이지 경로로 변경 (예: /login)
      }, 2000);
    }
  } catch (error) {
    console.error('회원가입 실패:', error);
    isSignupSuccess.value = false;
    if (error.response) {
      // 백엔드에서 전달된 에러 메시지가 있다면 사용
      if (error.response.data && error.response.data.message) {
        signupMessage.value = '회원가입 실패: ' + error.response.data.message;
      } else if (error.response.status === 409) { // 예를 들어, 아이디/이메일 중복 시 409 Conflict
          signupMessage.value = '회원가입 실패: 이미 존재하는 아이디 또는 이메일입니다.';
      } else if (error.response.status === 400) { // 유효성 검사 실패 등
          signupMessage.value = '회원가입 실패: 유효하지 않은 입력값이 있습니다.';
          // 백엔드에서 상세 에러 메시지를 제공하는 경우 처리 (예: Spring의 @Valid 에러)
          if (error.response.data.errors) {
              error.response.data.errors.forEach(err => {
                  if (errors[err.field]) {
                      errors[err.field] = err.defaultMessage;
                  }
              });
          }
      }
      else {
        signupMessage.value = '회원가입 실패: ' + error.response.statusText;
      }
    } else if (error.request) {
      signupMessage.value = '회원가입 실패: 서버 응답이 없습니다.';
    } else {
      signupMessage.value = '회원가입 실패: 요청을 보내는 중 오류가 발생했습니다.';
    }
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
.signup-container {
  max-width: 500px;
  margin: 50px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  font-family: 'Pretendard', sans-serif;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 25px;
  font-weight: 700;
}

.signup-form .form-group {
  margin-bottom: 20px;
}

.signup-form label {
  display: block;
  margin-bottom: 8px;
  color: #555;
  font-weight: 500;
}

.signup-form input[type="text"],
.signup-form input[type="password"],
.signup-form input[type="email"] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

.signup-form input[type="text"]:focus,
.signup-form input[type="password"]:focus,
.signup-form input[type="email"]:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
}

.signup-form button[type="submit"] {
  width: 100%;
  padding: 12px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 15px;
}

.signup-form button[type="submit"]:hover:not(:disabled) {
  background-color: #0056b3;
}

.signup-form button[type="submit"]:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #dc3545;
  font-size: 0.85em;
  margin-top: 5px;
}

.signup-message {
  text-align: center;
  margin-top: 20px;
  padding: 10px;
  border-radius: 5px;
  font-weight: 500;
}

.signup-message.success {
  background-color: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.signup-message.error {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>