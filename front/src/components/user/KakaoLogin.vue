<template>
  <div>
    <div alt="kakao login">
      <img alt="kakao logo" src="@/assets/kakao_login_large_wide.png" width="300" @click="login()" />
    </div>
  </div>
</template>

<script>
export default {
  name: "KakaoLogin",

  data() {
    return {
      CLIENT_ID: "dd8bcfb2279bb7b354363765736c5718",
      REDIRECT_URI: "http://localhost:8080/user/kakaologin",
    };
  },

  mounted() {},

  methods: {
    login() {
      let url = `https://kauth.kakao.com/oauth/authorize?client_id=${this.CLIENT_ID}&redirect_uri=${this.REDIRECT_URI}&response_type=code`;
      window.location.href = url;
    },
  },
};
</script>

<style lang="scss" scoped></style>