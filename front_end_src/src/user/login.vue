<template>
  <v-container style="max-width: 350px">
      <v-row align="center" justify="center">
        <v-card width="350px">
          <v-toolbar>
          <v-card-title>
            <span>Login</span>
          </v-card-title>
        </v-toolbar>

          <v-card-text>
            <v-flex v-text="'ID'"></v-flex>
            <v-text-field label="ID" v-model="id" @keyup.enter="onAdd()" solo></v-text-field>
            <v-flex v-text="'Password'"></v-flex>
            <v-text-field label="Password" v-model="pw" :type="show1 ? 'text' : 'password'"
            @keyup.enter="onAdd()" solo></v-text-field>

            <v-card-actions>
                
                <div data-app>
                <v-dialog
                v-model="dialog"
                persistent
                max-width="500px"
                >
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                    dark
                    v-bind="attrs"
                    v-on="on"
                    >
                    아이디 찾기
                    </v-btn>
                </template>
                <v-card>
                    <v-card-title>
                    <span class="headline">회원 아이디 찾기</span>
                    </v-card-title>
                    <v-card-text>
                    <v-container>
 
                <v-row>
                  <v-col>
                    <v-text-field
                    v-model="name"
                    label="이름"
                   outline
               ></v-text-field>
                  </v-col>
                </v-row>

              <v-row>
                <v-col>
                  <v-text-field
                  v-model="email"
                  label="가입 이메일"
                 outline
             ></v-text-field>
                </v-col>
              </v-row>
              <v-row>

            <v-btn depressed block @click="userfindid()">아이디 찾기</v-btn>
            </v-row>

            <v-row>
              <v-col>
                <v-card-text class="headline" v-if="idtext">
                  회원님이 가입한 아이디는 {{data}} 입니다.
                </v-card-text>
              </v-col>
            </v-row>
        </v-container>

                    <small>*indicates required field</small>
                    </v-card-text>
                    <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="closebtn()"
                    >
                        Close
                    </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            </div>

              <v-spacer></v-spacer>
              

              <div data-app>
                <v-dialog
                v-model="dialog2"
                persistent
                max-width="500px"
                >
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                    dark
                    v-bind="attrs"
                    v-on="on"
                    >
                    비밀번호 찾기
                    </v-btn>
                </template>
                <v-card>
                    <v-card-title>
                    <span class="headline">회원 비밀번호 찾기</span>
                    </v-card-title>
                    <v-card-text>
                    <v-container>
 
                <v-row>
                  <v-col>
                    <v-text-field
                    v-model="idf"
                    label="가입 아이디"
                   outline
               ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                    <v-col>
                    <v-text-field
                    v-model="name"
                    label="이름"
                    outline
                ></v-text-field>
                    </v-col>
                </v-row>

                <v-row>
                <v-btn depressed block @click="pwfind()">비밀번호 찾기</v-btn>
                </v-row>

                <v-row>
                <v-col>
                    <v-card-text class="headline" v-if="pwtext">
                    회원님의 비밀번호 찾기 질문은 {{data}} 입니다.
                    </v-card-text>
                </v-col>
                </v-row>

                <v-row>
                  <v-col>
                    <v-text-field
                    v-if="pwtext"
                    v-model="userAnswer"
                    label="답변"
                   outline
               ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                <v-btn v-if="pwtext" depressed block @click="qaCheck()">질문 답변 확인</v-btn>
                </v-row>

                <v-row>
                  <v-col>
                    <v-text-field
                    v-if="pwtext && pwUpdate"
                    v-model="pw1"
                    label="새로운 비밀번호"
                   outline
               ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col>
                    <v-text-field
                    v-if="pwtext && pwUpdate"
                    v-model="pw2"
                    label="비밀번호 확인"
                   outline
               ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                <v-btn v-if="pwtext && pwUpdate" depressed block @click="pwchange()">
                    비밀번호 재설정</v-btn>
                </v-row>


            </v-container>

                        <small>*indicates required field</small>
                        </v-card-text>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="closebtn2()"
                        >
                            Close
                        </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                </div>


            </v-card-actions>

            <v-card-actions>
              <v-btn color="#89c5f0" depressed block @click="onAdd()">Login</v-btn>
            </v-card-actions>

            <v-card-actions>
              <v-btn color="#89c5f0" depressed block @click="$router.push('/user/join')">join Us</v-btn>
            </v-card-actions>
          </v-card-text>

        </v-card>
      </v-row>
  </v-container>
