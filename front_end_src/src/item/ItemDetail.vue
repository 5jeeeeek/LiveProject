<template>
  <v-container style="max-width: 1000px">
    <br>
    <v-col>
     <h1>도서 정보</h1>
   </v-col><br><hr><br><br>
   <v-card>
    <v-row>
        <v-col cols="6">
            <v-img
            :src="'http://minback.com/upimg/'+itemlist.iimage"
            height="400"
            contain
        ></v-img>
        </v-col>
        <v-col cols="6">
            <div>
            <br><h2 class="headline mb-0 text-md-center">{{itemlist.iname}}</h2><br><br>
                <h3 class="text-md-center">{{itemlist.icate}}</h3><br><br>
                <h3 class="text-md-center">출판사 : {{itemlist.ipublisher}}</h3><br><br>
                <h3 class="text-md-center">현재 재고 수량 : {{itemlist.icount}}개</h3><br>
                <v-card-actions>
                <v-rating
                      :value="itemlist.iratingavg"
                      color="#FF9100"
                      dense
                      half-increments
                      readonly
                      size="22"
                    ></v-rating>({{itemlist.iratingavg}}) / {{itemlist.ireview}}개
                </v-card-actions>
                <v-card-actions>
                <v-divider></v-divider>
                <br><br>
                  </v-card-actions>
           <div class="text-xs-center">
               <v-btn rounded large outlined color="#304FFE" dark
              @click="itemmove()"><b>상품 목록</b></v-btn>
              
              <v-btn rounded large outlined color="#E91E63" dark v-if="wishck == false"
              @click="wishlist(itemlist.icode)"><b>위시리스트 추가</b></v-btn>

              <v-btn rounded large outlined color="#424242" dark v-if="wishck == true"
              @click="wishlist(itemlist.icode)"><b>위시리스트 삭제</b></v-btn>
          </div>
      </div>
    </v-col>
      </v-row>

      <br><hr><br><br>


      <v-row>
        <v-col>
            <h2>상품 소개 내용</h2><br><br>
        </v-col>
      </v-row>

      <v-row>
          <v-col>
              <h3>{{itemlist.itext}}</h3><br><br>
          </v-col>
      </v-row>
      </v-card><br><br>

      <v-row>
      <v-col cols="8">
        <v-textarea
          label="리뷰 작성란"
          v-model="qrptext"
          outlined
        ></v-textarea>
        <v-spacer></v-spacer>
      </v-col>
      <v-col cols="4">
          <br>
        <v-rating
          v-model="rating"
          color="#FF9100"
          background-color="#BDBDBD"
          empty-icon="$ratingFull"
          half-increments
          hover
          large
        ></v-rating><br>
          <v-btn large dark color="#EF6C00" @click="onAddText()">등록</v-btn>
      </v-col>
    </v-row>

    <v-row align="center">
    <template v-for="(one,i) in reviews">
      <v-col :key="i" cols="12">
          <v-card class="mx-auto" color="#FAFAFA">
              <v-card-actions>
                <v-avatar color="#EF6C00">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
              <v-card-title class="title  text-md-left">{{one.rname}}</v-card-title>
              <v-rating
                      :value="one.rrating"
                      color="#FF9100"
                      dense
                      half-increments
                      readonly
                      size="18"
                    ></v-rating>({{one.rrating}})
              <v-spacer></v-spacer>
              <span>작성날짜 : {{one.rdate}}</span></v-card-actions>
                <v-card-actions>
                <v-card-text>
                  {{one.rtext}}
                </v-card-text>
                <v-spacer></v-spacer>
            <v-btn dark color="#EF6C00" @click="DelComment(one.rcode, one.id)">삭제</v-btn><br></v-card-actions>
          </v-card>

        </v-col>
     </template>
   </v-row>

    </v-container>
</template>


