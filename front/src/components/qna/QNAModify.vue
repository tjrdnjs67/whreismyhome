<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글수정</h3></b-alert>
      </b-col>
    </b-row>
    <q-n-a-input-item type="modify" />
  </b-container>
</template>

<script>
import QNAInputItem from "@/components/qna/item/QNAInputItem";

export default {
  name: "QNAModify",
  components: {
    QNAInputItem,
  },
};
</script>

<style></style>
