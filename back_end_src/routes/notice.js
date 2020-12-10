var express = require('express');
var router = express.Router();


router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express Notice_page' });
});


var mysql = require("mysql");
var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});



// 공지사항 불러오기
router.post('/noticelist', (req, res, next) => {
    var number = Number(req.body.number)
    var num1 = 0;
    if(number > 1){
      num1 = (number - 1) * 10;
    }
    var num2 = 10;
    client.query("select * from notice order by tcode DESC limit " + num1 + ", " + num2 + ";",
    function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.json(result);
      }
    });
});


// 공지사항 상세정보 가져오기
router.post('/noticeview', (req, res, next) => {
    client.query("select * from notice where tcode = ?;", [req.body.tcode], function(err, result){
        if(err){
            res.send(err);
        }
        else{
            res.json(result);
        }
    });
});


// 공지사항 데이터 개수
router.post('/noticelen', (req, res, next) => {
  client.query("select COUNT(*) as cnt from notice;",
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send(result);
      }
    });
});


// 공지사항 작성하기
router.post('/noteadd', (req, res, next) => {
    client.query("insert into notice (tcode, ttitle, ttext, timage, tname, tdate) values (?,?,?,?,?,?);",
    [null, req.body.ttitle, req.body.ttext, req.body.timage, req.body.tname, req.body.tdate],
    function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
});


// 게시판 글 정보 수정하기
router.post('/noticeupdate', (req, res, next) => {
    client.query("update notice set ttitle = ?, ttext = ? where tcode = ?;",
     [req.body.ttitle, req.body.ttext, req.body.tcode],
     function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
  });


// 공지사항 삭제하기
router.post('/noticedel', (req, res, next) => {
    client.query("delete from notice where tcode = ?;" , [req.body.tcode] , 
      function(err){
        if(err){
          res.send(err);
        }
        else{
          res.send('1');
        }
    });
});









module.exports = router;
