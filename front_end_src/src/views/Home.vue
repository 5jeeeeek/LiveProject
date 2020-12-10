<template>
  <div>
    <v-container><br><br>
  
      <img alt="Vue logo" 
        height="250"
        contain
      src="../assets/liverary_icon.png">


      <v-col>
        <br><h1>Live Project 구성원</h1><br><br><hr><br>
      </v-col>
    
    <v-row dense>
        <!-- <v-col cols="4">
          <v-card
            color="#385F73"
            dark
            height="300px"
          >
            <v-card-title class="headline">
              Unlimited music now
            </v-card-title>

            <v-card-subtitle>Listen to your favorite artists and albums whenever and wherever, online and offline.</v-card-subtitle>

            <v-card-actions>
              <v-btn text>
                Listen Now
              </v-btn>
            </v-card-actions>

            <v-avatar
                class="ma-3"
                size="125"
                tile
              >
                <v-img src="../assets/hyomin1.jpg"></v-img>
              </v-avatar>
          </v-card>
        </v-col> -->

        <v-col
          v-for="(item, i) in items"
          :key="i"
          cols="4"
        >
          <v-card
            :color="item.color"
            dark
            height="340px"
          >
            <div class="d-flex flex-no-wrap justify-space-between">
              <div>
                <h2>
                <v-col
                  v-text="item.title"
                ></v-col></h2><hr>
                
                <v-card-subtitle v-text="item.artist"></v-card-subtitle>

                <v-card-text class="headline font-weight-bold">
                        한경대학교 컴퓨터공학과 sc트랙 4학년
                </v-card-text>
                

              </div>

              <v-avatar
                class="ma-3"
                size="125"
                tile
              >
                <v-img :src="item.src"></v-img>
              </v-avatar>

              <v-col>
                  <v-btn
                    class="ml-2 mt-5"
                    outlined
                    rounded
                    large
                    @click="open(item.code)"
                  >
                    <b>더보기</b>
                  </v-btn>
                </v-col>

            </div>
          </v-card>
        </v-col>
      </v-row>
      <br><br><hr><br><br>
    </v-container>

    <div data-app>
    <v-dialog v-model="dialog" persistent max-width="800px">
     <v-toolbar color="#673AB7" dark>
          <v-toolbar-title><h2>Note</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="dialog = false">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
        <v-card-title class="headline">
            * 이름 : {{data.title}}
        </v-card-title>

       <v-card-title class="headline">
         * 전공 : {{data.class}}
       </v-card-title>

       <v-card-title class="headline">
         * 역할 : {{data.artist}}
       </v-card-title>

       <v-card-title class="headline">
         * 사용언어 : {{data.position}}
       </v-card-title>

       <v-card-title class="headline">
         * 학년 : {{data.classnum}}
       </v-card-title>

       <v-card-title class="headline">
         * 학번 : {{data.classnumber}}
       </v-card-title>

       <v-card-title class="headline">
         * 연락처 : {{data.phone}}
       </v-card-title>

       <v-card-title class="headline">
         * 이메일 : {{data.email}}
       </v-card-title>

       <v-card-title class="headline">
         * 목표 : {{data.text}}
       </v-card-title>

       <v-card-title class="headline">
         * 입학여부 : {{data.state}}
       </v-card-title>

       <v-img
              height="600"
              contain
              :src="data.src"
            ></v-img>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded dark color="#673AB7" @click="dialog = false">닫기</v-btn>
       </v-card-actions>
       </v-col>
     </v-card>
   </v-dialog>
   </div>

  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  // name: 'Home',
  // components: {
  //   HelloWorld
  // },
  data () {
      return {
        dialog: false,
        data:[],
        items: [
          {
          code: 1,
          color: '#385F73',
          src: '../hyomin1.jpg',
          title: '길효민(팀원)',
          artist: 'WEB 개발 - Vue.js & Node.js',
          email:"bigjoy22@naver.com",
          phone:"010-5063-4559",
          classnumber:"2019250063",
          class:"한경대학교 컴퓨터공학과 sc트랙",
          classnum:"4학년",
          state:"편입생",
          text:"풀스택 개발자가 목표이며, WEB 개발자가 장래희망",
          position:"Mysql & JavaScript - Back-end, Front-end"
        },
        {
          code:2,
          color: '#1F7087',
          src: '../ck.jpg',
          title: '주철기(팀장)',
          artist: 'App 개발 - Android Studio',
          email:"ck2711@naver.com",
          phone:"010-4909-0712",
          classnumber:"2019250072",
          class:"한경대학교 컴퓨터공학과 sc트랙",
          classnum:"4학년",
          state:"편입생",
          text:"모바일 개발자가 목표이며, 실시간 서비스 개발자가 장래희망",
          position:"Android JAVA, XML, MySQL"
        },
        {
          code:3,
          color: '#952175',
          src: '../ty.png',
          title: '김태양(팀원)',
          artist: 'App 개발 - Android Studio',
          email:"5jeeeeek@naver.com",
          phone:"010-3444-6116",
          classnumber:"2019250068",
          class:"한경대학교 컴퓨터공학과 sc트랙",
          classnum:"4학년",
          state:"편입생",
          text:"머신러닝 & 딥러닝와 같은 AI 개발자가 장래희망",
          position:"Android JAVA, XML, MySQL"
        },
      ],
      }
    },
    methods:{
      open(code){
          if(code == 1){
            this.data = this.items[0];
            this.dialog = true;
          }
          else if(code == 2){
            this.data = this.items[1];
            this.dialog = true;
          }
          else{
            this.data = this.items[2];
            this.dialog = true;
          }
          console.log(this.data);
      },
      // hexToBytes: function() {
      //   var str = "Hello123";
      //   var bytes = []; // char codes

      //   for (var i = 0; i < str.length; ++i) {
      //     var code = str.charCodeAt(i);
      //     bytes = bytes.concat([code]);
      //   }

      //   // 72, 101, 108, 108, 111, 31452
      //   console.log('bytes', bytes.join(', '));
      //   console.log(bytes);
      // }
    }
}
</script>
