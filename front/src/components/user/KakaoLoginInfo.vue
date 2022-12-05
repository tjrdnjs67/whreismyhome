<template>
  <div>카카오 유저 확인 중..</div>
</template>

<script>
import http from "@/api/http";
import { mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "KakaoLoginInfo",

  data() {
    return {
      code: "",
      token: {
        ACCESS_TOKEN: null,
        REFRESH_TOKEN: null,
      },
      form: {
        email: null,
        password: null,
        presence: null,
      },
    };
  },

  mounted() {
    // 인가코드
    this.code = this.$route.query.code;
    console.log("인가코드 : " + this.code);
    this.getToken();
  },

  methods: {
    ...mapActions(memberStore, ["loginSuccess", "tokenSuccess"]),
    async getToken() {
      // 인가코드 이용해서 Access Token 받기
      await http
        .get(`/member/token/${this.code}`)
        .then(({ data }) => {
          this.token.ACCESS_TOKEN = data.ACCESS_TOKEN;
          this.token.REFRESH_TOKEN = data.REFRESH_TOKEN;
          // console.log("ac " + this.token.ACCESS_TOKEN);
          // console.log("re " + this.token.REFRESH_TOKEN);
        })
        .then(() => {
          // 사용자 정보 가져오기
          this.getUserInfo();
        });
    },
    getUserInfo() {
      // Access Token 이용해서 사용자 정보 가져오기
      http
        .get(`/member/info/${this.token.ACCESS_TOKEN}`)
        .then(({ data }) => {
          this.form.email = data.email;
          this.form.password = data.id;
          this.form.presence = data.presence;
          // console.log(this.form);
        })
        .then(() => {
          // 회원가입 안했다면
          if (this.form.presence === "false") {
            // 회원가입으로 이동
            this.moveJoin();
          } else {
            // 로그인 시켜주기
            alert("로그인에 성공했습니다!");
            // console.log(this.token);
            this.loginSuccess(this.form);
            this.tokenSuccess(this.token);
            // 메인으로 이동
            this.moveMain();
          }
        });
    },
    moveJoin() {
      this.$router.push({ name: "join", params: { email: this.form.email } });
    },
    moveMain() {
      this.$router.push({ name: "main", params: { email: this.form.email } });
    },
  },
  computed: {
    // ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
};
</script>

<style lang="scss" scoped></style>