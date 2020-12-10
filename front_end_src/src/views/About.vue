<template>
  <div class="about">
    <h1>This is an about page</h1>
    <v-btn color="blue darken-1" @click="test()">테스트</v-btn>
    <v-btn color="blue darken-1" @click="test2()">위시리스트 테스트</v-btn>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  data(){
    return{
      
    }
  },
  methods: {
    test: function(){
      var params = new URLSearchParams();
      params.append("nick", "길효민");
      axios.post("/users/nickcheck", params).then(response => {
          var result = response.data;
          if (result == 1) {
            alert("사용가능한 닉네임입니다.");
            console.log(result);
          } else {
            alert("이미 사용중인 닉네임입니다.");
            console.log(result);
          }
        });
    },
    test2: function(){
      var params = new URLSearchParams();
      params.append("id", "test");
      axios.post("/item/wishlist", params).then(response => {
          console.log(response.data);
          console.log(response.data.length);
        });
    },

  },
}
</script>