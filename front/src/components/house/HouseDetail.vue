<template>
  <b-container v-if="house" class="bv-example-row">
    <b-row>
      <b-col
        ><h3>{{ house.aptName }} 아파트</h3>
        <b-iconstack font-scale="5" @click="interestChange()">
          <b-icon stacked icon="square"></b-icon>
          <b-icon stacked icon="check" v-if="this.isInterest"></b-icon>
        </b-iconstack>
      </b-col>
    </b-row>
    <b-row class="mb-2 mt-1">
      <b-col><b-img :src="require('@/assets/apt.png')" fluid-grow width="100" height="50"></b-img></b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="secondary">일련번호 : {{ house.aptCode }}</b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="info"
          >주소 : {{ house.sidoName }} {{ house.gugunName }}
          {{ house.dongName }}
        </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="warning">최근 거래 금액 : {{ house.recentPrice }} 원</b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="danger">
          {{ house.buildYear }}년에 건설
          <!-- 거래금액 : {{ (parseInt(house.거래금액.replace(",", "")) * 10000) | price }}원 -->
        </b-alert>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { interestRegister, interestDelete } from "@/api/interest";
import { mapActions, mapState } from "vuex";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "HouseDetail",
  data() {
    return {
      isInterest: false,
    };
  },
  created() {
    this.getApt(this.$route.params.aptcode);
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    ...mapState(memberStore, ["userInfo"]),
    // house() {
    //   return this.$store.state.house;
    // },
  },
  methods: {
    ...mapActions(houseStore, ["getApt"]),
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

  // filters: {
  //   price(value) {
  //     if (!value) return value;
  //     return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  //   },
  // },
};
</script>

<style></style>
