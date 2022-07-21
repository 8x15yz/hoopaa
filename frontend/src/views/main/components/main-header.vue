<template>
  <el-row class="main-header" :gutter="10">

    <div class="hide-on-small">

        <router-link to="/mainPage">
          <div class="logo-wrapper"><div class="ic ic-logo"/></div>
        </router-link>

        <el-menu @select="menuSelect">
          <router-link style=text-decoration:none; to="/">
            <span class="el-menu-show-all">모든 토론보기</span>
          </router-link>
          <span class="el-menu-show-all">미칭 시작하기</span>
          <span class="el-menu-show-all">미팅 참여하기</span>
        </el-menu>

        <div class="tool-wrapper">
            <router-link style=text-decoration:none; to="/login"><span class="tool-wrapper-span-login">Log in</span></router-link>
            <router-link style=text-decoration:none; to="/signUp"><span class="tool-wrapper-span-signup">Sign up</span></router-link>
        </div>

    </div>

    <div class="hide-on-big">
      <div class="menu-icon-wrapper" @click="changeCollapse"><i class="el-icon-menu"></i></div>
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="menu-icon-wrapper"><i class="el-icon-search"></i></div>
      <div class="mobile-sidebar-wrapper" v-if="!state.isCollapse">
        <div class="mobile-sidebar">
          <div class="mobile-sidebar-tool-wrapper">
            <div class="logo-wrapper"><div class="ic ic-logo"/></div>
            <el-button type="primary" class="mobile-sidebar-btn login-btn" @click="clickLogin">로그인</el-button>
            <el-button class="mobile-sidebar-btn register-btn">회원가입</el-button>
          </div>
        </div>
        <div class="mobile-sidebar-backdrop" @click="changeCollapse"></div>
      </div>
    </div>

  </el-row>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    height: {
      type: String,
      default: '70px'
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      searchValue: null,
      isCollapse: true,
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.title = MenuItems[keys[i]].name
          menuArray.push(menuObject)
        }
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex'])
    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[param]
      })
      console.log(keys[param])
    }

    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse
    }

    return { state, menuSelect, changeCollapse }
  }
}
</script>
<style>

  /*Mobile, Tablet*/
  .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .main-header .hide-on-big .logo-wrapper {
    display: inline-block;
    margin: 0 calc(50% - 51px)
  }
  .main-header .hide-on-big .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }
  .mobile-sidebar-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar {
    width: 240px; height: calc(100vh - 1px);
    display: inline-block;
    background-color: white;
    padding: 0 10px;
    vertical-align: top;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-tool-wrapper {
    padding-bottom: 20px;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn.login-btn {
    color: white;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper {
    display: block
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    margin: 0 auto;
    margin-top: 30px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }
  .mobile-sidebar-wrapper .mobile-sidebar-backdrop {
    width: calc(100% - 260px); height: calc(100vh - 1px);
    background-color: black;
    display: inline-block;
    opacity: 0.3;
  }
  .mobile-sidebar-wrapper{
    margin-top: 0;
    padding-left: 0;
    height: calc(100% - 235px);
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item {
    cursor: pointer;
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item .ic {
    margin-right: 5px;
  }
  .el-menu {
    list-style : none;
    padding: 0px;
    display: flex;
  }



  /*Desktop - Need to add Class if Need*/
  .main-header .hide-on-small {
    display: flex;
    padding: 5px 10px;
    justify-content: space-evenly;
  }
  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/favi6.png');
  }

  .el-menu {
    margin-right: 550px;
  }
  .el-menu-item > span {
    outline: solid 1px #9747ff;
    border-radius: 5px;
    background-color: none;
    color: #9747ff;
    padding: 8px;
    cursor: pointer;
    /* font-weight: bold; */
  }
  .el-menu-show-all {
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 8px;
    cursor: pointer;
  }
  .el-menu-show-all:hover, .tool-wrapper-span-login:hover {
    font-weight: bold;
  }
  .el-menu-item > span:hover {
    outline: solid 1px #9747ff;
    border-radius: 5px;
    background-color: #9747ff ;
    color: white;
    padding: 8px;
  }
  .tool-wrapper {
    padding: 15px;
    justify-content: end;
  }
  .tool-wrapper-span-signup {
    outline: solid 1px #1bb061;
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 10px;
    cursor: pointer;
    margin: 5px;
    font-weight: bold;
  }
  .tool-wrapper-span-signup:hover {
    outline: solid 1px #1bb061;
    border-radius: 5px;
    background-color: #1bb061 ;
    color: white;
  }
  .tool-wrapper-span-login {
    border-radius: 5px;
    background-color: none;
    color: #1bb061;
    padding: 10px;
    cursor: pointer;
    margin: 5px;
  }



</style>
