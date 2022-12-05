<template>
  <div class="map_wrap">
    <div id="map"></div>
    <ul id="category">
      <li id="BK9" data-order="0">
        <img class="is" src="@/img/bank.png" />
        은행
      </li>
      <li id="CS2" data-order="1">
        <img class="is" src="@/img/shops.png" />
        편의점
      </li>
      <li id="HP8" data-order="2">
        <img class="is" src="@/img/hospital-building.png" />
        병원
      </li>
      <li id="SC4" data-order="3">
        <img class="is" src="@/img/school.png" />
        학교
      </li>
    </ul>
  </div>
  <!-- <div id="map"></div> -->
</template>

<script>
import { EventBus } from "@/api/lib/eventBus";
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseMap",

  data() {
    return {
      map: null,
      ps: null,
      infowindow: null,
      clusterer: null,
      contentNode: null,
      bankMakers: [],
      hospitalMakers: [],
      conveniMakers: [],
      schoolMakers: [],
      aptMarkers: [],
    };
  },

  created() {
    EventBus.$on("eventbus", (house) => {
      console.log(house, "event house 정보");
      this.map.setLevel(1);
      this.map.setCenter(new kakao.maps.LatLng(Number(house.lat), Number(house.lng)));
    });
  },

  mounted() {
    const script = document.createElement("script");

    /* global kakao */
    script.onload = () => kakao.maps.load(this.initMap);
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=71d7f0b8360f0ef9bbf4b2986885b53a&libraries=services,clusterer`;
    document.head.appendChild(script);
  },

  computed: {
    ...mapState(houseStore, ["houses"]),
  },
  methods: {
    initMap() {
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      this.currCategory = "";
      this.markers = [];
      this.contentNode = document.createElement("div");

      const mapContainer = document.getElementById("map"); // 지도를 표시할 div
      const mapOption = {
        center: new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심좌표
        level: 12, // 지도의 확대 레벨
      };

      // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
      this.map = new kakao.maps.Map(mapContainer, mapOption);

      this.ps = new kakao.maps.services.Places(this.map);
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", kakao.maps.event.preventMap);

      //this.searchPlaces();
      // 커스텀 오버레이 컨텐츠를 설정합니다
      console.log(this.placeOverlay, "tico");
      console.log(this.contentNode, "contentNode mini");
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();
    },

    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      if (!this.currCategory) {
        return;
      }

      // 지도에 표시되고 있는 마커를 제거합니다
      //this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        console.log(data, "data mini");
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },

    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);
        const displayPlaceInfo = this.displayPlaceInfo;
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    //마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      var imageSrc = ""; // 마커 이미지 url, 스프라이트 이미지를 씁니다

      if (order === "0") {
        // 은행
        imageSrc = require("@/img/bank.png");
      } else if (order === "1") {
        // 편의점
        imageSrc = require("@/img/shops.png");
      } else if (order === "2") {
        // 병원
        imageSrc = require("@/img/hospital-building.png");
      } else if (order === "3") {
        // 학교
        imageSrc = require(`@/img/school.png`);
      }

      var imageSize = new kakao.maps.Size(25, 25), // 마커 이미지의 크기
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다

      if (order === "0") {
        // 은행
        this.bankMakers.push(marker); // 배열에 생성된 마커를 추가합니다
      } else if (order === "1") {
        // 편의점
        this.conveniMakers.push(marker); // 배열에 생성된 마커를 추가합니다
      } else if (order === "2") {
        // 병원
        this.hospitalMakers.push(marker); // 배열에 생성된 마커를 추가합니다
      } else if (order === "3") {
        // 학교
        this.schoolMakers.push(marker); // 배열에 생성된 마커를 추가합니다
      }
      return marker;
    },

    removeMarker(id) {
      console.log(id, "mini removeid");
      var i;

      if (id === "BK9") {
        for (i = 0; i < this.bankMakers.length; i++) {
          this.bankMakers[i].setMap(null);
        }
        this.bankMakers = [];
      } else if (id === "CS2") {
        for (i = 0; i < this.conveniMakers.length; i++) {
          this.conveniMakers[i].setMap(null);
        }
        this.conveniMakers = [];
      } else if (id === "HP8") {
        for (i = 0; i < this.hospitalMakers.length; i++) {
          this.hospitalMakers[i].setMap(null);
        }
        this.hospitalMakers = [];
      } else if (id === "SC4") {
        console.log("들어옴?");
        for (i = 0; i < this.schoolMakers.length; i++) {
          this.schoolMakers[i].setMap(null);
        }
        this.schoolMakers = [];
      }
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다

    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
      }

      content += '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      console.log(this.contentNode, "contentNode innerHTML mini");
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },

    // aptCode
    // aptName
    // buildYear
    // dongCode
    // dongName
    // gugunName
    // img
    // jibun
    // lat
    // lng
    // recentPrice
    // sidoName

    moveHouseDetail() {
      console.log("moveHouseDetail 들어옴");
      this.$route.push("housedetail", this.house);
    },
    displayAptPlaceInfo(house, position) {
      var address = `${house.sidoName} ${house.gugunName} ${house.dongName} ${house.jibun}`;

      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        house.aptCode +
        '" title="' +
        house.aptName +
        '">' +
        house.aptName +
        "</a>";
      if (house.aptName) {
        content +=
          '    <span title="' +
          house.aptName +
          '">' +
          house.aptName +
          "</span>" +
          '  <span class="jibun" title="' +
          address +
          '">(지번 : ' +
          address +
          ")</span>";
      } else {
        content += '    <span title="' + address + '">' + address + "</span>";
      }
      content += '    <span class="tel">' + house.buildYear + "</span>" + "</div>" + '<div class="after"></div>';
      this.contentNode.innerHTML = content;
      console.log(this.contentNode, "contentNode innerHTML mini");
      this.placeOverlay.setPosition(position);
      this.placeOverlay.setMap(this.map);
    },

    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      var category = document.getElementById("category"),
        children = category.children;

      console.log(children, "children mini");

      for (var i = 0; i < children.length; i++) {
        console.log(children[i], "children mini");
        children[i].onclick = this.onClickCategory;
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(object) {
      let obj = object.target;
      let id = obj.id;
      let className = obj.className;

      this.placeOverlay.setMap(null);

      if (id === "") {
        obj = obj.parentNode;
        id = obj.getAttribute("id");
        className = obj.className;
      }

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass(obj);
        this.removeMarker(id);
      } else {
        this.currCategory = id;
        this.changeCategoryClass(obj);
        this.searchPlaces();
      }
    },

    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      if (el.className === "on") {
        el.className = "";
      } else {
        el.className = "on";
      }
    },

    setCenter() {
      // 이동할 위도 경도 위치를 생성합니다
      if (this.houses.length !== 0) {
        const map = this.map;
        const geocoder = new kakao.maps.services.Geocoder();

        const address = `${this.houses[0].sidoName} ${this.houses[0].gugunName} ${this.houses[0].dongName}`;
        console.log(address);
        geocoder.addressSearch(address, function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
          }
        });
        console.log("이동 성공");
        this.setAptMarkers();
      } else {
        console.log("이동 실패");
        this.setAptMarkers();
      }
    },

    setAptMarkers() {
      if (this.clusterer !== null) {
        this.clusterer.clear();
      }
      this.removeAptMarker();

      const map = this.map;

      if (this.houses.length > 0) {
        this.houses.forEach((house) => {
          const position = new kakao.maps.LatLng(Number(house.lat), Number(house.lng));

          var imageSrc = require("@/img/apartments.png");
          var imageSize = new kakao.maps.Size(25, 25), // 마커 이미지의 크기
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

          var marker = new kakao.maps.Marker({
            position: position,
            image: markerImage,
          });

          const displayAptPlaceInfo = this.displayAptPlaceInfo;
          // 마커와 검색결과 항목을 클릭 했을 때
          // 장소정보를 표출하도록 클릭 이벤트를 등록합니다

          (function (marker, house, position) {
            kakao.maps.event.addListener(marker, "click", function () {
              displayAptPlaceInfo(house, position);
            });
          })(marker, house, position);

          this.aptMarkers.push(marker);
        });

        this.clusterer = new kakao.maps.MarkerClusterer({
          map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
          averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
          minLevel: 10, // 클러스터 할 최소 지도 레벨
        });

        this.clusterer.addMarkers(this.aptMarkers);
      }
    },

    removeAptMarker() {
      for (var i = 0; i < this.aptMarkers.length; i++) {
        this.aptMarkers[i].setMap(null);
      }
      this.aptMarkers = [];
    },
  },

  watch: {
    houses: "setCenter",
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 83vh;
  position: relative;
  overflow: hidden;
}

.is {
  width: 35px;
  height: 35px;
}

#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: rgb(255, 255, 255, 0.6);
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: rgb(255, 230, 230);
}

#category li:hover {
  background: rgb(255, 230, 230);
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
</style>
