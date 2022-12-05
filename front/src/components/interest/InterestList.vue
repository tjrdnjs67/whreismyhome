<template>
  <b-container v-if="interests && interests.length != 0" class="bv-example-row mt-3">
    <interest-list-item v-for="(interest, index) in interests" :key="index" :interest="interest" />
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-button @click="interestList">새로 고침</b-button>
      <!-- <b-col @load="getInterestList" /> -->
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import InterestListItem from "@/components/interest/InterestListItem";
import { mapState, mapActions }from "vuex";

const interestStore = "interestStore";
const memberStore = "memberStore";

export default {
  name: "InterestList",
  components: {
    InterestListItem,
  },
  data() {
    return {};
  },
  created() {
    this.getInterestList(this.userInfo.userid);
  },
  computed: {
    ...mapState(interestStore, ["interests"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(interestStore, ["getInterestList"]),
    interestList() {
        this.getInterestList(this.userInfo.userid);
    },
  },
};
</script>

<style></style>
