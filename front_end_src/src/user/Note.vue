<template>
    <v-container style="max-width: 1000px">
        <br><v-row>
            <v-col>
                <h1>My Note Page</h1>
            </v-col>
        </v-row><br>

        <v-row>
            <v-col>
                <h3>관리자와 소통할 수 있는 공간입니다.</h3>
            </v-col>
        </v-row><br><hr><br>

        <v-toolbar color="#673AB7">
            <v-btn dark rounded color="#03A9F4" @click="re()">받은쪽지함</v-btn>
            <v-btn dark rounded color="#00BCD4" @click="send()">보낸쪽지함</v-btn>
            <v-spacer></v-spacer>
            <v-btn rounded outlined color="#FFFFFF" @click="addOpen()"><b>쪽지 작성</b></v-btn>
        </v-toolbar><br>

        <v-card v-if="notestate == 0">
            <v-card-title>
                받은 쪽지 목록
            <v-spacer></v-spacer>
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
            ></v-text-field>
            </v-card-title>
            <v-data-table
                :headers="headers"
                :items="desserts"
                :search="search"
                :items-per-page="5"
                @click:row="select_note"
                style="cursor:pointer"
            ></v-data-table>
        </v-card>

        <v-card v-if="notestate == 1">
            <v-card-title>
                보낸 쪽지 목록
            <v-spacer></v-spacer>
            <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
            ></v-text-field>
            </v-card-title>
            <v-data-table
                :headers="headers2"
                :items="desserts"
                :search="search"
                :items-per-page="5"
                @click:row="select_note"
                style="cursor:pointer"
            ></v-data-table>
        </v-card>

    <br><hr><br>


    <div data-app>
    <v-dialog v-model="dialog" persistent max-width="800px">
     <v-toolbar color="#673AB7" dark>
          <v-toolbar-title><h2>쪽지 작성</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
       <v-card-title class="headline">
         쪽지 작성하기
       </v-card-title>

       <v-card-actions v-if="adminState == true">
          <v-text-field
            v-model="user"
            label="ID"
            placeholder="유저 아이디"
            required
          ></v-text-field>
          <v-spacer></v-spacer>
           <v-btn dark large color="#2979FF" @click="idCheck()">회원확인</v-btn>
        </v-card-actions>

        <v-card-title class="headline" v-if="ckstate == true">
         확인완료 회원 닉네임 : {{nickname}}
       </v-card-title>

       <v-card-text>
         <v-textarea
          label="쪽지 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="ntitle"
        ></v-textarea>
         <v-textarea
          outlined
          label="쪽지 내용"
          auto-grow
          v-model="ntext"
        ></v-textarea>
       </v-card-text>

       <form> 
           <input type="file" id="ex_file" ref='uploadImageFile'
            @change="onFileSelected()" accept="image/*"> 
        </form>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded color="#673AB7" dark @click="addClose()">닫기</v-btn>
         <v-btn rounded color="#673AB7" dark @click="onSave()">작성하기</v-btn>
       </v-card-actions>
       </v-col>
     </v-card>
   </v-dialog>
   </div>

   <div data-app>
    <v-dialog v-model="dialog2" persistent max-width="800px">
     <v-toolbar color="#673AB7" dark>
          <v-toolbar-title><h2>Note</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose2()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
        <v-card-title class="headline">
            발신자 : {{data.nsend}}
        </v-card-title>

        <v-card-title class="headline">
            수신자 : {{data.nreceive}}
        </v-card-title>

        <v-card-title class="headline">
            작성일 : {{data.ndate}}
        </v-card-title>

        <v-card-title class="headline" v-if="data.nstate == 1">
            읽음 상태 : 읽음
        </v-card-title>

        <v-card-title class="headline" v-if="data.nstate == 0">
            읽음 상태 : 읽지 않음
        </v-card-title>

       <v-card-title class="headline">
         쪽지 내용
       </v-card-title>

       <v-card-text>
         <v-textarea
          label="쪽지 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.ntitle"
        ></v-textarea>
         <v-textarea
          outlined
          label="쪽지 내용"
          auto-grow
          v-model="data.ntext"
        ></v-textarea>
       </v-card-text>

       <v-img v-if="data.nimage != '0'"
              height="600"
              contain
              :src="'http://minback.com/upimg/'+data.nimage"
            ></v-img>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded dark color="#673AB7" @click="addClose2()">닫기</v-btn>
         <v-btn v-if="notestate == 1" dark color="#673AB7" rounded @click="notedel()">삭제하기</v-btn>
       </v-card-actions>
       </v-col>
     </v-card>
   </v-dialog>
   </div>

    </v-container>
</template>


