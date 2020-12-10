<template>
  <v-container>

    <br>
        <v-row>
            <v-col>
            <h1>Live Item</h1>
            </v-col>
        </v-row><br>

        <v-row>
            <v-col>
                <v-text-field
                    v-model="search"
                    label="도서상품 검색"
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
                <v-btn rounded block outlined color="#000000" 
                  @click="datalist()"><b>전체 상품</b></v-btn>
              </v-row><br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('소설')"><b>소설</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('인문')"><b>인문</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('역사')"><b>역사</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('예술')"><b>예술</b></v-btn>
                </v-col>
              </v-row>
              <br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('사회')"><b>사회</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('과학')"><b>과학</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('경제')"><b>경제</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('자기계발')"><b>자기계발</b></v-btn>
                </v-col>
              </v-row>
              <br><hr><br>

              <v-row>
                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('여행')"><b>여행</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('요리')"><b>요리</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('외국어')"><b>외국어</b></v-btn>
                </v-col>

                <v-col cols="3">
                  <v-btn rounded large outlined color="#000000" 
                  @click="catelist('IT')"><b>IT</b></v-btn>
                </v-col>
              </v-row>


            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>

    <v-row>
      <template v-for="(item, i) in itemlist">
        <v-col :key="i" cols="4">
          <v-card
            class="mx-auto my-12"
            max-width="370"
          >
            <v-img
              height="450"
              contain
              :src="'http://minback.com/upimg/'+item.iimage"
            ></v-img><br>

              <h3>{{item.iname}}</h3>

              <v-card-subtitle>
                <p style="text-align:center">{{item.icate}}</p>
              </v-card-subtitle>
              <v-card-text>
                  <v-row>
                    <v-rating
                      :value="item.iratingavg"
                      color="#FF9100"
                      dense
                      half-increments
                      readonly
                      size="18"
                    ></v-rating>

                    <div class="grey--text ml-4">
                      {{item.iratingavg}} ({{item.ireview}})
                    </div>
                  </v-row>
              </v-card-text>
            <v-divider class="mx-4"></v-divider>

          <v-card-actions>
            <v-btn rounded large outlined color="#3F51B5" @click="addOpen(item.icode)"><b>상품 이동</b></v-btn>
            <v-spacer></v-spacer>
            더보기
            <v-btn
                icon
                @click="show = !show"
              >
                <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
              </v-btn>
            </v-card-actions>

            <v-expand-transition>
              <div v-show="show">
                <v-divider></v-divider>
                <v-card-text>
                <p style="text-align:center">출판사 : {{item.ipublisher}}</p>
            </v-card-text>
                <v-card-text>
                  {{item.itext}}
                </v-card-text>
              </div>
            </v-expand-transition>
          </v-card>
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

  </v-container>
</template>


<script>
  export default {
    data: () => ({
      selection: 1,
      show: false,
      itemlist:[],
      searchlist:[],
      num:1,
      search:"",
      len:0,
      icate:"",
      istate:0, // 0은 전체, 1은 카테고리, 2는 검색
    }),

    methods: {
      addOpen(code){
          this.$router.push("/item/itemdetail/" + code);
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
      clearMessage(){ // 검색 ㅊ
            this.search = "";
      },
      itemSearch(){ // 상품 검색
            if(this.search == ""){
                alert("검색어를 입력해주세요.");
            }
            else{
                var p = new URLSearchParams();
                p.append("itemsearch", this.search);
                axios.post("/item/itemsearch", p).then(response => {
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
          this.itemlength(cate);
          console.log(this.itemlist);
          this.istate = 1;
        });
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
    },
    created(){
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