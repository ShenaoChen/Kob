export default {
    state: {
        btn_flag: false,
        is_record: false,
        a_steps: "",
        b_steps: "",
        record_loser: "",
    },
    getters: {
    },
    mutations: {
        updateBtnFlag(state, btn_flag) {
            state.btn_flag = btn_flag;
        },
        updateIsRecord(state, is_record) {
            state.is_record = is_record;
        },
        updateSteps(state, data) {
            state.a_steps = data.a_steps;
            state.b_steps = data.b_steps;
        },
        updateRecordLoser(state, loser) {
            state.record_loser = loser;
        }
    },
    actions: { 
    },
    modules: {
    }
}