<script>
  export default {
    data: () => ({
        ntitle:"",
        ntext:"",
        uploadImageFile:"",
        bimage:"",
        dialog:false,
        dialog2:false,
        notestate:0,
        nick:"",
        search: '',
        headers: [
          {
            text: '쪽지 번호',
            align: 'start',
            sortable: false,
            value: 'ncode',
          },
          { text: '제목', value: 'ntitle' },
          { text: '보낸이', value: 'nsend' },
          { text: '읽음 상태', value: 'nstate' },
          { text: '게시일', value: 'ndate' },
        ],
        headers2: [
          {
            text: '쪽지 번호',
            align: 'start',
            sortable: false,
            value: 'ncode',
          },
          { text: '제목', value: 'ntitle' },
          { text: '받는이', value: 'nreceive' },
          { text: '읽음 상태', value: 'nstate' },
          { text: '게시일', value: 'ndate' },
        ],
        desserts: [],
        data:[],
        adminState:false,
        user:"",
        nickname:"",
        ckstate:false,
    }),
    mounted(){
        var nick = this.$cookie.get("admin");
        if(nick == 1){
            this.adminState = true;
        }
        else{
            this.adminState = false;
        }
        this.receive_data();
        console.log(this.adminState);
    },
    methods:{
        idCheck(){
            var p = new URLSearchParams();
            p.append("id", this.user);
            axios.post("/users/usercheck", p).then(response => {
                this.nickname = response.data[0].nickname;
                console.log(response.data);
                if(this.nickname == ""){
                    this.ckstate = false;
                }
                else{
                    alert("회원 확인 완료");
                    this.ckstate = true;
                }
            });
        },
        imgdelete(){ //서버 쪽지 이미지 삭제 요청 -> 글 삭제 전 단계
            var p = new URLSearchParams();
            p.append("img", this.data.nimage);
            p.append("ncode", this.data.ncode);
            axios.post("/imgupload/noteimgdel", p).then(response => {
                console.log(response.data);
            });
        },
        notedel(){
            if(this.data.nimage != "0"){
                this.imgdelete();
            }
            var p = new URLSearchParams();
            p.append("ncode", this.data.ncode);
            axios.post("note/notedel", p).then(response => {
                if(response.data == '1'){
                    alert("삭제 완료");
                    this.dialog2 = false;
                    this.send_data();
                }
                else{
                    alert("삭제 실패");
                    console.log(response.data);
                }
            });
        },
        addClose2(){
            this.dialog2 = false;
        },
        onFileSelected(event){ 
            this.uploadImageFile = this.$refs.uploadImageFile.files[0]
        },
        noteAdd(){
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
            if(minutes < 10){
                minutes = "0" + String(minutes);
            }
            var bcdate = String(yy) + "-" + mm + "-" + dd + " " + hours + ":" + minutes;
            var p = new URLSearchParams();
            var img;
            if(this.bimage == ""){
                p.append("nimage", '0');
            }
            else{
                p.append("nimage", this.bimage);
            }
            p.append("ntitle", this.ntitle);
            p.append("ntext", this.ntext);
            if(this.adminState == false){
                p.append("nsend", this.$cookie.get("uid"));
                p.append("nreceive", "admin")
            }
            else if(this.adminState == true){
                p.append("nsend", "admin");
                p.append("nreceive", this.user);
            }
            p.append("ndate", bcdate);
            axios.post("/note/noteadd", p).then(response => {
                if(response.data == '1'){
                    alert("글 작성 완료");
                    this.addClose();
                    this.send();
                }
                else{
                    alert("글 작성 실패");
                    console.log(response.data);
                }
            });
        },
        onSave(){
            if(this.uploadImageFile != ""){
                const fd = new FormData(); //반드시 필요 
                fd.append('img', this.uploadImageFile); //4번 
                axios.post('/imgupload/boardimg',fd).then(response => {
                    this.bimage = response.data.filename;
                    console.log(response.data);
                    this.noteAdd();
                });
            }
            else{
                this.noteAdd();
            }
        },
        addClose(){
            this.dialog = false;
            this.ntitle = "";
            this.ntext = "";
            this.bimage = "";
            this.uploadImageFile = "";
        },
        addOpen(){
            this.dialog = true;

        },
        re(){
            this.notestate = 0;
            this.receive_data();
        },
        send(){
            this.notestate = 1;
            this.send_data();
        },
        select_note(value){
            this.data = value;
            if(this.data.nstate == 0 && this.notestate == 0){
                var p = new URLSearchParams()
                p.append("ncode", value.ncode);
                axios.post("/note/noteview", p).then(response => {
                    console.log(response.data);
                });
            }
            this.dialog2 = true;
        },
        send_data(){
            var p = new URLSearchParams();
            if(this.adminState == false){
                p.append("id", this.$cookie.get('uid'));
            }
            else{
                p.append("id", "admin");
            }
            axios.post("/note/notesendlist", p).then(response => {
                this.desserts = response.data;
                console.log(this.desserts);
            });
        },
        receive_data(){
            var p = new URLSearchParams();
            if(this.adminState == false){
                p.append("id", this.$cookie.get('uid'));
            }
            else{
                p.append("id", "admin");
            }
            axios.post("/note/notereceivelist", p).then(response => {
                this.desserts = response.data;
                console.log(this.desserts);
            });
        },
    }
  }
</script>
