var express = require('express');
var router = express.Router();


router.get('/', function(req, res, next) {
    res.render('index', { title: 'Express Note_page' });
  });



var mysql = require("mysql");

var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});


// 보낸 쪽지함 불러오기
router.post('/notesendlist', (req, res, next) => {
    client.query("select * from note where nsend = ? order by ncode DESC;", [req.body.id], 
    function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.json(result);
      }
    });
  });


// 받음 쪽지함 불러오기
router.post('/notereceivelist', (req, res, next) => {
    client.query("select * from note where nreceive = ? order by ncode DESC;", [req.body.id], 
    function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.json(result);
      }
    });
  });


// 쪽지 작성하기
router.post('/noteadd', (req, res, next) => {
    client.query("insert into note (ncode, ntitle, ntext, nsend, nreceive, nstate, ndate, nimage) values (?,?,?,?,?,?,?,?);",
    [null, req.body.ntitle, req.body.ntext, req.body.nsend, req.body.nreceive, 0, req.body.ndate, req.body.nimage],
    function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
});


// 쪽지 삭제하기
router.post('/notedel', (req, res, next) => {
    client.query("delete from note where ncode = ?;" , [req.body.ncode] , 
      function(err){
        if(err){
          res.send(err);
        }
        else{
          res.send('1');
        }
    });
});


// 쪽지 상세보기 -> 읽음 표시 처리
router.post('/noteview', (req, res, next) => {
    client.query("update note set nstate = 1 where ncode = ?;", [req.body.ncode], 
    function(err, result){
        if(err){
            res.send(err);
        }
        else{
            res.send('1');
        }
    });
});






module.exports = router;
