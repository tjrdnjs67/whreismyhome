<template>
  <div>
    <b-row class="mb-1">
      <b-form>
        <b-form-group>
          <b-form-textarea
            id="content"
            v-model="comment"
            placeholder="댓글 내용 입력..."
            rows="3"
            cols="69"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>
        <b-button @click="registMemo">댓글 등록</b-button>
      </b-form>
    </b-row>
    <b-container v-if="memos && memos.length != 0" class="bv-example-row mt-3">
      <memo-list-item
        v-for="(memo, index) in memos"
        :key="index"
        :memo="memo"
      />
    </b-container>
  </div>
</template>

<script>
import MemoListItem from "@/components/memo/MemoListItem";
import { writeMemo } from "@/api/memo";
import { mapState, mapActions } from "vuex";

const memoStore = "memoStore";
const memberStore = "memberStore";

export default {
  name: "MemoList",
  components: {
    MemoListItem,
  },
  data() {
    return {
      articleno: 0,
      userid: "",
      comment: "",
    };
  },
  created() {
    this.articleno = this.$route.params.articleno;
    this.getListMemo(this.articleno);
  },
  computed: {
    ...mapState(memoStore, ["memos"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memoStore, ["getListMemo"]),
    memoList() {
      // this.getListMemo(params);
    },
    registMemo() {
      if (this.comment == "") {
        alert("댓글 내용을 입력해주세요");
      } else {
        let param = {
          userid: this.userInfo.userid,
          comment: this.comment,
          articleno: this.articleno,
        };

        console.log(param);
        writeMemo(
          param,
          ({ data }) => {
            console.log(data);
            let msg = "등록 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "댓글 등록이 완료되었습니다.";
              console.log("바보짓");
              this.getListMemo(this.articleno);
            }
            alert(msg);
            this.comment = "";
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

<style></style>
