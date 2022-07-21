<template>
  <search></search>
  <hr/>
   <el-container class="main-container">


   <el-aside class="category" width="240px" height="400px">
        <side
          :width="`240px`"/>
      </el-aside>
<div class="list">
  <div>
  <span>카테고리</span>
  <button>정렬조건</button>
  <el-checkbox>모집중만 보기</el-checkbox>
  </div>
  <ul class="infinite-list"  style="overflow:auto">
    <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
      <conference />
    </li>
  </ul>
  <hr/>
  <ul class="infinite-list"  style="overflow:auto">
    <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
      <conference />
    </li>
  </ul>
</div>

  </el-container>
</template>
<style scopped>
ul {
  display: flex;
  flex-direction: column;
}
.el-main {
  width: 100%;
}
.main-container {
  display: flex;
  flex-direction: row;
  height: 100%;
}
.infinite-list {
  display: flex;
  flex-direction: row;
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1069px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}
</style>
<script>
import search from '@/views/common/search'
import Conference from './components/conference'
import side from '@/views/main/components/main-sidebar'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Home',

  components: {
    Conference,
    search,
    side
  },

  setup () {
    const router = useRouter()

    const state = reactive({
      count: 5
    })

    const load = function () {
      state.count += 4
    }

    const clickConference = function (id) {
      router.push({
        name: 'conference-detail',
        params: {
          conferenceId: id
        }
      })
    }

    return { state, load, clickConference }
  }
}
</script>
