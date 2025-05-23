<template>
  <main class="kr-suggest">
    <section class="suggest-board">
      <h1 class="suggest-title">게시물 제안하기</h1>
      <form class="suggest-form" @submit.prevent="submitForm">
        <div class="form-group">
          <label for="title">제목</label>
          <input
            id="title"
            v-model="form.title"
            type="text"
            required
            maxlength="100"
            placeholder="제목을 입력하세요"
          />
        </div>
        <div class="form-group">
          <label for="category">구분</label>
          <select id="category" v-model="form.category" required>
            <option value="">선택하세요</option>
            <option value="philosophy">철학</option>
            <option value="politics">정치</option>
            <option value="suggest">제안</option>
            <option value="motto">모토</option>
          </select>
        </div>
        <div class="form-group">
          <label for="youtube">YouTube 링크</label>
          <input
            id="youtube"
            v-model="form.youtube"
            type="url"
            placeholder="https://www.youtube.com/watch?v=..."
          />
        </div>
        <div class="form-group">
          <label for="content">내용</label>
          <textarea
            id="content"
            v-model="form.content"
            required
            rows="8"
            maxlength="2000"
            placeholder="내용을 입력하세요"
          ></textarea>
        </div>
        <div class="form-actions">
          <button type="submit">등록</button>
        </div>
      </form>
    </section>
  </main>
</template>

<script setup>
import { reactive } from 'vue'

const form = reactive({
  title: '',
  category: '',
  youtube: '',
  content: '',
})

function submitForm() {
  alert('제안이 등록되었습니다!\n' + JSON.stringify(form, null, 2))
  form.title = ''
  form.category = ''
  form.youtube = ''
  form.content = ''
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Pretendard:wght@400;700&family=Noto+Sans+KR:wght@400;700&display=swap');

.kr-suggest {
  background: #f7f9fb;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  padding-top: 40px;
}

.suggest-board {
  background: #fff;
  border: 1.5px solid #e3e6ea;
  border-radius: 14px;
  box-shadow: 0 4px 16px #dbe4ed33;
  padding: 36px 32px 32px 32px;
  max-width: 440px;
  width: 95vw;
  margin: 0 auto 40px auto;
}

.suggest-title {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1.6em;
  color: #222;
  text-align: center;
  margin-bottom: 30px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.suggest-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 7px;
}

label {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1.05em;
  color: #3b4861;
  font-weight: 600;
  margin-bottom: 2px;
  letter-spacing: -0.01em;
}

input[type="text"],
input[type="url"],
select,
textarea {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1em;
  padding: 11px 14px;
  border: 1.5px solid #c8d2e2;
  border-radius: 8px;
  background: #f8fafc;
  color: #222;
  outline: none;
  transition: border 0.18s, box-shadow 0.18s;
  box-shadow: 0 1px 4px #e5eaf055;
  resize: vertical;
}

input:focus,
select:focus,
textarea:focus {
  border-color: #4f8cff;
  background: #fff;
  box-shadow: 0 0 0 2px #4f8cff33;
}

textarea {
  min-height: 120px;
}

.form-actions {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

button[type="submit"] {
  font-family: 'Pretendard', 'Noto Sans KR', Arial, sans-serif;
  font-size: 1.08em;
  font-weight: 700;
  color: #fff;
  background: linear-gradient(90deg, #4f8cff 70%, #2d7be5 100%);
  border: none;
  border-radius: 8px;
  padding: 10px 38px;
  cursor: pointer;
  box-shadow: 0 2px 8px #e3e6ea;
  transition: background 0.18s, color 0.18s, transform 0.14s;
}

button[type="submit"]:hover,
button[type="submit"]:focus {
  background: linear-gradient(90deg, #fff 10%, #4f8cff 90%);
  color: #2d7be5;
  transform: translateY(-2px) scale(1.04);
  border: 1.5px solid #4f8cff;
}

@media (max-width: 600px) {
  .suggest-board {
    padding: 16px 4vw;
    max-width: 99vw;
  }
  .suggest-title {
    font-size: 1.15em;
  }
}
</style>
