import { createRouter, createWebHistory } from 'vue-router'
import list from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import SignUp from '@/views/user/sign-up'
import Login from '@/views/user/login'


import mainPage from '@/views/main-page/main-page'
import Participate from '@/views/participate/participating-code'
import participatingPage from '@/views/participate/participating-page'
import waitingPage from '@/views/participate/waiting-page'
import signUpEmail from '@/views/user/email'
import gameSetPage from '@/views/debate-hosting/game-set-page'
import makeRoom from '@/views/debate-hosting/make-room'

// const routerComponent = require('@/views/main/router-components.json')

import myPage from '@/views/user/mypage'
import tempKey from '@/views/user/tempKey'
import checkPwd from '@/views/user/checkPwd'
import myPageInfo from '@/views/user/mypage-info'

const fullMenu = require('@/views/main/menu.json')
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else { // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)

  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail
  },
  {path: '/signUp',
  name: 'sign-up',
  component: SignUp},
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    name: 'main-page',
    component: mainPage
  },
  {
    path: '/list',
    name: 'list',
    component: list
  },
  {
    path: '/myPage',
    name: 'myPage',
    component : myPage
  },

  {
  path : '/myPage/info',
  name : 'myPage-info',
  component : myPageInfo,
  beforeEnter: (to, from, next) => {
    if (to.query[tempKey]) {
      next();
    } else {
      next('/checkPwd');
    }
  },
},
{
  path : '/checkPwd',
  name : 'checkPwd',
  component : checkPwd,
},
{
  path: '/participatingPage',
  name: 'participating-page',
  component: participatingPage
},
{
  path: '/startWithCode',
  name: 'Participate',
  component: Participate
},
{
  path: '/waitingPage',
  name: 'waiting-page',
  component: waitingPage
},
{
  path : '/makeRoomPage',
  name: 'make-room',
  component: makeRoom
},
{
  path : '/email',
  name : 'email',
  component : signUpEmail,
}
  )

  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

// router.afterEach((to) => {
//   console.log('이거냐?', to.fullPath, $state.headerVisible)

// })

export default router
