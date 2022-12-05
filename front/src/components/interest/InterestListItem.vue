<template>
  <b-row
    class="m-2"
    @click="selectInterest"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img
        thumbnail
        src="https://picsum.photos/250/250/?image=58"
        alt="Image 1"
      ></b-img>
    </b-col>
    <b-col cols="10" class="align-self-center">
      {{ interest.aptName }} 아파트<br />
      최근 거래액: {{ interest.recentPrice }} 원
    </b-col>
    <b-col>
      <b-iconstack font-scale="5" @click="interestChange()">
        <b-icon stacked icon="square"></b-icon>
        <b-icon
          stacked
          icon="check"
          v-if="this.isInterest"
        ></b-icon> </b-iconstack
    ></b-col>
  </b-row>
</template>

<script>
import { interestRegister, interestDelete } from "@/api/interest";
import { mapActions, mapState } from "vuex";

const houseStore = "houseStore";
const interestStore = "interestStore";
const memberStore = "memberStore";

export default {
  name: "InterestListItem",
  data() {
    return {
      isInterest: false,
      isColor: false,
    };
  },
  computed:{
    ...mapState(houseStore, ["house"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  props: {
    interest: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    ...mapActions(interestStore, ["detailInterest"]),
    selectInterest() {
      this.detailHouse(this.interest);
      // this.$store.dispatch("getHouse", this.house);
      this.detailInterest(this.interest);    
    },
    colorChange(flag) {
      this.isColor = flag;
    },
    interestChange() {
      this.isInterest = !this.isInterest;
      let params = {
        userid: this.userInfo.userid,
        aptcode: this.house.aptCode,
      };
      if (this.isInterest) {
        interestRegister(
          params,
          ({ data }) => {
            console.log("여기");
            console.log(data);
            let msg = "관심 아파트 등록 실패";
            if (data === "success") {
              msg = "관심 아파트 등록 완료";
            }
            alert(msg);
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        interestDelete(
          params,
          ({ data }) => {
            console.log(data);
            let msg = "관심 아파트 해제 실패";
            if (data === "success") {
              msg = "관심 아파트 해제 성공";
            }
            alert(msg);
          },
          (error) => {
            console.log(error);
          }
        );
      }
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
