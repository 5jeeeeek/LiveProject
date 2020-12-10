<template>
    <v-container>
        <br>
        <v-row>
            <v-col>
            <h1>Live 커뮤니티</h1>
            </v-col>
        </v-row><br>

        <v-row>
            <v-col>
                <v-text-field
                    v-model="search"
                    label="게시판 검색"
                    outlined
                    append-icon="mdi-magnify"
                    clear-icon="mdi-close-circle"
                    clearable
                    type="text"
                    @click:clear="clearMessage()"
                    @click:append="boardSearch()"
                    @keyup.enter="boardSearch()"
                ></v-text-field>
            </v-col>
        </v-row>

        <v-toolbar color="#3F51B5">
            <v-btn dark rounded color="#03A9F4" @click="boardData()">자유게시판</v-btn>
            <v-btn dark rounded color="#00BCD4" @click="boardData2()">질문게시판</v-btn>
            <v-btn dark rounded color="#009688" @click="boardData3()">인기게시판</v-btn>
            <v-spacer></v-spacer>
            <v-btn rounded outlined color="#FFFFFF" @click="addOpen()"><b>글 작성</b></v-btn>
        </v-toolbar><br>


        <v-row>
            <template v-for="(item, i) in data">
            <v-col :key="i" cols="6">
                <v-hover
                    v-slot="{ hover }"
                >
                <v-card
                    :elevation="hover ? 16 : 2"
                    :class="{ 'on-hover': hover }"
                    class="mx-auto"
                    color="#3F51B5"
                    dark
                    @click="openBoard(item.bcode)"
                >
                    <v-card-title>
                    <v-icon
                        large
                        left
                    >
                        mdi-twitter
                    </v-icon>
                    <span class="title font-weight-light">{{item.btitle}}</span>
                    </v-card-title>

                    <v-card-text class="headline font-weight-bold">
                        {{item.btext}}
                    </v-card-text>

                    <v-card-actions>
                    <v-list-item class="grow">
                        <v-list-item-avatar color="grey darken-3">
                        <v-img
                            class="elevation-6"
                            alt=""
                            src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                        ></v-img>
                        </v-list-item-avatar>

                        <v-list-item-content>
                        <v-list-item-title>작성자:{{item.bnickname}}</v-list-item-title>
                        </v-list-item-content>

                        <v-spacer></v-spacer>
                                <v-icon>
                                    mdi-thumb-up
                                </v-icon>
                                <span>{{item.blike}}</span>
                                <v-icon>
                                    mdi-share-variant
                                </v-icon>
                                <span>{{item.bviews}}</span>
                                <v-icon>
                                    mdi-message-text
                                </v-icon>
                                <span>{{item.bcomment}}</span>
                    </v-list-item>
                    </v-card-actions>
                </v-card>
             </v-hover>
        </v-col>
        </template>
    </v-row>

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
          <v-toolbar-title><h2>게시판 글 작성</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
       <v-card-title class="headline">
         게시판 글 작성하기
       </v-card-title>
       <v-card-text>
         <v-select :items="BoardCate" label="게시판 카테고리"
          chips v-model="boardcate"></v-select>
         <v-textarea
          label="글 제목"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="btitle"
        ></v-textarea>
         <v-textarea
          outlined
          label="글 내용"
          auto-grow
          v-model="btext"
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
            data: [],
            num: 1,
            len:0,
            search:"",
            bstate:1,
            dialog:false,
            BoardCate :['자유게시판', '질문게시판'],
            boardcate:"",
            btitle:"",
            btext:"",
            bimage:"",
            uploadImageFile:"",
            data2:[],
        };
    },
    methods:{
        // test1(){
        //     var p = new URLSearchParams();
        //     p.append("img", "2020-11-09T19_29_06_621ZScreenshot_20201109-210906_LIVERARY.jpg");
        //     axios.post("/imgupload/imgdel", p).then(response => {
        //         console.log(response.data);
        //         if(response.data == '1'){
        //             alert("삭제 성공");
        //         }
        //         else{
        //             alert("실패");
        //         }
        //     });
        // },
        onFileSelected(event){ 
            this.uploadImageFile = this.$refs.uploadImageFile.files[0]
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
            var img;
            if(this.bimage == ""){
                p.append("bimage", '0');
            }
            else{
                p.append("bimage", this.bimage);
            }
            if(this.boardcate == "자유게시판"){
                p.append("bcate", '1');
            }
            else if(this.boardcate == "질문게시판"){
                p.append("bcate", '2');
            }
            p.append("id", this.$cookie.get("uid"));
            p.append("btitle", this.btitle);
            p.append("btext", this.btext);
            p.append("bnickname", this.$cookie.get("memberNick"));
            p.append("bdate", bcdate);
            axios.post("/board/boardadd", p).then(response => {
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
                    this.bimage = response.data.filename;
                    console.log(response.data);
                    this.boardAdd();
                });
            }
            else{
                this.boardAdd();
            }
        },
        addClose(){
            this.dialog = false;
            this.boardcate = "";
            this.btitle = "";
            this.btext = "";
            this.bimage = "";
            this.uploadImageFile = "";
        },
        addOpen(){
            this.dialog = true;
        },
        boardSearch(){
            if(this.search == ""){
                alert("검색어를 입력해주세요.");
            }
            else{
                var p = new URLSearchParams();
                p.append("boardsearch", this.search);
                axios.post("/board/boardsearch", p).then(response => {
                    if(response.data.length == 0){
                        alert("검색된 게시물이 없습니다.");
                    }
                    else{
                        this.bstate = 4;
                        var num1 = parseInt(response.data.length) / 10;
                        var num2 = num1 % 10;
                        if(num2 > 0){
                            this.len = num1 + 1;
                        }
                        else{
                            this.len = num1;
                        }
                        this.data2 = response.data;
                        this.pageItem();
                        console.log(this.data2);
                    }
                });
            }
        },
        pageItem: function(){
            this.data = this.data2.slice(
                (this.num - 1) * 10,
                this.num * 10
            )
        },
        clearMessage(){
            this.search = "";
        },
        boardlength(lens){
            var params = new URLSearchParams();
            params.append("bcate", lens);
            axios.post("/board/boardlen", params).then(response => {
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
        boardData3(){
            var params = new URLSearchParams();
            params.append("number", this.num);
            axios.post("/board/boardlist3", params).then(response => {
                this.data = response.data;
                this.boardlength(3);
                this.bstate = 3;
                console.log(this.data);
            });
        },
        boardData2(){
            var params = new URLSearchParams();
            params.append("number", this.num);
            axios.post("/board/boardlist2", params).then(response => {
                this.data = response.data;
                this.boardlength(2);
                this.bstate = 2;
                console.log(this.data);
            });
        },
        boardData(){
            var params = new URLSearchParams();
            params.append("number", this.num);
            axios.post("/board/boardlist1", params).then(response => {
                this.data = response.data;
                this.boardlength(1);
                this.bstate = 1;
                console.log(this.data);
            });
        },
        openBoard(bcode){
            var params = new URLSearchParams();
            params.append("bcode", bcode);
            axios.post("/board/viewadd", params).then(response => {
            if(response.data == '1'){
                this.$router.push("/board/boarddetail/" + bcode);
            }
            else{
                alret("오류")
            }
            });

        },
    },
    mounted(){
        this.boardData();
    },
    watch:{
        num:function(){
            if(this.bstate == 1){
                this.boardData();
            }
            else if(this.bstate == 2){
                this.boardData2();
            }
            else if(this.bstate == 3){
                this.boardData3();
            }
            else if(this.bstate == 4){
                this.pageItem();
            }
        },
    }
}
</script>