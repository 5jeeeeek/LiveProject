<template>
    <v-container style="max-width: 1000px">
        <br>
        <v-row>
            <v-col>
            <h1>회원 관리</h1>
            </v-col>
        </v-row><br>

        <v-row>
            <v-col cols="3">
                <v-select
                v-model="select"
                :items="list"
                filled
                label="검색 옵션"
                ></v-select>
            </v-col>
            <v-col cols="9">
                <v-text-field
                    v-model="usersearch"
                    label="게시판 검색"
                    outlined
                    append-icon="mdi-magnify"
                    clear-icon="mdi-close-circle"
                    clearable
                    type="text"
                    @click:clear="clearMessage()"
                    @click:append="userSearch()"
                    @keyup.enter="userSearch()"
                ></v-text-field>
            </v-col>
        </v-row>

            <v-card>
                <v-card-title>
                    회원 검색 결과
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
                    @click:row="userDetail"
                    style="cursor:pointer"
                ></v-data-table>
             </v-card>
            <br> 

    <div data-app>
    <v-dialog v-model="dialog" persistent max-width="600px">
     <v-toolbar dark>
          <v-toolbar-title><h2>회원 정보</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
       <v-card-text>
         <v-textarea
          label="아이디"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.id"
        ></v-textarea>

        <v-textarea
          label="이름"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.name"
        ></v-textarea>

        <v-textarea
          label="닉네임"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.nickname"
        ></v-textarea>

        <v-textarea
          label="이메일"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.email"
        ></v-textarea>

        <v-textarea
          label="연락처"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.phone"
        ></v-textarea>

        <v-textarea
          label="본인확인 질문"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.question"
        ></v-textarea>

        <v-textarea
          label="본인확인 질문"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.answer"
        ></v-textarea>

        <v-card-title class="headline" v-if="data.blackstate == 0">
         회원 상태 : 일반 회원
       </v-card-title>

       <v-card-title class="headline" v-if="data.blackstate == 1">
         회원 상태 : 블랙리스트 회원
       </v-card-title>

       <br>

        <v-btn rounded block dark color="#424242" v-if="data.blackstate == 0"
        @click="black()">블랙리스트 추가</v-btn>

        <br>
        <v-btn rounded block dark color="#424242" @click="userout()">회원 탈퇴</v-btn>

       </v-card-text>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded large dark color="#673AB7" @click="addClose()">닫기</v-btn>
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
            list: ['전체', '아이디', '이름', '닉네임', '이메일'],
            data: [],
            select:"",
            search:"",
            usersearch:"",
            dialog:false,
            headers: [
            {
                text: '아이디',
                align: 'start',
                sortable: false,
                value: 'id',
            },
            { text: '닉네임', value: 'nickname' },
            { text: '이름', value: 'name' },
            { text: '이메일', value: 'email' }
            ],
            desserts: [],
        };
    },
    methods:{
        black(){
            var result = confirm("정말 해당 회원을 블랙리스트에 추가하겠습니까?");
            if(result){
                var p = new URLSearchParams();
                p.append("id", this.data.id);
                axios.post("/users/blackuser", p).then(response => {
                    if(response.data == '1'){
                        alert("블랙리스트 추가 완료");
                    }
                    else{
                        console.log(response.data);
                    }
                });
            }
        },
        userout(){
            var result = confirm("정말 해당 회원을 탈퇴시키겠습니까?");
            if(result){
                var p = new URLSearchParams();
                p.append("id", this.data.id);
                axios.post("/users/userout", p).then(response => {
                    if(response.data == '1'){
                        alert("회원 탈퇴 완료");
                        this.dialog = false;
                    }
                    else{
                        console.log(response.data);
                    }
                });
            }
        },
        addClose(){
            this.dialog = false;
        },
        userDetail(value){
            this.data = value;
            this.dialog = true;
        },
        userSearch(){
            if(this.usersearch == "" || this.list == ""){
                alert("검색어와 검색 옵션을 올바르게 입력해주세요.");
            }
            else{
                var user_url = "";
                if(this.select == "전체"){
                    user_url = "/users/alluser";
                }
                else if(this.select == "아이디"){
                    user_url = "/users/iduser";
                }
                else if(this.select == "이름"){
                    user_url = "/users/nameuser";
                }
                else if(this.select == "닉네임"){
                    user_url = "/users/nickuser";
                }
                else if(this.select == "이메일"){
                    user_url = "/users/emailuser";
                }
                var p = new URLSearchParams();
                p.append("search", this.usersearch);
                axios.post(user_url , p).then(response => {
                    console.log(response.data);
                    this.desserts = response.data;
                    if(this.desserts.length == 0){
                        alert("검색 결과가 없습니다.");
                    }
                });
            }
        },
        clearMessage(){
            this.usersearch = "";
        },
    },
    mounted(){

    },
}
</script>