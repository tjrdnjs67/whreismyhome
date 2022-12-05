import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
// module import
import boardStore from "@/store/modules/boardStore";
import houseStore from "@/store/modules/houseStore";
import memberStore from "@/store/modules/memberStore";
import qnaStore from "@/store/modules/qnaStore";
import todoStore from "@/store/modules/todoStore";
import interestStore from "@/store/modules/interestStore";
import memoStore from "@/store/modules/memoStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    boardStore,
    houseStore,
    memberStore,
    qnaStore,
    todoStore,
    interestStore,
    memoStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
