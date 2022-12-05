<template>
  <b-row
    v-if="memo.articleno == this.articleno"
    class="m-2"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <!-- <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail src="https://picsum.photos/250/250/?image=58" alt="Image 1"></b-img>
    </b-col> -->
    <b-col cols="10" class="align-self-center">
      {{ memo.userid }}: {{ memo.comment }}
      <b-col class="text-right" v-if="userInfo.userid === memo.userid">
        <b-button
          variant="outline-info"
          size="sm"
          @click="editMemo"
          class="mr-2"
          >글수정
        </b-button>
        <b-button variant="outline-danger" size="sm" @click="removeMemo"
          >글삭제</b-button
        >
      </b-col>
      <b-col class="text-right" v-else-if="userInfo.usertype === 'a'">
        <b-button variant="outline-danger" size="sm" @click="removeMemo"
          >글삭제
        </b-button>
      </b-col>
    </b-col>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";
import { mapState } from "vuex";
import { deleteMemo, modifyMemo } from "@/api/memo";

const memberStore = "memberStore";

const memoStore = "memoStore";

export default {
  name: "MemoListItem",
  data() {
    return {
      articleno: 0,
      isColor: false,
    };
  },
  created() {
    this.articleno = this.$route.params.articleno;
  },
  props: {
    memo: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memoStore, ["memoDetail"]),
    // selectMemo() {
    //   // console.log("listRow : ", this.house);
    //   // this.$store.dispatch("getHouse", this.house);
    //   console.log("dd");
    //   this.memoDetail(this.memo);
    // },
    colorChange(flag) {
      this.isColor = flag;
    },
    editMemo() {
      modifyMemo;
    },
    removeMemo() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        deleteMemo(this.memo.memono);
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
