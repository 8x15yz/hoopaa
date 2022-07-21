<template>
    <div>
        <h1>참여 코드를 받았나요?</h1>
            <input type="text">
            <router-link to="/participatingPage"><button>submit</button></router-link>
            
    </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {

}

</script>
