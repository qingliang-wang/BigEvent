<!-- <script>

export default {
  data() {
    return {
      msg: '伤害'
    }
  },
}

</script> -->

<script setup>
import {ref} from 'vue'
const msg = ref('沛县');
// 导入api.vue
import api from './api.vue';

</script>

<template>

  <!-- HTML -->

  <p1> {{msg}}</p1>
  <api/>
</template>
