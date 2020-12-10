var express = require('express');
var router = express.Router();


router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express userpage' });
});

var mysql = require("mysql");

var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});

/* GET users listing. */
router.get('/testg', function(req, res, next) {
  res.send('1');
});

router.post('/testp', function(req, res, next) {
  res.send('1');
});

// 회원가입
router.post('/join', (req, res, next) => {
  client.query("insert into user (id, pw, email, name, nickname, phone, adminstate, question, answer, blackstate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
  [req.body.id, req.body.pw, req.body.email, req.body.name, req.body.nickname, req.body.phone, 0, req.body.question, req.body.answer, 0],
   function(err){
     if(err){
       res.send(err); 
     }
     else{
       res.send('1');
     }
  });
});

// id 중복확인
router.post('/idcheck', (req, res, next) => {
  var id = req.body.id;
  client.query("select * from user where id = ?;",[id], function(err, result, fields){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('1');
      }
      else{
        res.send('0');
      }
    }
  });
});

// 닉네임 중복확인
router.post('/nickcheck', (req, res, next) => {
  var nick = req.body.nick;
  client.query("select * from user where nickname = ?;",[nick], function(err, result, fields){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('1');
      }
      else{
        res.send('0');
      }
    }
  });
});

// 로그인
router.post('/login', (req, res, next) => {
  var id = req.body.id;
  var pw = req.body.pw;
  client.query("select * from user where id=?;",[id], function(err, result, fields){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('3'); //아이디 없음
      }
      else{
        var response = result[0];
            if(response.blackstate == 0){
              if(response.pw == pw){
                var data = [response.id, response.nickname, response.adminstate];
                res.send(data); //로그인 성공 id를 보냄
              }
              else{
                res.send('2'); //비밀번호 틀림
              }
            }
            else{
              res.send('4'); // 블랙리스트 회원임
            }
        }
    }
  });
});

// 회원정보 가져오기
router.post('/myinfo', (req, res, next) => {
  client.query("select * from user where id = ?;", [req.body.id] , function(err, result, fields){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send("0");
      }
      else{
        res.json(result);
      }
    }
  });
});

// 유저 체크 -> 닉네임을 보냄
router.post('/usercheck', (req, res, next) => {
  client.query("select nickname from user where id = ?;", [req.body.id], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


// 유저 닉네임 변경
router.post('/nickchange', (req, res, next) => {
  client.query("update user set nickname = ? where id = ?;", [req.body.nickname, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 비밀번호 변경
router.post('/pwchange', (req, res, next) => {
  client.query("update user set pw = ? where id = ?;", [req.body.pw, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 이메일 변경
router.post('/emailchange', (req, res, next) => {
  client.query("update user set email = ? where id = ?;", [req.body.email, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 잔화번호 변경
router.post('/phonechange', (req, res, next) => {
  client.query("update user set phone = ? where id = ?;", [req.body.phone, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 비밀번호 힌트 질문 / 답변 변경
router.post('/qachange', (req, res, next) => {
  client.query("update user set question = ?, answer = ? where id = ?;", [req.body.pwq, req.body.pwqa, req.body.id],
   function(){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 회원탈퇴
router.post('/userout', (req, res, next) => {
  client.query("delete from user where id = ?;", [req.body.id] , function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 유저 내가 쓴 글
router.post('/myboard', (req, res, next) => {
  client.query("select * from board where id = ? order by bcode DESC;", [req.body.id], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 유저 내가 쓴 글에서 검색
router.post('/myboardsearch', (req, res, next) => {
  var data = "%" + req.body.myboardsearch + "%";
  client.query("select * from board where (id = ? and btitle like ?) or (id = ? and btext like ?);",
  [req.body.id, data, req.body.id, data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 회원 아이디 찾기 -> 아이디, 이름으로 검색
router.post('/idfind', (req, res, next) => {
  client.query("select * from user where name = ? and email = ?;", [req.body.name, req.body.email], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('0'); //검색된 데이터가 없음
      }
      else{
        var response_data = result[0];
        var data = [response_data.id];
        res.send(data); //회원의 아이디를 보내줌
      }
    }
  });
});


// 회원 질문답변 찾기 -> 아이디, 이름으로 검색
router.post('/pwfind', (req, res, next) => {
  client.query("select * from user where id = ? and name = ?;", [req.body.id, req.body.name], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('0'); //검색된 데이터가 없음
      }
      else{
        var response_data = result[0];
        var data = [response_data.question, response_data.answer];
        res.send(data); //회원의 질문과 답변을 보내줌
      }
    }
  });
});


// 회원 검색하기 -> 전체
router.post('/alluser', (req, res, next) => {
  var data = "%" + req.body.search + "%";
  client.query("select * from user where id like ? or name like ? or nickname like ? or email like ?;",
  [data, data, data, data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


// 회원 검색하기 -> 이름
router.post('/nameuser', (req, res, next) => {
  var data = "%" + req.body.search + "%";
  client.query("select * from user where name like ?;",
  [data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 회원 검색하기 -> 아이디
router.post('/iduser', (req, res, next) => {
  var data = "%" + req.body.search + "%";
  client.query("select * from user where id like ?;",
  [data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 회원 검색하기 -> 닉네임
router.post('/nickuser', (req, res, next) => {
  var data = "%" + req.body.search + "%";
  client.query("select * from user where nickname like ?;",
  [data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 회원 검색하기 -> 이메일
router.post('/emailuser', (req, res, next) => {
  var data = "%" + req.body.search + "%";
  client.query("select * from user where email like ?;",
  [data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


// 블랙리스트 회원으로 변경
router.post('/blackuser', (req, res, next) => {
  client.query("update user set blackstate = ? where id = ?;", [1, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 일반 회원으로 변경
router.post('/nomaluser', (req, res, next) => {
  client.query("update user set blackstate = ? where id = ?;", [0, req.body.id] ,
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 블랙리스트 회원 목록 가져오기
router.post('/blacklist', (req, res, next) => {
  client.query("select * from user where blackstate = 1;",
   function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


module.exports = router;
