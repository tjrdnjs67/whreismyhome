import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function registerUser(user, success, fail) {
  api.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

async function idCheck(userid, success, fail) {
  await api.get(`/user/idcheck/${userid}`).then(success).catch(fail);
}

async function modifyUser(user, success, fail) {
  console.log("modify 호출");
  api.put(`/user`, user).then(success).catch(fail);
}

async function deleteUser(userid, success, fail) {
  console.log(userid);
  await api.delete(`/user`, {params: {"userid": userid}}).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export { login, registerUser, idCheck, modifyUser, deleteUser, findById, tokenRegeneration, logout };
