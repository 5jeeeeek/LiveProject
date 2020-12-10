<template>
<v-container>
  <br><v-row>
    <v-col>
     <h1>Admin Page</h1>
    </v-col>
   </v-row><br>

   <v-row>
     <v-col>
       <h3>{{nick}}님 도서관 관리페이지에 오신 것을 환영합니다.</h3>
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

        <v-col cols="4">
          <v-card
            color="#9E9D24"
            dark
          >
            <v-card-title class="headline">
              회원 관리
            </v-card-title>

            <v-card-subtitle>도서관 회원들을 관리할 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/admin/useradmin')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col cols="4">
          <v-card
            color="#000000"
            dark
          >
            <v-card-title class="headline">
              블랙리스트
            </v-card-title>

            <v-card-subtitle>블랙리스트에 등록된 회원들을 관리할 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/admin/blacklist')">
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col cols="4">
          <v-card
            color="#E65100"
            dark
          >
            <v-card-title class="headline">
              상품 관리
            </v-card-title>

            <v-card-subtitle>도서관 상품들을 관리할 수 있는 공간</v-card-subtitle>

            <v-card-actions>
              <v-btn outlined large @click="$router.push('/admin/itemadmin')">
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
