<template>
  <v-container style="max-width: 1000px">
    <br>
   <v-col>
     <v-card>
         <v-card-title class="headline">
          {{data.ttitle}}
        </v-card-title>
       <v-card-text>
         <v-card-actions>
           <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
         <pre><div>  {{data.tname}}  </div></pre><v-divider vertical></v-divider>
         <pre><span>  {{data.tdate}}</span></pre>

         </v-card-actions>
       </v-card-text><v-divider></v-divider><br>
            <v-img
            v-if="data.timage != 0"
            height="700"
            contain
            :src="'http://minback.com/upimg/'+data.timage"
          ></v-img>
       <v-card-text>{{data.ttext}}
     </v-card-text><br>
     <v-card-actions v-if="adstate == true">
       <v-spacer></v-spacer>
       <v-btn rounded dark color="#0276f9" @click="BoardModify()">
         글 수정하기</v-btn><pre><div>  </div></pre>
       <v-btn rounded dark color="#0276f9" @click="BoardDelete()">
         글 삭제하기</v-btn>
     </v-card-actions>
     <v-divider></v-divider><br>
     <v-btn block dark color="#3591fa" @click="$router.push('/board/notice')">게시판 목록</v-btn>
     </v-card>
   </v-col>


    <div data-app>
    <v-dialog max-width="700px" v-model="dialog">
      <v-card>
        <v-col>
        <v-card-title primary-title>
          <span class="headline">{{data2.bnickname}}님 환영합니다.</span>
        </v-card-title><hr>
          <v-textarea
          label="글 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.ttitle"
        ></v-textarea>
         <v-textarea
          outlined
          label="글 내용"
          auto-grow
          v-model="data.ttext"
        ></v-textarea>
        <v-img
            v-if="data.timage != 0"
            height="700"
            contain
            :src="'http://minback.com/upimg/'+data.timage"
          ></v-img>
        <v-btn v-if="data.timage != 0" large @click="imgdel()">이미지 삭제하기</v-btn>
          <form v-if="data.timage == 0"> 
            <input type="file" id="ex_file" ref='uploadImageFile'
              @change="onFileSelected()" accept="image/*"> 
          </form>
          <v-btn v-if="uploadImageFile != ''" @click="onSave()">이미지 추가하기</v-btn>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn rounded @click="ModifyAdd()">수정완료</v-btn>
          <v-btn rounded @click="closeEvent()">닫기</v-btn>
        </v-card-actions>
        </v-col>
      </v-card>
    </v-dialog>
    </div>

  </v-container>
</template>

<script>

export default {
  data: function() {
    return {
      qrptext: "",
      id:"",
      qcode: "",
      data: [],
      comment: [],
      qrpcode:"",
      dialog: false,
      data2:[],
      commentnum: 0,
      row: "",
      uploadImageFile:"",
      bimage2:"",
      column:null,
      priState:false,
      adstate:false,
    };
  },
  methods: {
    closeEvent(){
      this.uploadImageFile = "";
      this.bimage2 = "";
      this.dialog = false;
    },

    onFileSelected(event){ 
        this.uploadImageFile = this.$refs.uploadImageFile.files[0]
    },

    onSave(){ 
      if(this.uploadImageFile != ""){
        const fd = new FormData(); //반드시 필요 
          fd.append('img', this.uploadImageFile); //4번 
          axios.post('/imgupload/boardimg',fd).then(response => {
              this.bimage2 = response.data.filename;
              console.log(response.data);
              this.imgadd();
          });
      }
      else{
        alert("이미지를 선택해주세요.");
      }
    },

    imgadd(){ //이미지 추가
        var p = new URLSearchParams();
        p.append("timage", this.bimage2);
        p.append("tcode", this.data.tcode);
        axios.post("/board/boardimgadd", p).then(resposne => {
          if(resposne.data == '1'){
            alert("이미지 추가 성공");
            this.Boarddetail();
            this.uploadImageFile = "";
            this.bimage2 = "";
          }
          else{
            alert("이미지 추가 실패");
            console.log(response.data);
          }
        });
    },

    imgdel(){ // 글 수정에서 이미지만 삭제하기
          var p = new URLSearchParams();
          p.append("img", this.data.timage);
          p.append("tcode", this.data.tcode);
          axios.post("/imgupload/noticeimgdel", p).then(response => {
              console.log(response.data);
              if(response.data == '1'){
                  this.Boarddetail();
                  this.data2 = this.data;
              }
              else{
                  alert("실패");
              }
          });
    },

    ModifyAdd: function(){ //게시판 정보 수정하기
      var params = new URLSearchParams();
      params.append("tcode", this.data.tcode);
      params.append("ttitle", this.data.ttitle);
      params.append("ttext", this.data.ttext);
      axios.post("/notice/noticeupdate", params).then(response => {
        if(response.data == '1'){
          alert("글 수정 성공");
          this.Boarddetail();
          this.closeEvent();
        }
        else{
          alert("글 수정 실패");
        }
      });
    },

    BoardModify: function(){ //게시판 수정하기 창 띄우기
          this.dialog = true;
    },

    BoardDelete(){
      if(this.data.timage != 0){
        this.imgdelete();
      }
      var p = new URLSearchParams();
      p.append("tcode", this.data.tcode);
      axios.post("/notice/noticedel", p).then(response => {
        if(response.data == '1'){
          alert("삭제 완료");
          this.closeEvent();
          this.$router.push("/board/notice");
        }
        else{
          console.log(response.data);
        }
      })
    },

    imgdelete(){ //서버 게시판 이미지 삭제 요청 -> 글 삭제 전 단계
        var p = new URLSearchParams();
        p.append("img", this.data.timage);
        p.append("tcode", this.data.tcode);
        axios.post("/imgupload/noticeimgdel", p).then(response => {
            console.log(response.data);
        });
    },

    Boarddetail: function(){ //게시판 상세정보 가져오기
      var params = new URLSearchParams();
      params.append("tcode", this.$route.params.tcode);
      axios.post("/notice/noticeview", params).then(response =>{
        this.data = response.data[0];
        console.log(this.data)
      });
    },
  },
  created(){
    this.Boarddetail();
  },
  mounted(){
      var result = this.$cookie.get("admin");
      if(result == 1){
        this.adstate = true;
      }
      else{
        this.adstate - false;
      }
  },
};
</script>

<style lang="css">
</style>
