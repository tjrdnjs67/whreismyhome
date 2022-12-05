import { apiInstance } from "./index.js";

const api = apiInstance();

function writeMemo(memo, success, fail) {
  api.post(`/memo`, JSON.stringify(memo)).then(success).catch(fail);
}

function listMemo(param, success, fail) {
  api.get(`/memo`, { params: param }).then(success).catch(fail);
}

function getNav(param, success, fail) {
  api.get(`/memo/nav`, { params: param }).then(success).catch(fail);
}

function navigationMemo(memo, success, fail) {
  api.get(`/memo/nav`, JSON.stringify(memo)).then(success).catch(fail);
}

function modifyMemo(memo, success, fail) {
  api.put(`/memo`, JSON.stringify(memo)).then(success).catch(fail);
}

// function getMemo(memono, success, fail) {
//   api.get(`/memo/${memono}`).then(success).catch(fail);
// }

function deleteMemo(memono, success, fail) {
  console.log("memo.js deleteMemo, " + memono);
  api.delete(`/memo/${memono}`).then(success).catch(fail);
}

export { writeMemo, listMemo, getNav, navigationMemo, modifyMemo, deleteMemo };
