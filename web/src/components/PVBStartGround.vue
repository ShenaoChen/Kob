<template>
    <div class="matchground">
        <div class="row">
            <div class="col-12">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                     {{ $store.state.user.username }}
                </div>
                <div class="user-select-bot">
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自出马</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">
                            {{ bot.title }}
                        </option>
                    </select>
                </div>
            </div>
            
            <div class="col-12" style="text-align: center; padding-top: 12vh;">
                <button @click="click_match_btn" type="button" :class="'btn '+match_btn_type+' btn-lg'">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>
 
<script>
import { ref } from 'vue'
import { useStore } from 'vuex';
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始游戏");
        let match_btn_type = ref("btn-warning");
        let bots = ref([]);
        let select_bot = ref("-1");

        const click_match_btn = () => {
            if (match_btn_info.value === "开始游戏") {
                match_btn_info.value = "取消";
                match_btn_type.value = "btn-secondary";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start_matching", 
                    bot_id: select_bot.value,
                    is_pvp: 0,
                }))
            } else {
                match_btn_info.value = "开始游戏";
                match_btn_type.value = "btn-warning";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop_matching", 
                }))
            }
        }

        const refresh_bots = () => {
            $.ajax({
                url: "https://app4121.acapp.acwing.com.cn/api/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp
                }
            })  
        }
        
        refresh_bots();

        return {
            match_btn_info,
            match_btn_type,
            click_match_btn,
            bots,
            select_bot,
        }
    }
}
</script>

<style scoped>
div.matchground {
    width:40vw;
    height:70vh;
    margin:  40px auto;
    background-color: rgb(50, 50, 50, 50%);
}
div.user-photo {
    text-align: center;
    padding-top: 10vh;
}
div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
}
div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.user-select-bot {
    padding-top: 2vh;
    width: 15vw;
    margin: 0 auto;
}
</style>