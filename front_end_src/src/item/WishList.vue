<template >
  <v-container style="max-width: 900px">
    <v-col>
     <br><h1>Wish List</h1><br><hr>
   </v-col>

   <template v-for="(one, i) in wishdata">
   <v-col :key="i" cols="12">
     <v-footer color="#E0F2F1" height="auto">
       <v-row>
         <v-col cols="3">
           <v-img
           :src="'http://minback.com/upimg/'+one.iimage"
           height="130"
           contain
         ></v-img>
       </v-col>
       <v-col cols="3">
         <div>
             <br>
         <h4 class="subtitle mb-0 text-md-center"><b>{{one.iname}}</b></h4><br>
         <h4 class="subheading mb-0 text-md-center" style="color:gray;">{{one.icate}}</h4><br>
         <h4 class="title mb-0 text-md-right" style="color:gray;">출판사 : {{one.ipublisher}}</h4>
          </div>
       </v-col>
       <v-col cols="3">
         <div><br>
         <h4>평점</h4>
         <v-card-actions>
                <v-rating
                      :value="one.iratingavg"
                      color="#FF9100"
                      dense
                      half-increments
                      readonly
                      size="22"
                    ></v-rating>({{one.iratingavg}})
                </v-card-actions>
                <br>
                <h4>현재 재고 : {{one.icount}}</h4>
          </div>
       </v-col>
       <v-col cols="3"><br>
         <v-btn outlined rounded large color="#E53935" @click="goodsmove(one.icode)">이동하기</v-btn>
         <v-btn outlined rounded large color="#6200EA" @click="goodsDelete(one.icode)">삭제하기</v-btn>
       </v-col>
       </v-row>
     </v-footer><v-divider></v-divider>
   </v-col>
   </template>
   <br><hr><br>
  </v-container>
</template>

<script>
export default {
  data(){
    return{
      wishdata:[],
    }
  },
  methods:{
    refreshdata: function(){
      var params = new URLSearchParams();
      params.append("id", this.$cookie.get("uid"));
      axios.post("/item/wishlist",params).then(response => {
        this.wishdata = response.data;
        console.log(this.wishdata);
      });
    },
    goodsmove: function(icode){
      this.$router.push("/item/itemdetail/" + icode);
    },
    goodsDelete: function(icode){
      var params = new URLSearchParams();
      params.append("icode", icode);
      params.append("id", this.$cookie.get('uid'));
      axios.post("/item/wishdel", params).then(response => {
        if(response.data == "1"){
          alert("상품 삭제 완료");
          this.refreshdata();
        }
        else{
          alert("상품 삭제 실패");
        }
      });
    },
  },
  mounted(){
      this.refreshdata();
  },
}
</script>

<style lang="css">
</style>
