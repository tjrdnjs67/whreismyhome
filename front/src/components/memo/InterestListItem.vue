<template>
  <b-row
    class="m-2"
    @click="selectInterest"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail src="https://picsum.photos/250/250/?image=58" alt="Image 1"></b-img>
    </b-col>
    <b-col cols="10" class="align-self-center"> {{ interest.aptName }} 아파트<br />  최근 거래액: {{ interest.recentPrice }} 원 </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const interestStore = "interestStore";

export default {
  name: "InterestListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    interest: Object,
  },
  methods: {
    ...mapActions(interestStore, ["detailInterest"]),
    selectInterest() {
      // console.log("listRow : ", this.house);
      // this.$store.dispatch("getHouse", this.house);
      this.detailInterest(this.interest);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightblue;
}
</style>
