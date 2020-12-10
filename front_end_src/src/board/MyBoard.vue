<template>
    <v-container style="max-width: 1050px">
        <br><v-row>
            <v-col>
                <h1>My Board Page</h1>
            </v-col>
        </v-row><br>

        <v-row>
            <v-col>
                <h3>{{nick}}님이 작성한 게시물입니다.</h3>
            </v-col>
        </v-row><br><hr><br>

        <v-card>
            <v-card-title>
                내 글 목록
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
                @click:row="boardmove"
                style="cursor:pointer"
            ></v-data-table>
        </v-card>

    <br><hr><br>
    </v-container>
</template>


<script>
  export default {
    data: () => ({
        nick:"",
        search: '',
        headers: [
          {
            text: '글 번호',
            align: 'start',
            sortable: false,
            value: 'bcode',
          },
          { text: '글 제목', value: 'btitle' },
          { text: '작성 닉네임', value: 'bnickname' },
          { text: '좋아요', value: 'blike' },
          { text: '댓글', value: 'bcomment' },
          { text: '게시일', value: 'bdate' },
        ],
        desserts: [],
    }),
    mounted(){
        this.nick = this.$cookie.get('memberNick');
        this.boarddata();
    },
    methods:{
        boardmove(value){
            this.$router.push("/board/boarddetail/" + value.bcode);
        },
        boarddata(){
            var p = new URLSearchParams();
            p.append("id", this.$cookie.get('uid'));
            axios.post("/users/myboard", p).then(response => {
                this.desserts = response.data;
                console.log(this.desserts);
            });
        },
    }
  }
</script>
