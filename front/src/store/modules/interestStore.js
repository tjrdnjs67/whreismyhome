import { interestList } from "@/api/interest.js";

const interestStore = {
  namespaced: true,
  state: {
    interests: [],
    interest: null,
  },
  getters: {},
  mutations: {
    CLEAR_INTEREST_LIST(state) {
      state.interests = [];
      state.interest = null;
    },
    SET_INTEREST_LIST(state, interests) {
      state.interests = interests;
    },
    SET_DETAIL_INTEREST(state, interest) {
      state.interest = interest;
    },
  },
  actions: {
    getInterestList: ({ commit }, userID) => {
      const params = { userid: userID };
      interestList(
        params,
        ({ data }) => {
          commit("SET_INTEREST_LIST", data);
          // commit("SET_HOUSE_LIST", data.response.body.items.item);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailInterest: ({ commit }, interest) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_INTEREST", interest);
    },
  },
};

export default interestStore;
