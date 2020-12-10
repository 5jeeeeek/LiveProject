<template>
<v-container style="max-width: 1000px">
  <br><v-row>
    <v-col>
     <h1>좌석 현황</h1>
    </v-col>
   </v-row>

   <v-row>
     <v-col>
       <h3>현재 도서관 좌석 실시간 현황입니다.</h3>
     </v-col>
     
   </v-row>
   <br><hr><br><v-toolbar color="#00C853">
            <v-btn large rounded color="#FFFFFF" @click="sensorData()"><b>제1열람실</b></v-btn>
            <!-- <pre><div>  </div></pre>
            <v-btn dark large rounded color="#00BCD4" @click="boardData2()"><b>제2열람실</b></v-btn>
            <pre><div>  </div></pre>
            <v-btn dark large rounded color="#009688" @click="boardData3()"><b>제3열람실</b></v-btn> -->
            <v-spacer></v-spacer>
            <v-btn
              icon
              large
              color="#FFFFFF"
              @click="sensorData()"
            >
              <v-icon large>mdi-cached</v-icon>
            </v-btn>
        </v-toolbar><br>

    <v-row>
        <v-col>
        <h2>제 1 열람실</h2>
        </v-col>
    </v-row>

    <v-row>
        <v-col>
        <pre><h3>사용 가능 : 52   사용 중/불가 : 3</h3></pre>
        </v-col>
    </v-row>

    <v-row>
        <v-col>
            사용 가능
            <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            </v-btn>
            _ 사용 중
            <v-btn
            small
            depressed
            color="#76FF03"
            >
            </v-btn>
            _ 사용 불가
            <v-btn
            small
            depressed
            color="#EF5350"
            >
            </v-btn>

        </v-col>
    </v-row>


   <v-row> <!--  ---1번째 줄---   -->
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>62</b>
            </v-btn>
      </v-col>
    <v-col cols="1"> 
          <v-btn
            small
            depressed
            color="#EF5350"
            >
            <b>55</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>54</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>41</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>
      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>28</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>15</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>14</b>
            </v-btn>
      </v-col>
    </v-row>



    <v-row>  <!--  ---2번째 줄---   -->
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>61</b>
            </v-btn>
      </v-col>
    <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>56</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>53</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>42</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>40</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>29</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>27</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>16</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>13</b>
            </v-btn>
      </v-col>
    </v-row>




    <v-row>   <!--  ---3번째 줄---   -->
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>60</b>
            </v-btn>
      </v-col>
    <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>57</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>52</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>43</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>39</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>30</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>26</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>17</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>12</b>
            </v-btn>
      </v-col>
    </v-row>




    <v-row>    <!--  ---4번째 줄---   -->
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>59</b>
            </v-btn>
      </v-col>
    <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>58</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>51</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>44</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#EF5350"
            >
            <b>38</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>31</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>25</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>18</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>11</b>
            </v-btn>
      </v-col>
    </v-row>


    
    <br><br>

    
    
    <v-row>    <!--  ---5번째 줄---   -->
      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>
    <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            :color="data[0].color"
            >
            <b>50</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>45</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>37</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>32</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>24</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>19</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>10</b>
            </v-btn>
      </v-col>
    </v-row>


    <v-row>    <!--  ---6번째 줄---   -->
      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>
    <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>49</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>46</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>36</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>33</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>23</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>20</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>9</b>
            </v-btn>
      </v-col>
    </v-row>


    <v-row>    <!--  ---7번째 줄---   -->
      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>
    <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

        <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>48</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>47</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>35</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>34</b>
            </v-btn>
      </v-col>

      <v-col cols="1">
          <pre><div> </div></pre>
      </v-col>

      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>22</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>21</b>
            </v-btn>
      </v-col>
      <v-col cols="1">
          <v-btn
            small
            depressed
            color="#E0E0E0"
            >
            <b>8</b>
            </v-btn>
      </v-col>
    </v-row>


    <br><hr><br>

</v-container>
</template>


<script>

export default {
  data: function(){
    return{
      data:[],

    };
  },
  methods :{
      sensorData(){
          var p = new URLSearchParams();
          axios.post("/iot/sensorlist", p).then(response => {
              this.data = response.data;
              console.log(this.data);
          });
      },
  },
  mounted(){
    this.sensorData();  
  },

  computed: {

  },
  components: {  }
};
</script>

<style scoped>
.v-card {
  transition: opacity .4s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.6;
 }

.show-btns {
  color: rgba(255, 255, 255, 1) !important;
}
</style>
