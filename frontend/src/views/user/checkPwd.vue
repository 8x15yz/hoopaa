<template>
<div>
<div class="check">
  <input placeholder="비밀번호 입력" v-model="checkPwd" />
  <button @click="clickPwd">확인</button>

</div>


</div>
</template>

<script>
import {ref} from 'vue'
import tempKey from './tempKey';
import { useRouter } from 'vue-router'
export default {
  name: 'checkPwd',
    setup(_, { root }) {
      const router = useRouter()
        const checkPwd = ref('');
        const clickPwd = () => {
            if (checkPwd.value === tempKey) {
                router.push({
                    path: '/myPage/info',
                    query: {
                        [tempKey] : true,
                    },
                })
            }
        };
        return {
            checkPwd,
            clickPwd,
        };
    },}
</script>

<style>

</style>
