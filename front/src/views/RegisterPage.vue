<template>
  <div class="signup-container">
    <h2>회원가입</h2>
    <form @submit.prevent="handleSignup" class="signup-form">
      <div class="form-group">
        <label for="userId">아이디</label>
        <div class="input-with-button">
          <input type="text" id="userId" v-model="form.userId" required minlength="4" maxlength="20">
          <button type="button" @click="checkUserId" :disabled="isCheckingId">중복 확인</button>
        </div>
        <p v-if="errors.userId" class="error-message">{{ errors.userId }}</p>
        <p v-if="userIdCheckMessage" :class="{ 'success': isUserIdAvailable, 'error': !isUserIdAvailable }" class="check-message">
          {{ userIdCheckMessage }}
        </p>
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
      <button type="submit" :disabled="isSubmitting">회원가입</button>
      <p v-if="signupMessage" :class="{ 'success': isSignupSuccess, 'error': !isSignupSuccess }" class="signup-message">
        {{ signupMessage }}
      </p>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const form = reactive({
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
});

const errors = reactive({
  userId: '',
  password: '',
  confirmPassword: '',
  username: '',
});

const isSubmitting = ref(false);
const signupMessage = ref('');
const isSignupSuccess = ref(false);

const isCheckingId = ref(false);
const userIdCheckMessage = ref('');
const isUserIdAvailable = ref(false);

const validateForm = () => {
  let isValid = true;
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

  return isValid;
};

const checkUserId = async () => {
  errors.userId = '';
  userIdCheckMessage.value = '';
  isUserIdAvailable.value = false;

  if (!form.userId) {
    errors.userId = '아이디를 입력해주세요.';
    return;
  }
  if (form.userId.length < 4 || form.userId.length > 20) {
    errors.userId = '아이디는 4자 이상 20자 이하로 입력해주세요.';
    return;
  }

  isCheckingId.value = true;
  try {
    const response = await axios.get(`/api/users/check-userId?userId=${form.userId}`);
    if (response.data.success) {
      userIdCheckMessage.value = response.data.message;
      isUserIdAvailable.value = true;
    } else {
      userIdCheckMessage.value = response.data.message;
      isUserIdAvailable.value = false;
    }
  } catch (error) {
    console.error('아이디 중복 확인 실패:', error);
    userIdCheckMessage.value = '아이디 확인 중 오류가 발생했습니다.';
    isUserIdAvailable.value = false;
    if (error.response && error.response.status === 400 && error.response.data.data) {
      const errorFields = error.response.data.data;
      errorFields.forEach(err => {
        if (err.field === 'userId') {
          errors.userId = err.defaultMessage;
        }
      });
      userIdCheckMessage.value = '';
    } else if (error.response && error.response.data && error.response.data.message) {
      userIdCheckMessage.value = error.response.data.message;
    }
  } finally {
    isCheckingId.value = false;
  }
};


const handleSignup = async () => {
  if (!validateForm()) {
    signupMessage.value = '입력값을 확인해주세요.';
    isSignupSuccess.value = false;
    return;
  }

  isSubmitting.value = true;
  signupMessage.value = '';
  userIdCheckMessage.value = '';
  isUserIdAvailable.value = false;

  try {
    const response = await axios.post('/api/users', {
      userId: form.userId,
      password: form.password,
      username: form.username,
    });

    if (response.data.success) {
      signupMessage.value = response.data.message;
      isSignupSuccess.value = true;
      setTimeout(() => {
        router.push('/login');
      }, 2000);
    } else {
      signupMessage.value = response.data.message || '회원가입에 실패했습니다.';
      isSignupSuccess.value = false;
    }
  } catch (error) {
    console.error('회원가입 실패:', error);
    isSignupSuccess.value = false;

    if (error.response) {
      const errorData = error.response.data;
      signupMessage.value = errorData.message || '회원가입에 실패했습니다.';

      if (error.response.status === 400 && errorData.data && Array.isArray(errorData.data)) {
        errorData.data.forEach(err => {
          if (errors[err.field]) {
            errors[err.field] = err.defaultMessage;
          }
        });
        signupMessage.value = '입력값을 확인해주세요.';
      } else if (error.response.status === 409) {
        errors.userId = errorData.message;
        signupMessage.value = '회원가입 실패: ' + errorData.message;
      } else if (errorData.message) {
        signupMessage.value = '회원가입 실패: ' + errorData.message;
      }
    } else if (error.request) {
      signupMessage.value = '회원가입 실패: 서버 응답이 없습니다. 네트워크 연결을 확인해주세요.';
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

.input-with-button {
  display: flex;
  gap: 10px; /* 입력 필드와 버튼 사이 간격 */
  align-items: center;
}

/* 아이디 입력 필드의 폭을 조절 */
.input-with-button input[type="text"]#userId {
  flex-grow: 1; /* 남은 공간을 유연하게 채움 */
  width: auto; /* 기본 flex 동작을 위해 auto로 설정 */
  /* 필요하다면 max-width를 줘서 아이디 입력 필드의 최대 폭을 제한할 수 있습니다. */
  /* max-width: calc(100% - 110px); */ /* 버튼 폭 + gap을 고려한 예시 */
}


.signup-form input[type="text"],
.signup-form input[type="password"] {
  /* width: 100%; 이 규칙을 제거하거나 하위 선택자에서 오버라이드 */
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-sizing: border-box;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

/* 비밀번호, 비밀번호 확인, 사용자 이름 입력 필드의 폭을 100%로 유지 */
.signup-form input[type="password"],
.signup-form input[type="text"]#username { /* username input도 100% */
  width: 100%;
}


.signup-form input[type="text"]:focus,
.signup-form input[type="password"]:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
}

.signup-form button[type="submit"],
.signup-form button[type="button"] {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;
  white-space: nowrap;
}

.signup-form button[type="submit"] {
  width: 100%;
  margin-top: 15px;
  font-size: 1.1rem;
  padding: 12px 20px;
}


.signup-form button[type="submit"]:hover:not(:disabled),
.signup-form button[type="button"]:hover:not(:disabled) {
  background-color: #0056b3;
}

.signup-form button[type="submit"]:disabled,
.signup-form button[type="button"]:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #dc3545;
  font-size: 0.85em;
  margin-top: 5px;
}

.check-message {
  font-size: 0.85em;
  margin-top: 5px;
  padding: 0 5px;
}

.check-message.success {
  color: #28a745;
}

.check-message.error {
  color: #dc3545;
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