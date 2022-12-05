import { apiInstance } from "./index.js";

const api = apiInstance();

async function interestRegister(params, success, fail) {
  api.post(`/interest`, JSON.stringify(params)).then(success).catch(fail);
}

async function interestDelete(params, success, fail) {
  console.log("들어옴 delete");
  api.delete(`/interest`, { params: params }).then(success).catch(fail);
}

async function interestList(params, success, fail) {
  api.get(`/interest`, { params: params }).then(success).catch(fail);
}

export { interestRegister, interestDelete, interestList };
