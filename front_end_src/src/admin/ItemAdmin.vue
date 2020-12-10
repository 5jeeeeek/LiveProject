<template>
    <v-container style="max-width: 1000px">
        <br>
        <v-row>
            <v-col>
            <h1>도서상품 관리</h1>
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
                    v-model="search"
                    label="상품 검색"
                    outlined
                    append-icon="mdi-magnify"
                    clear-icon="mdi-close-circle"
                    clearable
                    type="text"
                    @click:clear="clearMessage()"
                    @click:append="itemSearch()"
                    @keyup.enter="itemSearch()"
                ></v-text-field>
            </v-col>
        </v-row>

        <v-expansion-panels focusable>
          <v-expansion-panel>
            <v-expansion-panel-header>도서 상품 카테고리</v-expansion-panel-header>
            <v-expansion-panel-content>
              <br>

              <v-row>
                <v-btn rounded block outlined color="#3F51B5" 
                  @click="datalist()"><b>전체 상품</b></v-btn>
              </v-row><br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('소설')"><b>소설</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('인문')"><b>인문</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('역사')"><b>역사</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('예술')"><b>예술</b></v-btn>
                </v-col>
              </v-row>
              <br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('사회')"><b>사회</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('과학')"><b>과학</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('경제')"><b>경제</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('자기계발')"><b>자기계발</b></v-btn>
                </v-col>
              </v-row>
              <br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('여행')"><b>여행</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('요리')"><b>요리</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('외국어')"><b>외국어</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#3F51B5"
                  @click="catelist('IT')"><b>IT</b></v-btn>
                </v-col>
              </v-row>


            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>


            <v-card>
                <v-simple-table>
                    <template v-slot:default>
                    <thead>
                        <tr>
                        <th class="text-left">
                            상품 코드
                        </th>
                        <th class="text-left">
                            이름
                        </th>
                        <th class="text-left">
                            카테고리
                        </th>
                        <th class="text-left">
                            출판사
                        </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="item in itemlist"
                            :key="item.icode"
                            @click="openData(item.icode)"
                            style="cursor:pointer"
                        >
                        <td>{{ item.icode }}</td>
                        <td>{{ item.iname }}</td>
                        <td>{{ item.icate }}</td>
                        <td>{{ item.ipublisher }}</td>
                        </tr>
                    </tbody>
                    </template>
                </v-simple-table>
             </v-card>
             <br>
             <v-row>
             <v-spacer></v-spacer>
             <v-btn rounded large outlined color="#FF9800" @click="dialog2 = true"
                  ><b>새로운 상품 추가</b></v-btn>
                  </v-row>
            <br> 

    <div data-app>
    <v-dialog v-model="dialog" persistent max-width="600px">
     <v-toolbar color="#EF6C00">
          <v-toolbar-title><h2>상품 정보</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
        <v-card-title class="headline">
         상품코드 : {{data.icode}}
       </v-card-title>

       <v-card-title class="headline">
         등록 날짜 : {{data.idate}}
       </v-card-title>

       <v-card-title class="headline">
         리뷰 평점 : {{data.iratingavg}}
       </v-card-title>

       <v-card-title class="headline">
         리뷰 개수 : {{data.ireview}}
       </v-card-title>

       <v-card-text>
         <v-textarea
          label="상품 이름"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.iname"
        ></v-textarea>

        <v-textarea
          label="카테고리"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.icate"
        ></v-textarea>

        <v-textarea
          label="키워드"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.ikeyword"
        ></v-textarea>

        <v-textarea
          label="내용"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.itext"
        ></v-textarea>

        <v-textarea
          label="출판사"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.ipublisher"
        ></v-textarea>

        <v-textarea
          label="재고 수량"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="data.icount"
        ></v-textarea>

        <v-img
              height="600"
              contain
              :src="'http://minback.com/upimg/'+data.iimage"
            ></v-img>

       <br><br><hr><br><br>

        <form> 
           <input type="file" id="ex_file" ref='uploadImageFile'
            @change="onFileSelected()" accept="image/*"> 
        </form>

        <br>

        <v-btn rounded block dark color="#673AB7"
        @click="imgChange()"><b>이미지 변경하기</b></v-btn>

       </v-card-text>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded outlined large dark color="#4CAF50" 
         @click="itemUpdate()"><b>상품 수정하기</b></v-btn>
         <pre><div>  </div></pre>

         <v-btn rounded outlined large dark color="#DD2C00" 
         @click="itemdel()"><b>상품 삭제하기</b></v-btn>
         <pre><div>  </div></pre>

         <v-btn rounded outlined large dark color="#000000" 
         @click="addClose()"><b>닫기</b></v-btn>
       </v-card-actions>    
       </v-col>
     </v-card>
   </v-dialog>
   </div>


   <div data-app>
    <v-dialog v-model="dialog2" persistent max-width="600px">
     <v-toolbar color="#EF6C00">
          <v-toolbar-title><h2>새로운 상품 추가하기</h2></v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="addClose2()">
            <v-icon>mdi-close-circle</v-icon>
          </v-btn>
        </v-toolbar>
     <v-card>
       <v-col>
        <v-card-title class="headline">
         새로운 상품 추가하기
       </v-card-title>

       <v-card-text>
         <v-textarea
          label="상품 이름"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="new_name"
        ></v-textarea>

        <v-select :items="catelist_data" label="상품 카테고리"
          chips v-model="new_cate"></v-select>

        <v-textarea
          label="키워드"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="new_keyword"
        ></v-textarea>

        <v-textarea
          label="내용"
          outlined
          v-model="new_text"
        ></v-textarea>

        <v-textarea
          label="출판사"
          auto-grow
          outlined
          rows="1"
          row-height="3"
          v-model="new_pub"
        ></v-textarea>

        <form> 
           <input type="file" id="ex_file2" ref='new_img'
            @change="onFileSelected2()" accept="image/*"> 
        </form>

       </v-card-text>

       <v-card-actions>
         <v-spacer></v-spacer>
         <v-btn rounded outlined large dark color="#4CAF50" 
         @click="addCheck()"><b>상품 추가하기</b></v-btn>
         <pre><div>  </div></pre>
         <v-btn rounded outlined large dark color="#000000" 
         @click="addClose2()"><b>닫기</b></v-btn>
       </v-card-actions>    
       </v-col>
     </v-card>
   </v-dialog>
   </div>




   <div class="text-center">
        <v-pagination
        color="#3F51B5"
        v-model="num"
        :length="len"
        ></v-pagination>
    </div>

  </v-container>
