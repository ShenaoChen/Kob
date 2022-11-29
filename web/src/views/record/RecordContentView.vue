<template>
    <PlayGround />
    <div class="result-board-btn" v-if="$store.state.record.btn_flag === true">
        <button @click="restart" type="button" class="btn btn-warning btn-lg"> 返回 </button>
    </div>
</template>

<script>
import PlayGround from '@/components/PlayGround.vue'
import { useStore } from 'vuex'
import router from '@/router';

export default {
    components: {
        PlayGround,
    },
    setup() {
        const store = useStore();

        const restart = () => {
            store.commit("updateBtnFlag", false);
            router.push({
                name: "record_index",
            })
        }
        return {
            restart,
        }
    }
}
</script>

<style scoped>
div.result-board-btn{
    text-align: center;
}
</style>