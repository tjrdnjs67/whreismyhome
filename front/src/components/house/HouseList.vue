<template>
  <div v-if="houses && houses.length != 0">
    <house-list-item v-for="(house, index) in aptList" :key="index" :house="house" />

    <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"></b-pagination>
  </div>
</template>

<script>
import HouseListItem from "@/components/house/HouseListItem";
//import { getHeapStatistics } from "v8";
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseList",
  components: {
    HouseListItem,
  },
  data() {
    return {
      perPage: 6,
      currentPage: 1,
    };
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
    rows() {
      return this.houses.length;
    },
  },

  methods: {},

  watch: {
    houses: function () {
      this.aptList = [];

      const items = this.houses;

      for (let i = 0; i < this.perPage; i++) {
        if (i >= this.houses.length) break;

        this.aptList.push(items[i]);
      }
    },

    currentPage: function () {
      this.aptList = [];

      const items = this.houses;
      // Return just page of items needed

      for (let i = (this.currentPage - 1) * this.perPage; i < this.currentPage * this.perPage; i++) {
        if (i >= this.houses.length) break;

        this.aptList.push(items[i]);
      }
    },
  },
};
</script>

<style scoped></style>
