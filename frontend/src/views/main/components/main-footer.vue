<template>
  <el-row
    class="main-footer"
    :gutter="10">
    <div class="contents">
      <img :src="require(`@/assets/images/071414.png`)" alt="">
    </div>
  </el-row>
</template>

<style>
.contents > img {
  width: 100px;
}
@media (max-width: 700px) {
  .main-footer {
    height: 190px;
  }
  .main-footer .contents {
    line-height: 190px;
    text-align: center;
    margin: 0 auto;
  }
}

@media (min-width: 701px) {
  .main-footer {
    height: 110px;
  }
  .main-footer .contents {
    line-height: 110px;
    text-align: center;
    margin: 0 auto;
  }
}
</style>
<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'main-footer',

  props: {
    height: {
      type: String,
      default: '110px'
    }
  },

  setup() {
    const state = reactive({})

    return { state }
  }
  
}
</script>
<style>
</style>
