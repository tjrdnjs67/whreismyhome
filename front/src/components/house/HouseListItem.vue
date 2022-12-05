<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }">
    <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail src="https://picsum.photos/250/250/?image=58" alt="Image 1"></b-img>
    </b-col>
    <b-col cols="10" class="align-self-center" @click="showDetail()">
      {{ house.aptName }} 아파트<br />
      최근 거래액: {{ house.recentPrice }} 만원
      <b-iconstack font-scale="2" @click="interestChange()">
        <b-icon stacked icon="square"></b-icon>
        <b-icon stacked icon="check" v-if="this.isInterest"></b-icon>
      </b-iconstack>
    </b-col>
    <b-col> </b-col>
  </b-row>
</template>

<script>
import { EventBus } from "@/api/lib/eventBus";
import { interestRegister, interestDelete } from "@/api/interest";
import { mapActions, mapState } from "vuex";

const memberStore = "memberStore";
const houseStore = "houseStore";
const interestStore = "interestStore";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
      isSelected: false,
      isInterest: false,
    };
  },
  created() {
    this.getInterestList(this.userInfo.userid);
    console.log(this.interests, "interests");
    this.checkIsInterest(this.interests);
  },
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(interestStore, ["interests"]),
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    ...mapActions(interestStore, ["getInterestList"]),
    selectHouse() {
      EventBus.$emit("eventbus", this.house);
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
            let msg = "관심 아파트 등록 실패";
            if (data === "success") {
              msg = "관심 아파트 등록 완료";
              this.getInterestList(this.userInfo.userid);
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
              this.getInterestList(this.userInfo.userid);
            }
            alert(msg);
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    showDetail() {
      this.isSelected = !this.isSelected;
    },
    colorChange(flag) {
      this.isColor = flag;
    },
    checkIsInterest(interests) {
      for (let i = 0; i < interests.length; i++) {
        if (this.house.aptCode === interests[i].aptCode) {
          console.log(interests[i], "interests issue");
          this.isInterest = true;
          break;
        }
      }
    },
  },

  watch: {},
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
