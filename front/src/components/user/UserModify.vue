<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원 정보 수정</h3></b-alert>
        <b-alert variant="secondary" show><h5>아이디: {{userInfo.userid}} (변경 불가!)</h5></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <!-- <b-alert show variant="danger" v-if="isLoginError">아이디 또는 비밀번호를 확인하세요.</b-alert> -->
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                required
                placeholder="이름 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                type="email"
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button type="button" variant="success" class="m-1" @click="editUser()">수정</b-button>
            <!-- <b-button type="button" variant="primary" class="m-1" @click="confirm">로그인 페이지로 이동</b-button> -->
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { modifyUser } from "@/api/member";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserModify",
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  data(){
    return {
      user:{
        userid : "",
        username: "",
        userpwd: "",
        email: "",
      },
    };
  },
  methods: {
    // onReset(event) {
    //   event.preventDefault();
    //   this.user.username= "",
    //   this.user.userid= "",
    //   this.user.userpwd= "",
    //   this.user.email= "",
    //   this.moveList();
    // },
    editUser() {
      // let param;
      // if(this.user.username===""){
      //   param = {
      //     userid: this.userInfo.userid,
      //     email: this.user.email,
      //     userpwd: this.user.userpwd,
      //     username: this.userInfo.username,
      //   };
      //     console.log("null");
      //     console.log(param);
      // }else{
      //   param = {
      //     userid: this.userInfo.userid,
      //   email: this.user.email,
      //   userpwd: this.user.userpwd,
      //   username: this.user.username,
      // };
      //   console.log("null 아님");
      //   console.log(param);
      // }

      let param={
        userid: this.userInfo.userid,
        email: this.user.email,
        userpwd: this.user.userpwd,
        username: this.user.username,
      };

      // console.log("파라미터터어어엉"+param);
      modifyUser(
        param,
        ({ data }) => {
          
        console.log(param);
          console.log(data);
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data.message === "success") {
            msg = "회원 정보 수정이 완료되었습니다.";
          }
          alert(msg);
          // this.moveList();
          this.movePage();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // modifyUser() {
    //   let param = {
    //     userid: this.user.userid,
    //   };
    //   modifyUser(
    //     param,
    //     ({ data }) => {
    //       let msg = "수정 처리시 문제가 발생했습니다.";
    //       if (data === "success") {
    //         msg = "수정이 완료되었습니다.";
    //       }
    //       alert(msg);
    //       // 현재 route를 /list로 변경.
    //       this.moveList();
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
    // },
    // moveList() {
    //   this.$router.push({ name: "boardlist" });
    // },
    movePage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>


<style></style>
