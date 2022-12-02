<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'" />
    <MatchGround v-if="$store.state.pk.status === 'matching'" />
    <ResultBoard v-if="$store.state.pk.loser != 'none'" />
    <div class="user-color-blue" v-if="parseInt($store.state.user.id) === parseInt($store.state.pk.a_id) && $store.state.pk.status === 'playing'">左下角</div>
    <div class="user-color-red" v-if="parseInt($store.state.user.id) === parseInt($store.state.pk.b_id) && $store.state.pk.status === 'playing'">右上角</div>
</template>

<script>
import PlayGround from '@/components/PlayGround.vue'
import MatchGround from '@/components/MatchGround.vue'
import ResultBoard from '@/components/ResultBoard.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'

export default {
    components: {
        PlayGround,
        MatchGround,
        ResultBoard,
    },
    setup() {
        const store = useStore();
        const socketUrl = `wss://app4121.acapp.acwing.com.cn/websocket/${store.state.user.token}/`;

        store.commit("updateLoser", "none");
        store.commit("updateIsRecord", false);

        let socket = null;
        onMounted(() => {
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png"
            })

            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (data.event === "start_matching") {
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateGame", data.game);
                        store.commit("updateStatus", "playing");
                    }, 1000);
                } else if (data.event === "move") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                } else if (data.event === "result") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    store.commit("updateLoser", data.loser);
                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                }
            }

            socket.onclose = () => {
                console.log("disconnected!");
                store.commit("updateStatus", "matching");
            }
        })

        onUnmounted(() => {
            socket.close();
        })
    }
}
</script>

<style scoped>
div.user-color-blue{
    text-align: center;
    color: blue;
    font-size: 30px;
    font-weight: 600;
}
div.user-color-red{
    text-align: center;
    color: red;
    font-size: 30px;
    font-weight: 600;
}
</style>