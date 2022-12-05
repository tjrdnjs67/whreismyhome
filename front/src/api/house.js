import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

function houseList(params, success, fail) {
  api.get(`/map`, { params: params }).then(success).catch(fail);
}

function house(params, success, fail) {
  api.get(`/map/apt`, { params: params }).then(success).catch(fail);
}

function houseSearch(params, success, fail) {
  console.log(params, "param search mini");
  api.get(`/map/search`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, dongList, houseList, houseSearch, house };
