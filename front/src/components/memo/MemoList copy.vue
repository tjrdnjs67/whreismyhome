<template>
  <div>

  <b-container v-if="memos && memos.length != 0" class="bv-example-row mt-3">
    <memo-list-item v-for="(memo, index) in memos" :key="index" :memo="memo" />
  </b-container>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-button @click="memoList">댓글 공개</b-button>
      <!-- <b-col @load="getMemoList" /> -->
      <b-col><b-alert show>댓글 비공개 상태</b-alert></b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
import MemoListItem from "@/components/memo/MemoListItem";
import { mapState, mapActions }from "vuex";

const memoStore = "memoStore";
const memberStore = "memberStore";

export default {
  name: "MemoList",
  components: {
    MemoListItem,
  },
  data() {
    return {};
  },
  created() {
    let param = this.$route.params.articleno;
    this.articleno = param;
  },
  computed: {
    ...mapState(memoStore, ["memos"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memoStore, ["getListMemo"]),
      memoList() {
        this.getListMemo(this.articleno);
    },
  },
};
</script>

<style></style>
