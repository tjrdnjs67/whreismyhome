<template>
  <div>
    <b-row class="mt-4 mb-4 text-center">
      <b-col class="sm-3">
        <b-form-input v-model="word" placeholder="아파트 이름" @keyup.enter="sendKeyword"></b-form-input>
      </b-col>
      <div align="left">
        <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
      </div>
      <b-col class="sm-3" align="left">
        <range-slider class="slider" min="1000" max="301000" step="1000" v-model="sliderValue" @change="rangeApt">
        </range-slider>
        <span v-if="Number(sliderValue) <= 300000">1000만원 ~ {{ sliderValue }}만원</span>
        <span v-else> 무제한</span>
      </b-col>
    </b-row>
    <b-row class="mt-4 mb-4 text-center">
      <b-col class="sm-3">
        <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select v-model="dongCode" :options="dongs" @change="searchApt"></b-form-select>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import RangeSlider from "vue-range-slider";
import "vue-range-slider/dist/vue-range-slider.css";

/*
  namespaced: true를 사용했기 때문에 선언해줍니다.
  index.js 에서 modules 객체의 '키' 이름입니다.

  modules: {
    키: 값
    memberStore: memberStore,
    houseStore: houseStore
  }  
*/
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      word: "",
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      sliderValue: 301000,
    };
  },
  components: {
    RangeSlider,
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.CLEAR_APT_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getSearchHouseList",
      "getHousePriceList",
    ]),
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_APT_LIST"]),
    // sidoList() {
    //   this.getSido();
    // },
    sendKeyword() {
      console.log("search 들어옴");
      this.sliderValue = 301000;
      if (this.word !== "") this.getSearchHouseList(this.word);
    },
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      if (this.dongCode) this.getHouseList(this.dongCode);
    },
    rangeApt() {
      this.getHousePriceList(this.sliderValue);
    },
  },
};
</script>

<style></style>
