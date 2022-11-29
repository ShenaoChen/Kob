import { createStore } from 'vuex'
import MoudleUser from './user'
import MoudlePk from './pk'
import MoudleRecord from './record'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: MoudleUser,
    pk: MoudlePk,
    record: MoudleRecord,
  }
})
