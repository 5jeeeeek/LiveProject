<template>
    <v-container style="max-width: 1000px">
        <br>
        <v-row>
            <v-col>
            <h1>도서관 공지사항</h1>
            </v-col>
        </v-row><br>

            <v-card>
                <v-simple-table>
                    <template v-slot:default>
                    <thead>
                        <tr>
                        <th class="text-left">
                            글 번호
                        </th>
                        <th class="text-left">
                            제목
                        </th>
                        <th class="text-left">
                            작성자
                        </th>
                        <th class="text-left">
                            게시일
                        </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="item in data"
                            :key="item.tcode"
                            @click="openData(item.tcode)"
                            style="cursor:pointer"
                        >
                        <td>{{ item.tcode }}</td>
                        <td>{{ item.ttitle }}</td>
                        <td>{{ item.tname }}</td>
                        <td>{{ item.tdate }}</td>
                        </tr>
                    </tbody>
                    </template>
                </v-simple-table>
             </v-card>
             <br>
             <v-row>
             <v-spacer></v-spacer>
             <v-btn rounded large outlined color="#FF9800" v-if="adminState == 1"
             @click="dialog = true"
                  ><b>공지사항 작성하기</b></v-btn>
                  </v-row>
            <br>


   <div class="text-center">
        <v-pagination
        color="#3F51B5"
        v-model="num"
        :length="len"
        ></v-pagination>
    </div>


    <div data-app>
    <v-dialog v-model="dialog" persistent max-width="800px">
     <v-toolbar dark>
          <v-toolbar-title><h2>공지사항 작성</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
       <v-card-title class="headline">
         공지사항 작성하기
       </v-card-title>
       <v-card-text>
         <v-textarea
          label="글 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="title"
        ></v-textarea>
         <v-textarea
          outlined
          label="글 내용"
          auto-grow
          v-model="text"
        ></v-textarea>
       </v-card-text>

       <form> 
           <input type="file" id="ex_file" ref='uploadImageFile'
            @change="onFileSelected()" accept="image/*"> 
        </form>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded @click="addClose()">닫기</v-btn>
         <v-btn rounded @click="onSave()">작성하기</v-btn>
       </v-card-actions>
       </v-col>
     </v-card>
   </v-dialog>
   </div>

  </v-container>
</template>


<script>
export default {
    data: function(){
        return{
            adminState:0,
            data:[],
            num:1,
            len:0,
            title:"",
            text:"",
            image:"",
            uploadImageFile:"",
            dialog:false,
        };
    },
    methods:{
        onFileSelected(event){ 
            this.uploadImageFile = this.$refs.uploadImageFile.files[0]
        },
        addClose(){
            this.dialog = false;
            this.boardcate = "";
            this.btitle = "";
            this.btext = "";
            this.bimage = "";
            this.uploadImageFile = "";
        },
        boardAdd(){
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
            if(this.image == ""){
                p.append("timage", '0');
            }
            else{
                p.append("timage", this.image);
            }
            p.append("ttitle", this.title);
            p.append("ttext", this.text);
            p.append("tname", "관리자");
            p.append("tdate", bcdate);
            axios.post("/notice/noteadd", p).then(response => {
                if(response.data == '1'){
                    alert("글 작성 완료");
                    this.addClose();
                    this.boardData();
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
                    this.image = response.data.filename;
                    console.log(response.data);
                    this.boardAdd();
                });
            }
            else{
                this.boardAdd();
            }
        },
        openData(code){
            this.$router.push("/board/noticedetail/" + code);
        },
        boardlength(){
            var params = new URLSearchParams();
            axios.post("/notice/noticelen", params).then(response => {
                var num1 = parseInt(response.data[0].cnt) / 10;
                var num2 = num1 % 10;
                if(num2 > 0){
                    this.len = num1 + 1;
                }
                else{
                    this.len = num1;
                }
            });
        },
        boardData(){
            var params = new URLSearchParams();
            params.append("number", this.num);
            axios.post("/notice/noticelist", params).then(response => {
                this.data = response.data;
                this.boardlength();
                console.log(this.data);
            });
        },
    },
    mounted(){
        this.boardData();
        var ss = this.cookie.get('admin');
        if(ss == 1){
            this.adminState = 1;
        }
        else{
            this.adminState = 0;
        }

    },
    watch:{
        num: function(){
            this.boardData();
        },
    }
}
</script>