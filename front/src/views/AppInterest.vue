<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-orange"><b-icon icon="house-fill"></b-icon> Interest Service</h3>
    <b-row>
      <b-col>
        <b-img alt="Happy House" :src="require('@/assets/happyhouse.png')" />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <interest-list />
      </b-col>
      <b-col cols="6">
        <house-detail />
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import InterestList from "@/components/interest/InterestList.vue";
import HouseDetail from "@/components/house/HouseDetail"
import {mapState} from "vuex";

const houseStore = "houseStore";

export default {
  name: "AppInterest",
  components: {
    InterestList,
    HouseDetail,
  },
  computed:{
    ...mapState(houseStore, ["house"]),
  }
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 149, 27, 0.3) 30%);
}
</style>
