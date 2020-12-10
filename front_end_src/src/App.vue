<template>
  <div id="app">
    <div>
    <v-toolbar
      dark
      src="https://cdn.vuetifyjs.com/images/backgrounds/vbanner.jpg"
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title @click="$router.push('/')"
      style="cursor:pointer">Live Project</v-toolbar-title>
      
      <v-spacer></v-spacer>
  
        <v-text-field
        class="mx-4"
        flat
        hide-details
        label="Search"
        prepend-inner-icon="mdi-magnify"
        solo-inverted
      ></v-text-field>

      <v-btn @click="$router.push('/user/login')" v-if="!loginState & !adminState"
      outlined large>Login</v-btn>

      <v-btn @click="userLogout()" v-if="loginState || adminState" outlined large>
        Logout</v-btn>

      <v-btn @click="$router.push('/user/join')"
        v-if="!loginState & !adminState" outlined large>
        Join</v-btn>

        <v-btn @click="$router.push('/user/mypage')" v-if="loginState" outlined large>
        MyPage</v-btn>

        <v-btn @click="$router.push('/admin/adminpage')" v-if="adminState" outlined large>
        AdminPage</v-btn>


      <template v-slot:extension>
        <v-tabs
        align-with-title>
          <v-tab @click="$router.push('/')">
            메인화면
          </v-tab>
          <v-tab @click="$router.push('/board/notice')">
            <v-icon>mdi-account-box</v-icon>
            공지사항
            </v-tab>
          <v-tab @click="$router.push('/user/seat')">
            <v-icon>mdi-phone</v-icon>
            좌석현황
          </v-tab>
          <v-tab @click="$router.push('/board/board')">
            <v-icon>mdi-heart</v-icon>
            커뮤니티
            </v-tab>
          <v-tab @click="$router.push('/item/itemlist')">
            <v-icon>mdi-account-box</v-icon>
            도서정보
            </v-tab>
        </v-tabs>
      </template>

      <!-- <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn> -->

    </v-toolbar>
  </div>
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div> -->
    <router-view/>


  <v-footer
    dark
    padless
  >
    <v-card
      flat
      tile
      class="indigo lighten-1 white--text text-center"
    >
      <v-card-text>
        <v-btn
          v-for="icon in icons"
          :key="icon"
          class="mx-4 white--text"
          icon
        >
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
      </v-card-text>

      <v-card-text class="white--text pt-0">
        2020년 한경대학교 컴퓨터공학과 SC트랙 4학년 졸업작품
      </v-card-text>

      <v-card-text class="white--text pt-0">
        대학교의 대표적 공공시설인 도서관통합시스템을 IoT와 결합하여 하드웨어와 소프트웨어 모두를 구현하여 만든 시스템입니다. 프론트엔드는 Web은 Vue.js를 사용, Mobile은 Android를 사용하였습니다. 데이터베이스와 요청과 응답을 실행하는 Back-End는 Node.js의 대표적인 프로그램인 Express를 사용하여 Front-End와 Http 통신을 이용하여 데이터를 주고받는 형식으로 시스템이 설계되었습니다. 도서관 자리마다 센서를 이용하여 사용자들에게 실시간 시스템을 제공합니다. 부가적으로 사용자들이 이용할 수 있는 커뮤니티 공간을 만들었으며, 도서관에 있는 모든 도서 및 여러 정보들을 제공하는 기능을 구현하였습니다. 이 프로젝트의 가장 궁극적인 의의는 학생들에게 정확한 정보전달과 현재의 상황의 정보를 빠르게 전달하여 학생들에게 크고 작은 도움이 되어 질 높은 대학 생활을 누릴 수 있게끔 하는데 가장 큰 목표를 둔 프로젝트입니다.
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="white--text">
        {{ new Date().getFullYear() }} — <strong>Live Project Team</strong>
      </v-card-text>
    </v-card>
  </v-footer>

  </div>

  

</template>


<script>
  export default {
    data: () => ({
      items: [
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me 2' },
      ],
      drawer: "",
      icons: [
        'mdi-facebook',
        'mdi-twitter',
        'mdi-linkedin',
        'mdi-instagram',
      ],
      loginState:false,
      adminState:false,
    }),
    mounted(){
      this.usercookie();
    },
    methods:{
      usercookie: function(){
        var memberNick = this.$cookie.get('uid');
        var admini = this.$cookie.get('admin');
        if(admini == 1){
          this.adminState = true;
        }
        else{
          this.adminState = false;
          if(memberNick){
            this.loginState = true;
          }
          else{
            this.loginState = false;
          }
        }
      },
      userLogout: function(){
        this.$cookie.delete('memberNick');
        this.$cookie.delete('uid');
        this.$cookie.delete('admin');
        this.loginState = false;
        this.adminState = false;
        alert("로그아웃이 정상적으로 되었습니다.");
        location.href = "/";
      }
    }
  }
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
