<script setup>
// 声明响应式数据
import { ref ,onMounted } from 'vue';
const count  = ref(0);
// 在组合式api 中需要将数据设置成响应式数据

function increment(){
    count.value++;
}

// 声明钩子函数
onMounted(()=>{
    console.log("vue 已经挂载完毕了！");
})
</script>

<template>

    <button @click="increment"> count{{ count }}</button>

</template>