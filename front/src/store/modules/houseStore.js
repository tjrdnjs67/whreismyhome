import { houseSearch, sidoList, gugunList, dongList, houseList, house } from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시" }],
    guguns: [{ value: null, text: "구/군" }],
    dongs: [{ value: null, text: "동" }],
    houses: [],
    pricehouses: [],
    recentValue: null,
    house: null,
  },
  getters: {},
  mutations: {
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구/군" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_APT_LIST(state) {
      state.houses = [];
      state.pricehouses = [];
      state.recentValue = 301000;
      state.house = null;
    },
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_HOUSE_LIST(state, houses) {
      state.pricehouses = [];
      state.recentValue = 301000;

      let arr = houses;

      for (let i = arr.length - 1; i >= 0; i--) {
        if (arr[i].recentPrice === null) {
          arr.splice(i, 1);
        }
      }

      state.houses = arr;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },

    SET_PRICE_LIST(state, deal) {
      console.log(deal, "deal mini");
      console.log(state.recentValue, "recentValue mini");

      if (deal < state.recentValue) {
        let arr = state.houses;
        let pricearr = state.pricehouses;

        console.log(arr, "before arr mini");
        console.log(pricearr, "before pricearr mini");

        if (arr.length > 0) {
          for (let i = arr.length - 1; i >= 0; i--) {
            console.log(arr[i].recentPrice);
            let prices = arr[i].recentPrice.trim().split(",", 2);
            let price = prices[0].concat(prices[1]);

            console.log(price, "price mini");

            if (Number(price) > Number(deal)) {
              pricearr.push(arr.splice(i, 1)[0]);
            }
          }
        }

        console.log(arr, "after arr mini");
        console.log(pricearr, "after pricearr mini");
        state.pricehouses = pricearr;
        state.houses = arr;
      } else if (deal > state.recentValue) {
        let arr = state.houses;
        let pricearr = state.pricehouses;

        console.log(pricearr, "before pricearr mini");
        console.log(arr, "before arr mini");

        if (pricearr.length > 0) {
          for (let i = pricearr.length - 1; i >= 0; i--) {
            let prices = pricearr[i].recentPrice.trim().split(",", 2);
            let price = prices[0].concat(prices[1]);

            if (Number(price) <= Number(deal)) {
              arr.push(pricearr.splice(i, 1)[0]);
            }
          }
        }

        console.log(pricearr, "after pricearr mini");
        console.log(arr, "after arr mini");

        state.pricehouses = pricearr;
        state.houses = arr;
      }

      state.recentValue = deal;
    },
  },
  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = { sido: sidoCode };
      gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = { gugun: gugunCode };
      dongList(
        params,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, dongCode) => {
      const params = { dong: dongCode };
      houseList(
        params,
        ({ data }) => {
          commit("SET_HOUSE_LIST", data);
          // commit("SET_HOUSE_LIST", data.response.body.items.item);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getSearchHouseList: ({ commit }, word) => {
      const params = { word: word };
      console.log(params, "params mini");

      houseSearch(
        params,
        ({ data }) => {
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getApt: ({ commit }, aptcode) => {
      const params = { aptcode: aptcode };
      console.log(params, "getApt mini");

      house(
        params,
        ({ data }) => {
          commit("SET_DETAIL_HOUSE", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getHousePriceList: ({ commit }, deal) => {
      commit("SET_PRICE_LIST", deal);
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default houseStore;
