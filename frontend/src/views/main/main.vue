<template>
  <el-container class="main-wrapper">
    <main-header v-if="visible" class="main-header-class" />
      <el-container class="main-container">

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container>
</template>
<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';
  .main-header-class {
  position: fixed;
  color: white;
}
  .el-main::-webkit-scrollbar{width: 0px;}
</style>
<script>
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter
  },
  data () {
    return {

      visible: true
    }
  },
  methods: {

    zeroLocation() {
      const topPosition = window.scrollY || document.documentElement.scrollTop;
      // console.log(topPosition, 'top')
      if (topPosition < 790) {
        this.visible = false
      } else {
        this.visible = true
      }

    }
  },
  mounted() {
    this.visible = false
    document.addEventListener('scroll', this.zeroLocation);
    // console.log('dlrjsi?', this.$router.currentRoute['_rawValue'].fullPath)
    const mainpageRouterName = this.$router.currentRoute['_rawValue'].fullPath
    console.log(this.$store.state.headerVisible)

  }
}
</script>

