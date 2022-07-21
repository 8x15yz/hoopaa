<template>
    <div id="mainpage-maincontainer">
        <div class="main-img-wrapp">
          <div class="main-img-wrapp-child">
            <img :src="require(`@/assets/images/071414.png`)" alt="">
            <div class="main-img-wrapp-button">
              <img :src="require(`@/assets/images/main-page-button01.png`)" alt="">
              <img :src="require(`@/assets/images/main-page-button02.png`)" alt="">
              <img :src="require(`@/assets/images/main-page-button03.png`)" alt="">
            </div>
          </div>

          <div class="main-img-wrapp-child main-introduce"><img v-if="show1" :src="require(`@/assets/images/mainPage01.png`)" alt=""><h1>팀을 구성하세요</h1></div>
          <div class="main-img-wrapp-child main-introduce"><img v-if="show2" :src="require(`@/assets/images/mainPage02.png`)" alt=""><h1>친구들과 재밌게 의견을 나눠보세요</h1></div>
          <div class="main-img-wrapp-child main-introduce"><img v-if="show3" :src="require(`@/assets/images/mainPage03.png`)" alt=""><h1>배틀에서 승리하세요</h1></div>
          <div class="main-img-wrapp-child main-introduce"><img v-if="show4" :src="require(`@/assets/images/mainPage04.png`)" alt=""><h1>재미있는 효과들과 함께 하세요</h1></div>
          <div class="main-img-wrapp-child main-introduce"><img v-if="show5" :src="require(`@/assets/images/mainPage05.png`)" alt=""><h1>방청객이 되어서 배틀을 관전할 수도 있습니다</h1></div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
      return{
        show1: false,
        show2: false,
        show3: false,
        show4: false,
        show5: false,
        scrollPosition: null
      }
    },
    methods: {
      updateScroll(){
        this.scrollPosition = window.scrollY || document.documentElement.scrollTop;
        // console.log(this.scrollPosition)
             if (790 < this.scrollPosition && this.scrollPosition < 1580) {this.show1 = true} 
        else if (1580 < this.scrollPosition && this.scrollPosition < 2380) {this.show2 = true}
        else if (2380 < this.scrollPosition && this.scrollPosition < 3180) {this.show3 = true}
        else if (3180 < this.scrollPosition && this.scrollPosition < 3970) {this.show4 = true}
        else if (3980 < this.scrollPosition) {this.show5 = true}
      }
    },
    mounted(){
      document.addEventListener('scroll', this.updateScroll);
      
    }
}
</script>

<style>
/* 스크롤 로직 */
.fade-enter-from {
  opacity: 0;
  transform: translateY(150px);
}

.fade-enter-active {
  transition: all 1s ease;
}

.fade-enter-to {
  opacity: 1;
  transform: translateY(0px);
}
/* 스크롤 로직 */

.fadein{
  overflow: hidden;
  animation: fadein 1s ease-in-out;
}

@keyframes fadein{
  0% {
    opacity: 0;
    transform: translateY(150px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.main-img-wrapp-item > img {
  justify-content: center;
    width: 70%;
}
.main-img-wrapp-item > img {
  margin: 10%;
}
.main-img-wrapp-button {
  display: flex;
  justify-content: center;
}
.main-img-wrapp-button > img {
  width: 20%;
}
.mainpage-maincontainer {
  display: flex;
  
}
.main-img-wrapp {
  display: felx;
}
.main-img-wrapp-child {
  left: 0;
  width: 100%;
  height: 100vh;
  border: 1px solid red;
  padding: auto;
  text-align: center;
}
.main-img-wrapp-child > img {
  width: 50%;
}
.main-introduce {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
</style>


