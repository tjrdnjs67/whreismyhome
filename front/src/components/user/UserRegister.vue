<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
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
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력...."
              >
              </b-form-input>
              <b-button @click="idCheckButtonPress()">아이디 중복검사</b-button>
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
            <b-form-group label="비밀번호 확인:" label-for="userpwdconfirm">
              <b-form-input
                type="password"
                id="userpwdconfirm"
                v-model="user.userpwdconfirm"
                required
                placeholder="비밀번호 확인"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="registUser()"
              >회원가입</b-button
            >
            <!-- <b-button type="button" variant="primary" class="m-1" @click="confirm">로그인 페이지로 이동</b-button> -->
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { registerUser, idCheck } from "@/api/member";

export default {
  name: "UserRegister",
  data() {
    return {
      isRegistable: false,
      user: {
        username: "",
        userid: "",
        userpwd: "",
        email: "",
      },
    };
  },
  props: {
    type: {},
  },
  methods: {
    idCheckButtonPress() {
      idCheck(
        this.user.userid,
        ({ data }) => {
          console.log(data);
          let msg = "이미 사용 중인 아이디입니다.";
          this.isRegistable = false;
          if (data.message === "success") {
            msg = "사용 가능한 아이디입니다.";
            this.isRegistable = true;
          }
          alert(msg);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    onReset(event) {
      event.preventDefault();
      (this.user.username = ""),
        (this.user.userid = ""),
        (this.user.userpwd = ""),
        (this.user.email = ""),
        this.moveList();
    },
    registUser() {
      if (this.isRegistable) {
        let param = {
          username: this.user.username,
          userid: this.user.userid,
          userpwd: this.user.userpwd,
          email: this.user.email,
        };

        console.log(param);
        registerUser(
          param,
          ({ data }) => {
            console.log(data);
            let msg = "등록 처리시 문제가 발생했습니다.";
            if (data.message === "success") {
              msg = "회원등록이 완료되었습니다.";
            }
            alert(msg);
            // this.moveList();
            this.movePage();
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        alert("아이디를 확인해주세요");
      }
    },
    movePage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>


<style></style>
