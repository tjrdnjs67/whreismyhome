import { listMemo, deleteMemo } from "@/api/memo.js";

const memoStore = {
  namespaced: true,
  state: {
    memos: [],
    memo: null,
  },
  getters: {},
  mutations: {
    CLEAR_LIST_MEMO(state) {
      state.memos = [];
      state.memo = null;
    },
    SET_LIST_MEMO(state, memos) {
      state.memos = memos;
    },
  },
  actions: {
    getListMemo: ({ commit }, articleNo) => {
      let params = { articleno: articleNo };
      listMemo(
        params,
        ({ data }) => {
          commit("SET_LIST_MEMO", data);
          // commit("SET_HOUSE_LIST", data.response.body.items.item);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    removeMemo: (memoNo)=>{
      deleteMemo(
        memoNo,
        ({data})=>{
          if (data.message === "success") {
            console.log("댓글 삭제 완료");
          } else {
            console.log("댓글 삭제 실패");
          }
        }
      );
    },
  },
};

export default memoStore;
