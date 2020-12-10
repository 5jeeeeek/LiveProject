<template>
  <v-container>
    <v-col><br>
     <p><h1>개인 정보수정</h1></p><br><br><hr><br>
   </v-col>
  

    <v-row
      class="fill-height"
      align="center"
      justify="center">

        <v-col cols="3">
            <v-card
                color="#0D47A1"
                dark
            >
                <v-card-title class="headline">
                ID
                </v-card-title>

                <v-card-subtitle>{{userdata[0].id}}</v-card-subtitle>

                <v-card-actions>
                <v-btn outlined>    
                    변경불가
                </v-btn>
                </v-card-actions>
            </v-card>
        </v-col>


        <v-col cols="3">
            <v-card
                color="#42A5F5"
                dark
            >
                <v-card-title class="headline">
                Password
                </v-card-title>

                <v-card-subtitle>*************</v-card-subtitle>

                <v-card-actions>
                <div data-app>
                    <v-dialog
                        v-model="dialog"
                        persistent
                        max-width="500px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                outlined
                                v-bind="attrs"
                                v-on="on"
                                >
                                비밀번호 변경
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">비밀번호 변경</span>
                                </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                            <v-col>
                                <v-text-field
                                v-model="pwo"
                                label="기존 비밀번호"
                            outline
                            type="password"
                        ></v-text-field>
                            </v-col>
                            </v-row>

                        <v-row>
                            <v-col>
                            <v-text-field
                            v-model="pw"
                            label="새로운 비밀번호"
                            outline
                            type="password"
                        ></v-text-field>
                            </v-col>
                        </v-row>

                        <v-row>
                            <v-col>
                            <v-text-field
                            v-model="pwc"
                            label="비밀번호 확인"
                            outline
                            type="password"
                        ></v-text-field>
                            </v-col>
                        </v-row>

                        <v-row>
                        <v-btn depressed block @click="pwchange()">비밀번호 변경</v-btn>
                        </v-row>
                    </v-container>
                         </v-card-text>
                         <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn text @click="closebtn()">
                                    Close
                                </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                </v-card-actions>
            </v-card>
        </v-col>

        <v-col cols="3">
            <v-card
                color="#42A5F5"
                dark
            >
                <v-card-title class="headline">
                Email
                </v-card-title>

                <v-card-subtitle>{{userdata[0].email}}</v-card-subtitle>

                <v-card-actions>
                <div data-app>
                    <v-dialog
                        v-model="dialog2"
                        persistent
                        max-width="550px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                outlined
                                v-bind="attrs"
                                v-on="on"
                                >
                                이메일 변경
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">이메일 변경</span>
                                </v-card-title>

                    <v-card-text>
                        <v-container>

                            <v-card-actions>
                                <v-text-field
                                v-model="email"
                                label="새로운 이메일"
                            outline
                        ></v-text-field>
                        <v-spacer></v-spacer>
                             <v-btn @click="emailSend()">이메일 인증요청</v-btn>
                        </v-card-actions>


                        <v-card-actions>
                                <v-text-field
                                v-model="emailcode"
                                label="이메일 인증번호"
                            outline
                        ></v-text-field>
                        <v-spacer></v-spacer>
                             <v-btn @click="codecheck()">인증번호 확인</v-btn>
                        </v-card-actions>

                        <v-row>
                        <v-btn depressed block @click="emailChange()">이메일 변경</v-btn>
                        </v-row>

                    </v-container>
                         </v-card-text>
                         <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn text @click="closebtn()">
                                    Close
                                </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                </v-card-actions>
            </v-card>
        </v-col>


        <v-col cols="3">
            <v-card
                color="#0D47A1"
                dark
            >
                <v-card-title class="headline">
                Name
                </v-card-title>

                <v-card-subtitle>{{userdata[0].name}}</v-card-subtitle>

                <v-card-actions>
                <v-btn outlined>
                    변경불가
                </v-btn>
                </v-card-actions>
            </v-card>
        </v-col>


        <v-col cols="3">
            <v-card
                color="#00ACC1"
                dark
            >
                <v-card-title class="headline">
                Nickname
                </v-card-title>

                <v-card-subtitle>{{userdata[0].nickname}}</v-card-subtitle>

                <v-card-actions>
                <div data-app>
                    <v-dialog
                        v-model="dialog3"
                        persistent
                        max-width="550px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                outlined
                                v-bind="attrs"
                                v-on="on"
                                >
                                닉네임 변경
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">닉네임 변경</span>
                                </v-card-title>

                    <v-card-text>
                        <v-container>

                            <v-card-actions>
                                <v-text-field
                                v-model="nick"
                                label="새로운 닉네임"
                            outline
                        ></v-text-field>
                        <v-spacer></v-spacer>
                             <v-btn @click="nickCheck()">닉네임 중복확인</v-btn>
                        </v-card-actions>

                        <v-row>
                        <v-btn depressed block @click="nickChange()">닉네임 변경</v-btn>
                        </v-row>

                    </v-container>
                         </v-card-text>
                         <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn text @click="closebtn()">
                                    Close
                                </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                </v-card-actions>
            </v-card>
        </v-col>


        <v-col cols="3">
            <v-card
                color="#0097A7"
                dark
            >
                <v-card-title class="headline">
                Phone
                </v-card-title>

                <v-card-subtitle>{{userdata[0].phone}}</v-card-subtitle>

                <v-card-actions>
                    <div data-app>
                    <v-dialog
                        v-model="dialog4"
                        persistent
                        max-width="550px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                outlined
                                v-bind="attrs"
                                v-on="on"
                                >
                                전화번호 변경
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">전화번호 변경</span>
                                </v-card-title>

                    <v-card-text>
                        <v-container>

                                <v-text-field
                                v-model="phone"
                                label="새로운 전화번호"
                            outline
                        ></v-text-field>

                        <v-row>
                        <v-btn depressed block @click="phoneChange()">전화번호 변경</v-btn>
                        </v-row>

                    </v-container>
                         </v-card-text>
                         <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn text @click="closebtn()">
                                    Close
                                </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                </v-card-actions>
            </v-card>
        </v-col>

        <v-col cols="6">
            <v-card
                color="#006064"
                dark
            >
                <v-card-title class="headline">
                Question and Answer
                </v-card-title>

                <v-card-subtitle>{{userdata[0].question}} : {{userdata[0].answer}}</v-card-subtitle>

                <v-card-actions>
                    <div data-app>
                    <v-dialog
                        v-model="dialog5"
                        persistent
                        max-width="550px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                outlined
                                v-bind="attrs"
                                v-on="on"
                                >
                                질문 / 답변 변경
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">질문 / 답변 변경</span>
                                </v-card-title>

                    <v-card-text>
                        <v-container>

                                <v-text-field
                                v-model="question"
                                label="새로운 질문"
                            outline
                        ></v-text-field>

                        <v-text-field
                                v-model="answer"
                                label="새로운 답변"
                            outline
                        ></v-text-field>

                        <v-row>
                        <v-btn depressed block @click="qaChange()">질문/답변 변경</v-btn>
                        </v-row>

                    </v-container>
                         </v-card-text>
                         <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn text @click="closebtn()">
                                    Close
                                </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        </div>
                </v-card-actions>
            </v-card>
        </v-col>

    </v-row>
    <br><hr> <br>
  </v-container>
