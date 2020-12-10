<template>
<v-container style="max-width: 600px">
  <v-row>
    <v-col>
    <v-form
            ref="form"
            v-model="valid"
            lazy-validation
        >
      <v-card width="600px">
          
        <v-card-text>
          
        <v-card-actions>
          <v-text-field
            v-model="id"
            :rules="[
                () => !!id || 'ID를 입력해주세요.',
                () => !!id && id.length >= 6 || '아이디는 5자리가 넘어야합니다.',
                () => !!id && id.length <= 18 || '아이디는 18자리가 넘을 수 없습니다.',
            ]"
            label="ID"
            placeholder="아이디"
            required
          ></v-text-field>
          <v-spacer></v-spacer>
           <v-btn dark color="#2979FF" @click="idCheck()">ID 중복 확인</v-btn>
           </v-card-actions>

          <v-text-field
            v-model="pw"
            :rules="[
              () => !!pw || '비밀번호를 입력해주세요.',
              () => !!pw && pw.length >= 6 || '비밀번호는 6자리가 넘어야합니다.'
            ]"
            label="Password"
            placeholder="비밀번호"
            counter="18"
            required
            type="password"
          ></v-text-field>

        <v-card-actions>
          <v-text-field

            v-model="pwc"
            :rules="[
              () => !!pwc || '비밀번호 확인을 입력해주세요.',
              () => !!pwc && pwc.length >= 6 || '비밀번호는 6자리가 넘어야합니다.',
              () => !!pwc && pwc.length <= 18 || '비밀번호는 18자리 이하여야합니다.',
            ]"
            label="PasswordCheck"
            placeholder="비밀번호 확인"
            counter="18"
            required
            type="password"
          ></v-text-field>
          <v-spacer></v-spacer>
          <v-btn dark color="#2979FF" @click="pwCheck()">비밀번호 확인</v-btn>
           </v-card-actions>


          <v-text-field
            v-model="name"
            :rules="[
                () => !!name || '성명을 입력해주세요.',
                () => !!name && name.length >= 1 || '이름은 1자리가 넘어야합니다.',
                () => !!name && name.length <= 10 || '이름은 10자리가 넘을 수 없습니다.',
            ]"
            label="이름"
            placeholder="성명"
            required
          ></v-text-field>

        <v-card-actions>
          <v-text-field
            v-model="nickname"
            :rules="[
                () => !!nickname || '닉네임을 입력해주세요.',
                () => !!nickname && nickname.length >= 1 || '닉네임은 1자리가 넘어야합니다.',
                () => !!nickname && nickname.length <= 18 || '닉네임은 18자리가 넘을 수 없습니다.',
            ]"
            label="닉네임"
            required
            placeholder="닉네임"
          ></v-text-field>
          <v-spacer></v-spacer>
          <v-btn dark color="#2979FF" @click="nickCheck()">닉네임 중복확인</v-btn>
           </v-card-actions>


           <v-text-field
            v-model="phone"
            :rules="[() => !!phone || '휴대전화를 입력해주세요.']"
            label="휴대전화"
            placeholder="휴대전화"
            required
          ></v-text-field>

        <v-card-actions>
          <v-text-field
            v-model="email"
            :rules="[
                () => !!email || '이메일을 입력해주세요.',
                () => !!(email || '').match(/@/) || '이메일 형식에 맞게 작성해주세요.',
            ]"
            label="email"
            required
            placeholder="이메일"
          ></v-text-field>
          <v-spacer></v-spacer>
          <v-btn dark color="#2979FF" @click="emailSend()">이메일 인증요청</v-btn>
           </v-card-actions>


          <v-card-actions>
          <v-text-field
            v-model="codecheck"
            :rules="[() => !!codecheck || '인증번호를 입력해주세요.']"
            label="인증번호"
            required
            placeholder="이메일 인증번호"
            type="password"
          ></v-text-field>
          <v-spacer></v-spacer>
          <v-btn dark color="#2979FF" @click="emailcodeCheck()">인증번호 확인</v-btn>
           </v-card-actions>

          <v-text-field
            v-model="question"
            :rules="[() => !!question || '본인확인 질문을 입력해주세요']"
            label="비밀번호 찾기 질문"
            placeholder="비밀번호 찾기 질문"
            required
          ></v-text-field>

          <v-text-field
            v-model="answer"
            :rules="[() => !!answer || '본인확인 답변을 입력해주세요']"
            label="비밀번호 찾기 답변"
            placeholder="비밀번호 찾기 답변"
            required
          ></v-text-field>


          <v-checkbox
            v-model="checkbox"
            :rules="[v => !!v || '체크 해주세요.']"
            label="회원가입을 동의하십니까?"
            required
            ></v-checkbox>


        </v-card-text>

        <v-divider class="mt-12"></v-divider>
        <v-card-actions>
          <v-btn dark color="#2979FF">
            메인화면
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            class="mr-4"
            @click="reset"
            dark color="#2979FF"
            >
            초기화
            </v-btn>
          <v-btn
            @click="submit"
            dark color="#2979FF"
          >
            회원가입
          </v-btn>
        </v-card-actions>
      </v-card>
      </v-form>
    </v-col>
  </v-row>
  </v-container>
</template>





<script>
  export default {
    data: () => ({
        valid:true,
        checkbox:null,
        name: null,
        id: null,
        nickname: null,
        email: null,
        codecheck: null,
        pw: null,
        pwc: null,
        phone: null,
        question: null,
        answer: null,
        formHasErrors: false,
        code:null,
        pwck:false,
        idck:false,
        nickck:false,
        mailck:false,
    }),
    computed: {

    },
    methods: {
        userAdd() {
            var params = new URLSearchParams();
            params.append("id", this.id);
            params.append("pw", this.pw);
            params.append("email", this.email);
            params.append("name", this.name);
            params.append("nickname", this.nickname);
            params.append("phone", this.phone);
            params.append("question", this.question);
            params.append("answer", this.answer);
            axios.post("/users/join", params).then(response => {
                var result = response.data;
                if(result == 1){
                    alert("회원 가입이 완료되었습니다.");
                    location.href = "/";
                }
                else{
                    alert("회원가입 실패");
                }
            });
        },
        idCheck: function(){
            var params = new URLSearchParams();
            params.append("id", this.id);
            axios.post("/users/idcheck", params).then(response => {
            var result = response.data;
            console.log(result);
            if (result == 1) {
              alert("사용가능한 아이디입니다.");
              this.idck = true;
            } else {
              alert("이미 사용중인 아이디입니다.");
              this.idck = false;
            }
          });
        },
        nickCheck() {
            var params = new URLSearchParams();
            params.append("nick", this.nickname);
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
        emailcodeCheck(){
            if(this.codecheck == this.code){
                alert("인증번호 확인이 완료되었습니다.")
                this.mailck = true;
            }
            else{
                alert("인증번호가 맞지 않습니다.")
                this.mailck = false;
            }
        },
        pwCheck(){
            if(this.pw == this.pwc){
                alert("비밀번호 확인이 완료되었습니다.")
                this.pwck = true;
            }
            else{
                alert("비밀번호가 서로 다릅니다. 다시 입력해주세요.")
                this.pwck = false;
            }
        },

        reset () {
            this.$refs.form.reset()
        },
        submit () {
            if(this.$refs.form.validate()){
                this.userAdd();
            }
            else{
                alert("회원가입 양식을 제대로 입력 해주세요.")
            }
        },
    },
  }
</script>