<template>
<div class="signUp-container">
  <img :src="require(`@/assets/images/signup.png`)" alt="signUp" class="signup"/>
  <div class="input-container">
  <div class="inputarea">
    <input type="text" placeholder="e-mail 입력">
    <el-button type="primary">중복확인</el-button>
  </div>
  <div class="inputarea">
  <input type="password" placeholder="비밀번호 입력" >
</div>
<div class="inputarea">
  <input type="password" placeholder="비밀번호 확인" >
</div>
<div class="inputarea">
  <input type="text" placeholder="닉네임 입력" >
  <button>중복확인</button>
</div>
<div>
  <button>회원가입</button>
</div>
<div>회원이신가요? <router-link to="/login">로그인</router-link></div>
</div>
</div>
</template>

<script>
export default {

}
</script>

<style>
.signup {
  margin-top: 5%;
  width: 50%;
}
.inputarea {
  margin-bottom: 0.5%;
}
.input-container {

}
</style>
