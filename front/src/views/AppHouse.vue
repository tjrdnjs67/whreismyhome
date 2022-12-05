<template>
  <div class="bv-example-row mt-3 text-center">
    <h3 class="underline-orange"><b-icon icon="house-fill"></b-icon> House Service</h3>
    <div class="house">
      <div class="house--map"><house-map /></div>
      <div class="house--list">
        <b-row>
          <b-col>
            <house-search-bar />
          </b-col>
        </b-row>
            <house-list />
      </div>
    </div>
  </div>
</template>

<script>
import HouseMap from "@/components/house/HouseMap.vue";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
// import HouseDetail from "@/components/house/HouseDetail.vue";

export default {
  name: "AppHouse",
  components: {
    HouseSearchBar,
    HouseList,
    // HouseDetail,
    HouseMap,
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 149, 27, 0.3) 30%);
}

.house {
  display: flex;
  justify-content: space-between;
}

.house--map {
  width: 60%;
  height: 100ch;
  padding-right: 10px;
}

.house--list {
  width: 40%;
  height: 100ch;
}
</style>
