<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="content-group" label="댓글:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="comment.content"
            placeholder="댓글 내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'">댓글작성</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else>수정</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { writeComment, modifyComment, getComment } from "@/api/board";

export default {
  name: "BoardCommentItem",
  data() {
    return {
      comment: {
        commentno: 0,
        userid: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      let param = this.$route.params.commentno;
      getComment(
        param,
        ({ data }) => {
          // this.comment.commentno = data.comment.commentno;
          // this.comment.userid = data.comment.userid;
          // this.comment.subject = data.comment.subject;
          // this.comment.content = data.comment.content;
          this.comment = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      err && !this.comment.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registComment() : this.modifyComment();
    },
    onReset(event) {
      event.preventDefault();
      this.comment.commentno = 0;
      this.comment.content = "";
      this.moveList();
    },
    registComment() {
      let param = {
        userid: this.comment.userid,
        content: this.comment.content,
      };
      writeComment(
        param,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyComment() {
      let param = {
        commentno: this.comment.commentno,
        userid: this.comment.userid,
        content: this.comment.content,
      };
      modifyComment(
        param,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>

<style></style>