<script>
import axios from 'axios'
export default {
  data: () => ({
    reviews: [], //댓글 저장소
    rating:null, //리뷰 별점 값
    itemlist:[],
    qrptext:"",
    wishck:false,
  }),
  methods: {
      itemmove(){
          this.$router.push("/item/itemlist");
      },
    wick(){
        var p = new URLSearchParams();
        p.append("id", this.$cookie.get('uid'));
        p.append("icode", this.$route.params.icode);
        axios.post("/item/wishcheck", p).then(response => {
            if(response.data == '1'){
                this.wishck = true;
            }
            else if(response.data == '0'){
                this.wishck = false;
            }
        });
    },
    wishlist: function(code){ //구매자 위시리스트에 상품 추가하기
      var nick = this.$cookie.get('memberNick');
      if(nick == null){
        alert("로그인 해주세요.");
      }
      else if(nick != null){
        var params = new URLSearchParams();
        params.append("icode", code);
        params.append("id", this.$cookie.get('uid'));
        axios.post("/item/wishlistadd", params).then(response => {
            console.log(response.data);
          if(response.data == '1'){
            alert("위시리스트 추가가 완료되었습니다.");
            this.wick();
          }
          else if(response.data == '0'){
            alert("위시리스트 삭제가 완료되었습니다.");
            this.wick();
          }
        });
      }
    },
    refresh: function(){ //현재 상품 상세보기 조회
      var params = new URLSearchParams();
      params.append("icode", this.$route.params.icode);
      axios.post("/item/itemview", params).then(response => {
        this.itemlist = response.data;
        console.log(this.itemlist);
      });
    },
    DelComment: function(recode, id){
      var uid = this.$cookie.get("uid");
      if(uid == id){
        var params = new URLSearchParams();
        params.append("rcode", recode);
        params.append("icode", this.$route.params.icode)
        axios.post("/item/reviewdel", params).then(response =>{
          if(response.data == '1'){
            alert("리뷰 삭제 완료");
            this.CmtUpdate();
          }
          else{
            alert("리뷰 삭제 실패");
          }
        });
        this.CmtUpdate();
      }
      else{
        alert("자신이 작성한 리뷰가 아닙니다.");
      }
    },
    CmtUpdate: function(){
      var params = new URLSearchParams();
      params.append("icode", this.$route.params.icode);
      axios.post("/item/reviewlist", params).then(response =>{
        this.reviews = response.data;
        console.log(response.data);
      });
    },
    onAddText: function() {
      var nick = this.$cookie.get("memberNick");
      if(nick != null){
        if(this.rating == null){
          alert("별점을 입력해주세요.");
        }
        else if(this.qrptext == ""){
          alert("리뷰를 작성해주세요.");
        }
        else{
          var d = new Date();
          var yy = d.getFullYear();
          var mm = d.getMonth() + 1;
          if(mm < 10){
            mm = "0" + String(mm);
          }
          var dd = d.getDate();
          if(dd < 10){
            dd = "0" + String(dd);
          }
          var hours = d.getHours(); // 시
          var minutes = d.getMinutes(); //분
          var bdate = String(yy) + "-" + mm + "-" + dd + " " + hours + ":" + minutes;
          var params = new URLSearchParams();
          params.append("rtext", this.qrptext);
          params.append("rname", nick);
          params.append("icode", this.$route.params.icode);
          params.append("rrating", this.rating);
          params.append("rdate", bdate);
          params.append("id", this.$cookie.get("uid"));
          axios.post("/item/reviewadd", params).then(response =>{
            console.log(response.data);
            if(response.data == '1'){
              alert("리뷰 등록 성공");
              this.qrptext = "";
              this.rating = null;
              this.CmtUpdate();
            }
            else{
              alert("리뷰 등록이 실패되었습니다.");
            }
          });
        }
      }
      else if(nick == "" || nick == null){
        alert("로그인 하세요.");
      }
    },
  },
  mounted() {
      this.refresh();
      this.CmtUpdate();
      this.wick();
  },
  components: {  }
}
</script>

<style lang="css">
</style>
