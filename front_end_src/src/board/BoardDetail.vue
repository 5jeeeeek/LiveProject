<template>
  <v-container style="max-width: 1000px">
    <br>
   <v-col>
     <v-card>
         <v-card-title class="headline">
          {{data.btitle}}
        </v-card-title>
       <v-card-text>
         <v-card-actions>
           <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
         <pre><div>  {{data.bnickname}}  </div></pre><v-divider vertical></v-divider>
         <pre><span>  {{data.bdate}}</span></pre>
           <v-spacer></v-spacer>
           
           <v-btn rounded large outlined color="#025ec7"
              @click="boardlike(data.bcode)">
              <v-icon large>mdi-thumb-up</v-icon>
              {{data.blike}}
            </v-btn><pre><div>  </div></pre>

           <v-btn rounded large outlined color="#025ec7">
             
             <v-icon large>mdi-arrow-up-bold-box-outline</v-icon>
             {{data.bviews}}</v-btn><pre><div>  </div></pre>

           <v-btn rounded large outlined color="#025ec7">
             
             <v-icon large>
                mdi-message-text
              </v-icon>{{data.bcomment}}
             </v-btn>

         </v-card-actions>
       </v-card-text><v-divider></v-divider><br>
            <v-img
            v-if="data.bimage != 0"
            height="700"
            contain
            :src="'http://minback.com/upimg/'+data.bimage"
          ></v-img>
       <v-card-text>{{data.btext}}
     </v-card-text><br>
     <v-card-actions>
       <v-spacer></v-spacer>
       <v-btn rounded dark color="#0276f9" @click="BoardModify(data.bcode, data.id)">
         글 수정하기</v-btn><pre><div>  </div></pre>
       <v-btn rounded dark color="#0276f9" @click="BoardDelete(data.bcode, data.id)">
         글 삭제하기</v-btn>
     </v-card-actions>
     <v-divider></v-divider><br>
     <v-btn block dark color="#3591fa" @click="$router.push('/board/board')">게시판 목록</v-btn>
     </v-card>
   </v-col>

    <v-row>
      <v-col cols="10">
        <v-textarea
          label="댓글을 입력하세요"
          v-model="qrptext"
          outlined
        ></v-textarea>
        <v-spacer></v-spacer>
      </v-col>
      <v-col cols="2">
        <v-radio-group
          v-model="column"
          column
        >
          <v-radio
            label="공개"
            value="0"
          ></v-radio>
          <v-radio
            label="비공개"
            value="1"
          ></v-radio>
          <v-btn large dark color="#3f97fa" @click="onAddText()">등록</v-btn>
        </v-radio-group>
      </v-col>
    </v-row>

   <v-row align="center">
    <template v-for="(one,i) in comment">
      <v-col :key="i" cols="12">

          <v-card class="mx-auto" color="#FAFAFA" v-if="one.cprivate == 0">
              <v-card-actions>
                <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
              <v-card-title class="title  text-md-left">{{one.cname}} </v-card-title><v-spacer></v-spacer>
              <span>게시일 : {{one.cdate}}</span></v-card-actions>
                <v-card-actions>
                <v-card-text>
                  {{one.ctext}}
                </v-card-text>
                <v-spacer></v-spacer>
            <v-btn dark color="#3f97fa" @click="DelComment(one.ccode, one.id)">삭제</v-btn><br></v-card-actions>
          </v-card>

          <v-card class="mx-auto" color="#FAFAFA" v-else-if="one.cprivate == 1 & one.id == id">
              <v-card-actions>
                <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
              <v-card-title class="title  text-md-left">{{one.cname}} </v-card-title><v-spacer></v-spacer>
              <span>게시일 : {{one.cdate}}</span></v-card-actions>
                <v-card-actions>
                <v-card-text>
                  {{one.ctext}}
                </v-card-text>
                <v-spacer></v-spacer>
            <v-btn dark color="#3f97fa" @click="DelComment(one.ccode, one.id)">삭제</v-btn><br></v-card-actions>
          </v-card>

          <v-card class="mx-auto" color="#FAFAFA" v-else-if="one.cprivate == 1 & one.id == data.id">
              <v-card-actions>
                <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
              <v-card-title class="title  text-md-left">{{one.cname}} </v-card-title><v-spacer></v-spacer>
              <span>게시일 : {{one.cdate}}</span></v-card-actions>
                <v-card-actions>
                <v-card-text>
                  {{one.ctext}}
                </v-card-text>
                <v-spacer></v-spacer>
            <v-btn dark color="#3f97fa" @click="DelComment(one.ccode, one.id)">삭제</v-btn><br></v-card-actions>
          </v-card>

          <v-card class="mx-auto" color="#424242" dark v-else>
              <v-card-actions>
                <v-avatar color="#2962FF">
                  <v-icon dark>
                    mdi-account-circle
                  </v-icon>
                </v-avatar>
              <v-card-title class="title  text-md-left">{{one.cname}} </v-card-title><v-spacer></v-spacer>
              <span>게시일 : {{one.cdate}}</span></v-card-actions>
                <v-card-actions>
                <v-card-text>
                  비공개 댓글입니다. 댓글 작성자와 게시물 작성자만 볼 수 있습니다.
                </v-card-text>
                <v-spacer></v-spacer>
            <v-btn dark color="#3f97fa" @click="DelComment(one.ccode, one.id)">삭제</v-btn><br></v-card-actions>
          </v-card>

        </v-col>
     </template>
   </v-row>

    <div data-app>
    <v-dialog max-width="700px" v-model="dialog">
      <v-card>
        <v-col>
        <v-card-title primary-title>
          <span class="headline">{{data2.bnickname}}님 환영합니다.</span>
        </v-card-title><hr>
            <v-radio-group
              v-model="data2.bcate"
              row
            >
              <v-radio
                label="자유게시판"
                value="1"
              ></v-radio>
              <v-radio
                label="질문게시판"
                value="2"
              ></v-radio>
        </v-radio-group>
          <v-textarea
          label="글 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data2.btitle"
        ></v-textarea>
         <v-textarea
          outlined
          label="글 내용"
          auto-grow
          v-model="data2.btext"
        ></v-textarea>
        <v-img
            v-if="data2.bimage != 0"
            :aspect-ratio="1"
            :src="'http://minback.com/upimg/'+data2.bimage"
          ></v-img>
        <v-btn v-if="data2.bimage != 0" large @click="imgdel()">이미지 삭제하기</v-btn>
          <form v-if="data2.bimage == 0"> 
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
    imgadd(){
        var p = new URLSearchParams();
        p.append("bimage", this.bimage2);
        p.append("bcode", this.data2.bcode);
        axios.post("/board/boardimgadd", p).then(resposne => {
          if(resposne.data == '1'){
            alert("이미지 추가 성공");
            this.Boarddetail();
            this.data2 = this.data;
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
          p.append("img", this.data2.bimage2);
          p.append("bcode", this.data2.bcode);
          axios.post("/imgupload/boardimgdel", p).then(response => {
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
    boardlike: function(code){ //좋아요 이벤트
      var pp = new URLSearchParams();
      pp.append("bcode", code);
      pp.append("id", this.$cookie.get('uid'));
      axios.post("/board/boardlike", pp).then(response => {
        if(response.data == '1'){
          alert("좋아요 성공");
          this.Boarddetail();
        }
        else{
          alert("이미 좋아요를 누르셨습니다.");
        }
      });
    },
    ModifyAdd: function(){ //게시판 정보 수정하기
      var params = new URLSearchParams();
      params.append("bcode", this.data2.bcode);
      params.append("btitle", this.data2.btitle);
      params.append("btext", this.data2.btext);
      params.append("bcate", this.data2.bcate);
      axios.post("/board/boardupdate", params).then(response => {
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

    BoardModify: function(code, id){ //게시판 수정하기 창 띄우기
      var uid = this.$cookie.get("uid");
      if(uid == null){
        alert("로그인 해주세요.");
      }
      else if(uid != null){
        if(id == uid){
          this.data2 = this.data;
          this.dialog = true;
        }
        else if(id != uid){
          alert("본인이 작성한 글이 아닙니다.");
        }
      }
    },
    imgdelete(){ //서버 게시판 이미지 삭제 요청 -> 글 삭제 전 단계
        var p = new URLSearchParams();
        p.append("img", this.data.bimage);
        p.append("bcode", this.data.bcode);
        axios.post("/imgupload/boardimgdel", p).then(response => {
            console.log(response.data);
        });
    },
    BoardDelete: function(code, id){ //게시판 글 삭제하기
      var uid = this.$cookie.get("uid");
      if(uid == null){
        alert("로그인 해주세요.");
      }
      else if(uid != null){
        if(id == this.$cookie.get("uid")){
          if(this.data.bimage != '0'){
              this.imgdelete();
          }
          var params = new URLSearchParams();
          params.append("bcode", code);
          axios.post("/board/boarddel", params).then(response => {
            if(response.data == '1'){
              alert("삭제 성공");
              this.$router.push("/board/board");
            }
            else{
              alert("글 삭제 실패");
              console.log(response.data)
            }
          });
        }
        else{
          alert("본인이 작성한 글이 아닙니다.");
        }
      }
    },

    Boarddetail: function(){ //게시판 상세정보 가져오기
      var params = new URLSearchParams();
      params.append("bcode", this.$route.params.bcode);
      axios.post("/board/boardview", params).then(response =>{
        this.data = response.data;
        console.log(this.data)
      });
    },

    DelComment: function(code, id){ //댓글 삭제하기
      var uid = this.$cookie.get("uid");
      if(uid == null){
        alert("로그인 해주세요.")
      }
      else if(uid != null){
        if(id == uid){
          var params = new URLSearchParams();
          params.append("ccode", code);
          params.append("bcode", this.$route.params.bcode);
          axios.post("/board/bcdel", params).then(response =>{
            var result = response.data;
            if(result == '1'){
              alert("댓글 삭제 완료");
              this.CmtUpdate();
            }
          });
        }
        else{
          alert("본인이 작성한 댓글이 아닙니다.");
        }
      }
    },

    CmtUpdate: function(){ //댓글 불러오기
      var params = new URLSearchParams();
      params.append("bcode", this.$route.params.bcode);
      axios.post("/board/bclist", params).then(response =>{
        this.comment = response.data;
        console.log(this.comment);
      });
    },

    onAddText: function() { //댓글 등록하기
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
      var bcdate = String(yy) + "-" + mm + "-" + dd + " " + hours + ":" + minutes;
      var params = new URLSearchParams();
      if(this.$cookie.get("uid") != null){
        if(this.column != null){
          params.append("bcode", this.$route.params.bcode);
          params.append("id", this.$cookie.get('uid'));
          params.append("cname", this.$cookie.get("memberNick"));
          params.append("ctext", this.qrptext);
          params.append("cdate", bcdate);
          params.append("cprivate", this.column);
          axios.post("/board/bcadd", params).then(response =>{
            var result = response.data;
            if(result == '1'){
              alert("댓글이 등록되었습니다.");
              this.qrptext = "";
              this.column = null;
              this.CmtUpdate();
            }
            else{
              alert("댓글등록 실패");
            }
          });
        }
        else{
          alert("댓글 공개여부를 선택하세요.");
        }
      }
      else{
        alert("로그인 하세요.");
      }
    },
    // privateCheck(){
    //   if(this.id == this.data.id){
    //     this.priState = true;
    //   }
    //   else{
    //     this.priState = false;
    //   }
    // }
  },
  created(){
    this.id = this.$cookie.get('uid');
    this.Boarddetail();
    this.CmtUpdate();
  },
  mounted(){
  
  },
};
</script>

<style lang="css">
</style>