</template>


<script>
export default {
    data: function(){
        return{
            catelist_data: ["소설", "인문", "역사", "예술", "사회", "과학", "경제", "자기계발", 
                        "여행", "요리", "외국어", "IT"],
            list: ["전체", "이름", "내용", "출판사"],
            data: [],
            select:"",
            search:"",
            dialog:false,
            dialog2:false,
            itemlist: [],
            searchlist: [],
            num:1,
            len:0,
            icate:"",
            istate:0, // 0은 전체, 1은 카테고리, 2는 검색
            uploadImageFile:"",
            image:"",
            new_name:"",
            new_text:"",
            new_cate:"",
            new_keyword:"",
            new_img:"",
            new_img2:"",
            new_pub:"",
        };
    },
    methods:{
        itemdel(){
            var result = confirm("상품을 삭제 하시겠습니까?");
            if(result){
                var p = new URLSearchParams();
                p.append("icode", this.data.icode);
                axios.post("/item/itemdel", p).then(response => {
                    if(response.data == '1'){
                        alert("상품 삭제 완료");
                        this.addClose();
                        this.datalist();
                    }
                    else{
                        console.log(response.data);
                    }
                });
            }
        },
        addCheck(){ // 상품 정보 입력 체크
            if(this.new_name == "" || this.new_text == "" || this.new_cate == "" || 
            this.new_keyword == "" || this.new_pub == ""){
                alert("상품 정보를 모두 입력해주세요.");
            }
            else{
                this.new_imgAdd();
            }
        },
        new_imgAdd(){ //상품 추가 시 이미지 업로드
            if(this.new_img != ""){
                const fd = new FormData(); //반드시 필요 
                fd.append('img', this.new_img); //4번 
                axios.post('/imgupload/boardimg', fd).then(response => {
                    this.new_img2 = response.data.filename;
                    console.log(response.data);
                    if(this.new_img2 != ""){
                        this.itemAdd();
                    }
                });
            }
            else{
                alert("이미지를 선택해주세요.");
            }
        },
        itemAdd(){ // 상품 추가
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
            var bcdate = String(yy) + "." + mm + "." + dd;
            var p = new URLSearchParams();
            p.append("iname", this.new_name);
            p.append("itext", this.new_text);
            p.append("icate", this.new_cate);
            p.append("ikeyword", this.new_keyword);
            p.append("ipublisher", this.new_pub);
            p.append("idate", bcdate);
            p.append("iimage", this.new_img2);
            axios.post("/item/itemadd", p).then(response => {
                if(response.data == '1'){
                    alert("상품 추가 완료");
                    this.addClose2();
                    this.datalist();
                }
                else{
                    alert("글 작성 실패");
                    console.log(response.data);
                }
            });
        },
        itemUpdate(){ // 상품 수정
            var p = new URLSearchParams();
            p.append("icode", this.data.icode);
            p.append("iname", this.data.iname);
            p.append("itext", this.data.itext);
            p.append("icate", this.data.icate);
            p.append("ikeyword", this.data.ikeyword);
            p.append("ipublisher", this.data.ipublisher);
            p.append("icount", this.data.icount);
            axios.post("/item/itemupdate", p).then( response => {
                if(response.data == '1'){
                    alert("아이템 수정 완료");
                    this.dialog = false;
                }
                else{
                    console.log(response.data);
                }
            })
        },
        imgChange(){ //이미지 삭제
            if(this.uploadImageFile != ""){
                var result = confirm("이미지를 변경하시겠습니까?");
                if(result){
                    var p = new URLSearchParams();
                    p.append("img", this.data.iimage);
                    axios.post("/imgupload/itemimgdel", p).then(response => {
                        if(response.data == '1'){
                            this.onSave();
                        }
                        else{
                            console.log(response.data);
                        }
                    });
                }
            }
            else{
                alert("이미지를 선택해주세요.");
            }
        },
        onFileSelected(event){ 
            this.uploadImageFile = this.$refs.uploadImageFile.files[0]
        },
        onFileSelected2(event){ 
            this.new_img = this.$refs.new_img.files[0]
        },
        onSave(){ //이미지 업로드 및 데이터베이스 수정 함수 호출
            if(this.uploadImageFile != ""){
                const fd = new FormData(); //반드시 필요 
                fd.append('img', this.uploadImageFile); //4번 
                axios.post('/imgupload/boardimg', fd).then(response => {
                    this.data.iimage = response.data.filename;
                    console.log(response.data);
                    this.uploadImageFile = "";
                    this.imgUpdate(response.data.filename);
                });
            }
            else{
                alert("이미지를 선택해주세요.");
            }
        },
        imgUpdate(img_name){ //이미지 이름 업데이트
            var p = new URLSearchParams();
            p.append("iimage", img_name);
            p.append("icode", this.data.icode);
            axios.post("/item/itemimgadd", p).then(response => {
                if(response.data == '1'){
                    alert("이미지 변경 성공");
                }
                else{
                    console.log(response.data);
                }
            });
        },
        openData(code){
            for(var n = 0; n < this.itemlist.length; n++){
                if(this.itemlist[n].icode == code){
                    this.data = this.itemlist[n];
                }
            }
            this.dialog = true;
        },
        clearMessage(){ // 검색창 초기화
            this.search = "";
        },
        blackdata(){
            var p = new URLSearchParams();
            axios.post("/users/blacklist", p).then(response => {
                this.itemlist = response.data;
                console.log(response.data);
            });
        },
        addClose(){
            this.dialog = false;
            this.uploadImageFile = "";
        },
        addClose2(){
            this.dialog2 = false;
            this.new_name = "";
            this.new_text = "";
            this.new_cate = "";
            this.new_keyword = "";
            this.new_img = "";
            this.new_img2 = "";
            this.new_pub = "";
        },
        datalist(){ // 전체 상품 리스트
            var p = new URLSearchParams();
            p.append("number", this.num);
            axios.post("/item/itemlist", p).then(response => {
            this.itemlist = response.data;
            this.itemlengthall();
            console.log(this.itemlist);
            this.istate = 0;
            });
        },
        itemlength(lens){ // 카테고리 별 길이 요청
            var params = new URLSearchParams();
            params.append("icate", lens);
            axios.post("/item/itemlen", params).then(response => {
                var num1 = parseInt(response.data[0].cnt) / 9;
                var num2 = num1 % 9;
                if(num2 > 0){
                    this.len = num1 + 1;
                }
                else{
                    this.len = num1;
                }
            });
      },
      itemlengthall(){ //전체 상품 길이 요청
            var params = new URLSearchParams();
            axios.post("/item/itemlenall", params).then(response => {
                var num1 = parseInt(response.data[0].cnt) / 9;
                var num2 = num1 % 9;
                if(num2 > 0){
                    this.len = num1 + 1;
                }
                else{
                    this.len = num1;
                }
            });
      },
      itemSearch(){ // 상품 검색
            if(this.search == ""){
                alert("검색어를 입력해주세요.");
            }
            else{
                var url = "";
                if(this.select == "전체"){
                    url = "/item/itemsearch";
                }
                else if(this.select == "이름"){
                    url = "/item/namesearch";
                }
                else if(this.select == "내용"){
                    url = "/item/textsearch";
                }
                else if(this.select == "출판사"){
                    url = "/item/publishersearch";
                }
                var p = new URLSearchParams();
                p.append("itemsearch", this.search);
                axios.post(url , p).then(response => {
                  if(response.data.length == 0){
                    alert("검색된 도서상품이 없습니다.");
                  }
                  else{
                    var num1 = parseInt(response.data.length) / 9;
                    var num2 = num1 % 9;
                    if(num2 > 0){
                        this.len = num1 + 1;
                    }
                    else{
                        this.len = num1;
                    }
                    this.searchlist = response.data;
                    this.istate = 2;
                    this.pageItem();
                  }
                });
            }
      },
      pageItem: function(){
        this.itemlist = this.searchlist.slice(
          (this.num - 1) * 9,
          this.num * 9
        )
      },
      catelist(cate){ // 카테고리 상품 리스트
        this.icate = cate;
        var p = new URLSearchParams();
        p.append("number", this.num);
        p.append("icate", cate);
        axios.post("/item/itemcatelist", p).then(response => {
          this.itemlist = response.data;
          console.log(this.itemlist);
          this.istate = 1;
          this.itemlength(cate);
        });
      },
    },
    mounted(){
        this.datalist();
    },
    watch:{
        num:function(){
          if(this.istate == 0){
            this.datalist();
          }
          else if(this.istate == 1){
            this.catelist(this.icate);
          }
          else if(this.istate == 2){
            this.pageItem();
          }
        },
    }
}
</script>