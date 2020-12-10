<template>
<v-container>
  <br><v-row>
    <v-col>
     <h1>My Page</h1>
    </v-col>
   </v-row><br>

   <v-row>
     <v-col>
       <h3>{{nick}}님 자신의 공간에 오신 것을 환영합니다.</h3>
     </v-col>
   </v-row><br><hr><br>

   <v-row
      class="fill-height"
      align="center"
      justify="center">
      <v-col cols="3">
          <v-card
            color="#00C853"
            dark
          >
            <v-card-title class="headline">
              회원정보수정
            </v-card-title>

            <v-card-subtitle>자신의 회원정보 열람과 개인정보 수정을 할 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/user/myinfo')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col cols="3">
          <v-card
            color="#0091EA"
            dark
          >
            <v-card-title class="headline">
              나의 작성 게시물
            </v-card-title>

            <v-card-subtitle>커뮤니티 공간에서 내가 작성한 글들을 모아서 한 번에 볼 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/board/myboard')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col cols="3">
          <v-card
            color="#952175"
            dark
          >
            <v-card-title class="headline">
              위시리스트
            </v-card-title>

            <v-card-subtitle>수 많은 도서관 상품들 중 내가 찜한 상품들만 볼 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/item/wishlist')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col cols="3">
          <v-card
            color="#651FFF"
            dark
          >
            <v-card-title class="headline">
              쪽지함
            </v-card-title>

            <v-card-subtitle>웹사이트 및 도서관 관리자들과 직접적으로 연락을 주고 받을 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/user/note')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>


    </v-row><br><hr><br>

</v-container>
</template>


<script>

export default {
  data: function(){
    return{
      nick: "",
    };
  },
  methods :{
    // openTab: function(url){
    //   var win = window.open(url, '_blank');
    //   win.focus();
    // },
    userOut: function(){
      var result = confirm("정말 회원탈퇴를 하시겠습니까?");
      if(result){
         var params = new URLSearchParams();
         params.append("id", this.$cookie.get("uid"));
         axios.post("/users/userout", params).then(response =>{
           var result = response.data;
           if(result == 1){
             this.$cookie.delete('memberNick');
             this.$cookie.delete('uid');
             this.loginState = false;
             this.nick = null;
             alert("저희 사이트를 이용해주셔셔 감사합니다. 회원탈퇴가 완료되었습니다.");
             location.href = "/";
           }
           else{
             alert("회원탈퇴가 실패했습니다.");
           }
         });
      };
    },
    nickset(){
        this.nick = this.$cookie.get('memberNick');
    },
    onOpenLink: function(one){
      window.location.href = one.link;
    },
  },
  mounted(){
      this.nickset();
  },

  computed: {

  },
  components: {  }
};
</script>

<style scoped>
.v-card {
  transition: opacity .4s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.6;
 }

.show-btns {
  color: rgba(255, 255, 255, 1) !important;
}
</style>
