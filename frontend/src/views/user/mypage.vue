<template>
  <div class="mypage-top">
    <div>
      OOOO님의 마이페이지
    <router-link to="/myPage/info"><el-button>회원정보 수정</el-button></router-link>
    </div>
    <el-card>
      <h1>토론 기록 상세 보기</h1>
      <ul>
        <el-button @click="toggleOn"><span>2022.07.14 13:21 사회 주민등록번호제도는 유지되어야 하는가 </span><span id="que-1-toggle">더보기</span></el-button>
        <div v-if="show">내 포지션 : 찬성 <br> 결과 : 총 100명중 찬성 51명 반대 48명 무효 1명</div>
      </ul>
    </el-card>
    <dialog></dialog>
  </div>
</template>

<script>

export default {
 data () {
  return {
    show : false,
  }
 },
 methods : {
  toggleOn() {
    this.show = !this.show;
  },
 }

}

</script>

<style scoped>

.answer {
    display: none;
    padding-bottom: 30px;
  }
</style>
