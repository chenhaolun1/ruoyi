<template>
<el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="(img,index) in imgList" :key="index">
        <img :src="img.url" width="100%" height="100%">
        
      </el-carousel-item>
    </el-carousel>
</template>



<script>
  export default {
    data() {
      return {
        imgList:[
     {
        url:require('@/data/image1.jpeg') //url: '../assets/lake.jpg'      
     },
     {
        url:require('@/data/image2.jpeg') //url: '../assets/lake.jpg'      
     },
     {
        url:require('@/data/image3.jpeg') //url: '../assets/lake.jpg'      
     }
    ]
    }
    }
  }
</script>

<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>