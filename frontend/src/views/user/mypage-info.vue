<template>
  <div class= 'mypage-info'>
    <form>

      <div><span>이메일</span><textarea>OOOO@naver.com</textarea></div>
      <div><sapn>비밀번호</sapn><input type="password" placeholder="[비밀번호}"></div>
      <div><span>비밀번호 확인</span><input type="password" placeholder="{비밀번호}"></div>
      <div><span>닉네임</span><input placeholder="{닉네임}"></div>
      <button>수정</button>
    </form>
  </div>
    <div><button>회원탈퇴</button></div>
</template>

<script>
export default {

}
</script>

<style>
.mypage-info {
  margin-top: 100px;
  display: flex;
  justify-content: space-around;
}
</style>