</template>

<script>
export default {
  data: function() {
    return {
        dialog: false,
        dialog2: false,
        show1:"",
        id: "",
        pw: "",
        idtext:false,
        name:"",
        email:"",
        data:"",
        idf:"",
        answer:"",
        pwtext: false,
        userAnswer:"",
        pwUpdate:false,
        pw1:"",
        pw2:""
    }
  },
  methods: {
      pwchange(){
          if(this.pw1 == this.pw2){
              if(this.pw1.length > 6 && this.pw1.length < 19){
                  var params = new URLSearchParams();
                params.append("id", this.idf);
                params.append("pw", this.pw1);
                axios.post("/users/pwchange", params).then(response => {
                    if(response.data == '1'){
                        alert("비밀번호 변경 성공");
                        this.closebtn2();
                    }
                    else{
                        console.log(response.data);
                        alert("비밀번호 변경 실패");
                        this.closebtn2();
                    }
                });
              }
              else{
                  alert("비밀번호는 5자리 이상 18자리 이하여야 합니다.");
              }
          }
          else{
              alert("입력 비밀번호가 서로 다릅니다.")
          }
      },
      qaCheck(){
          if(this.userAnswer == this.answer){
              alert("질문 답변 확인완료");
              this.pwUpdate = true;
          }
          else{
              alert("질문에 대한 답변이 옳지 않습니다.");
          }
      },
      closebtn2(){
          this.dialog = false;
          this.dialog2 = false;
          this.idf = "";
          this.answer = "";
          this.pwtext = false;
          this.data = "";
          this.userAnswer = "";
          this.pwUpdate = false;
          this.pw1 = "";
          this.pw2 = "";
          this.name = "";
      },
      pwfind(){
          var params = new URLSearchParams();
          params.append("id", this.idf);
          params.append("name", this.name);
          axios.post("/users/pwfind", params).then(response => {
              if(response.data == 0){
                  alert("입력정보에 해당하는 회원정보가 없습니다.");
              }
              else{
                  this.data = response.data[0];
                  this.answer = response.data[1];
                  this.pwtext = true;
              }
          });
      },
      closebtn(){
          this.dialog = false;
          this.dialog2 = false;
          this.name = "";
          this.email = "";
          this.idtext = false;
          this.data = "";
      },
      userfindid: function() {
        if(this.name == "" || this.email == ""){
            alert("이름과 가입 이메일을 입력해주세요.")
        }
        else{
            var params = new URLSearchParams();
            params.append("name", this.name);
            params.append("email", this.email);
            axios.post("/users/idfind", params).then(response => {
                if(response.data == 0){
                    alert("입력정보에 해당하는 아이디가 없습니다.");
                }
                else{
                    this.idtext = true;
                    this.data = response.data;
                }
            });
        }
       
     },
    onAdd: function() {
      if(this.id != "" && this.pw != ""){
          var params = new URLSearchParams();
          params.append("id", this.id);
          params.append("pw", this.pw);
          axios.post("/users/login", params).then(response => {
              var result = response.data;
              if (result == 2) {
                alert("비밀번호가 틀렸습니다.");
              }
              else if (result == 3) {
                alert("회원정보가 없습니다.");
              }
              else if(result == 4){
                alert("블랙리스트 회원입니다.");
              }
              else{
                alert("로그인 성공");
                this.$cookie.set("memberNick", result[1] , '3h');
                this.$cookie.set("uid", result[0], '3h');
                this.$cookie.set("admin", result[2], '3h');
                location.href = "/";
              }
          });
      }
      else{
        alert("아이디와 비밀번호를 입력해주세요.");
      }
      
    }
  },
  computed: {

  },
  components: { },
};
</script>

<style lang="css">
</style>
