<template>
    <v-container style="max-width: 1000px">
        <br>
        <v-row>
            <v-col>
            <h1>블랙리스트 관리</h1>
            </v-col>
        </v-row><br>

            <v-card>
                <v-card-title>
                    블랙리스트 회원 목록
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

       <v-card-title class="headline">
         회원 상태 : 블랙리스트 회원
       </v-card-title>

       <br>

        <v-btn rounded block dark color="#424242"
        @click="blackout()">블랙리스트 해제</v-btn>

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
            data: [],
            search:"",
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
        blackdata(){
            var p = new URLSearchParams();
            axios.post("/users/blacklist", p).then(response => {
                this.desserts = response.data;
                console.log(response.data);
            });
        },
        blackout(){
            var result = confirm("정말 해당 회원을 블랙리스트 해제 하시겠습니까?");
            if(result){
                var p = new URLSearchParams();
                p.append("id", this.data.id);
                axios.post("/users/nomaluser", p).then(response => {
                    if(response.data == '1'){
                        alert("블랙리스트 해제 완료");
                        this.dialog = false;
                        this.blackdata();

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
    },
    mounted(){
        this.blackdata();
    },
}
</script>