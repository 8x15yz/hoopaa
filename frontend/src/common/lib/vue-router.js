import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import SignUp from '@/views/main/components/sign-up'
import Login from '@/views/main/components/login'
import Start from '@/views/start/start'
import Participate from '@/views/participate/participate'
import mainPage from '@/views/main-page/main-page'

const fullMenu = require('@/views/main/menu.json')
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else if (key === 'start') { // 미팅 시작하기
      return { path: fullMenu[key].path, name: key, component: Start }
    } else if (key === 'participate') { // 미팅 참여하기
      return { path: fullMenu[key].path, name: key, component: Participate }
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
  })
  routes.push(
    {path: '/signUp',
    name: 'sign-up',
    component: SignUp}
  )
  routes.push({
    path: '/login',
    name: 'Login',
    component: Login
  }
  )
  routes.push({
    path: '/',
    name: 'Home',
    component: Home
  })
  routes.push({
    path: '/mainPage',
    name: 'main-page',
    component: mainPage
  })
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