</template>

<script>
export default {
  data: function(){
    return {
        userdata: [],
        nickname:"",
        pw:"",
        pwc:"",
        nick:"",
        dialog:false,
        email:"",
        emailcode:"",
        code:"",
        phone:"",
        question:"",
        answer:"",
        pwo:"",
        dialog2:false,
        mailstate:false,
        dialog3:false,
        nickck:false,
        dialog4:false,
        dialog5:false,
        }
    },
    methods:{
        closebtn(){
            this.pwo = "";
            this.pw = "";
            this.pwc = "";
            this.nick = "";
            this.dialog = false;
            this.dialog2 = false;
            this.dialog3 = false;
            this.dialog4 = false;
            this.dialog5 = false;
            this.email = "";
            this.emailcode = "";
            this.code = "";
            this.phone = "";
            this.question = "";
            this.answer = "";
        },
        qaChange(){
            var params = new URLSearchParams();
            params.append("pwq", this.question);
            params.append("pwqa", this.answer);
            params.append("id", this.userdata[0].id);
            axios.post("/users/qachange", params).then(response => {
                console.log(response.data);
                if(response.data == '1') {
                    alert("질문/답변 변경 성공");
                    this.question = "";
                    this.answer = "";
                    this.dialog5 = false;
                    this.userData();
                }
                else{
                    alert("질문/답변 변경 실패");
                    this.question = "";
                    this.answer = "";
                }
            });
        },
        phoneChange(){
            if(isNaN(this.phone)){
                alert("숫자를 입력해주세요.")
            }
            else{
                var params = new URLSearchParams();
                params.append("phone", this.phone);
                params.append("id", this.userdata[0].id);
                axios.post("/users/phonechange", params).then(response => {
                if(response.data == '1') {
                    alert("전화번호 변경 성공");
                    this.phone = "";
                    this.dialog4 = false;
                    this.userData();
                }
                else {
                    alert("전화번호 변경 실패");
                    this.phone = "";
                }
                });
            }
        },
        nickChange(){
            if(this.nickck){
                var params = new URLSearchParams();
                params.append("nickname", this.nick);
                params.append("id", this.userdata[0].id);
                axios.post("/users/nickchange", params).then(response => {
                if(response.data == '1') {
                    alert("닉네임 변경 성공");
                    this.nick = "";
                    this.nickck = false;
                    this.dialog3 = false;
                    this.userData();
                }
                else {
                    alert("닉네임 변경 실패");
                    this.nick = "";
                    this.nickck = false;
                }
                });
            }
            else{
                alert("이메일 인증번호 확인을 해주세요.");
            }
        },
        nickCheck() {
            var params = new URLSearchParams();
            params.append("nick", this.nick);
            axios.post("/users/nickcheck", params).then(response => {
            var result = response.data;
            if (result == 1) {
                alert("사용가능한 닉네임입니다.");
                this.nickck = true;
            } else {
                alert("이미 사용중인 닉네임입니다.");
                this.nickck = false;
            }
            });
          },
        codecheck(){
            if(this.emailcode == this.code){
                alert("인증번호 확인 완료");
                this.mailstate = true;
            }
            else{
                this.mailstate = true;
                alert("인증번호가 다릅니다.");
            }
        },
        emailChange(){
            if(this.mailstate){
                var params = new URLSearchParams();
                params.append("email", this.email);
                params.append("id", this.userdata[0].id);
                axios.post("/users/emailchange", params).then(response => {
                var result = response.data;
                if (result == '1') {
                    alert("이메일 변경 성공");
                    this.email = "";
                    this.emailcode = "";
                    this.code = "";
                    this.nickck = false;
                    this.dialog2 = false;
                    this.userData();
                } else {
                    alert("이메일 변경 실패");
                    this.email = "";
                    this.emailcode = "";
                    this.code = "";
                    this.nickck = false;
                }
                });
            }
            else{
                alert("이메일 인증번호 확인을 해주세요.");
            }
        },
        emailSend() {
            var params = new URLSearchParams();
            params.append("email", this.email);
            axios.post("/email/emailcheck", params).then(response => {
            var result = response.data;
            if (result == 0) {
                alert("사용 중인 이메일입니다.");
            } else {
                alert("정상적으로 발송되었습니다.");
                this.code = result;
            }
            });
        },
        pwchange(){
            if(this.pwo == this.userdata[0].pw && this.pw == this.pwc){
                if(this.pw.length > 5 && this.pw.length < 19){
                    var params = new URLSearchParams();
                    params.append("id", this.userdata[0].id);
                    params.append("pw", this.pw);
                    axios.post("/users/pwchange", params).then(response =>{
                        if(response.data == '1'){
                            alert("변경 성공");
                            this.pwo = "";
                            this.pw = "";
                            this.pwc = "";
                            this.dialog = false;
                            this.userData();
                        }
                        else{
                            alert("변경 실패");
                            this.pwo = "";
                            this.pw = "";
                            this.pwc = "";
                        }
                    });
                }
                else{
                    alert("비밀번호는 6자리이상 18자리 이하입니다.");
                }
            }
            else{
                alert("비밀번호를 정확히 입력해주세요.");
            }
        },
        userData(){
            var id = this.$cookie.get('uid');
            var params = new URLSearchParams();
            params.append("id", id);
            axios.post("/users/myinfo", params).then(response =>{
                this.userdata = response.data;
                console.log(this.userdata);
            });
        },
    },
    mounted(){
        this.userData();
    },
    components: { }
  }

</script>

<style lang="css">
</style>
