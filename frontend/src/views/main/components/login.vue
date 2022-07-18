<template>
<div class="signUp-container">
  <img :src="require(`@/assets/images/login.png`)" alt="login" class="login"/>
  <div>
    <input type="text" placeholder="e-mail 입력" >
  </div>
  <div>
  <input type="password" placeholder="비밀번호 입력">
</div>
<div>
  <el-checkbox-button>로그인 유지</el-checkbox-button>
</div>
<div>
  <button>로그인</button>
</div>
<div>회원이 아니신가요? <router-link to="/signUp">회원가입</router-link></div>
</div>

</template>

<script>
export default {

}
</script>

<style>
.login {
  margin-top: 5%;
  width: 50%;
}
</style>
