var express = require('express');
var router = express.Router();



router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express Board_page' });
});




var mysql = require("mysql");

var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "@",
  database: ""
});


// 자유게시판 글 목록 불러오기
router.post('/boardlist1', (req, res, next) => {
    var number = Number(req.body.number)
    var num1 = 0;
    if(number > 1){
      num1 = (number - 1) * 10;
    }
    var num2 = 10;
    client.query("select * from board where bcate = '1' order by bcode DESC limit " + num1 + ", " + num2 + ";", 
    function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.json(result);
      }
    });
  });



// 질문게시판 글 목록 불러오기
router.post('/boardlist2', (req, res, next) => {
  var number = Number(req.body.number)
  var num1 = 0;
  if(number > 1){
    num1 = (number - 1) * 10;
  }
  var num2 = 10;
  client.query("select * from board where bcate = '2' order by bcode DESC limit " + num1 + ", " + num2 + ";", 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});



// 인기게시판 글 목록 불러오기
router.post('/boardlist3', (req, res, next) => {
  var number = Number(req.body.number)
  var num1 = 0;
  if(number > 1){
    num1 = (number - 1) * 10;
  }
  var num2 = 10;
  client.query("select * from board where blike > 4 order by bcode DESC limit " + num1 + ", " + num2 + ";", 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});



// 게시판 상세정보 가져오기
router.post('/boardview', (req, res, next) => {
  client.query("select * from board where bcode = ?;", [req.body.bcode], function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send(result[0]);
      }
    });
  });


// 게시판 데이터 개수
router.post('/boardlen', (req, res, next) => {
  client.query("select COUNT(*) as cnt from board where bcate = ?;", [req.body.bcate],
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send(result);
      }
    });
});


// 게시물 조회수 증가
router.post('/viewadd', (req, res, next) => {
  client.query("update board set bviews = bviews + 1 where bcode = ?;", [req.body.bcode], 
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
  });



// 게시판 글 작성하기
router.post('/boardadd', (req, res, next) => {
  if(req.body.bimage == 0){
    client.query("insert into board (bcode, id, btitle, btext, bcate, bimage, blike, bnickname, bviews, bdate) values (?,?,?,?,?,?,?,?,?,?);",
    [null, req.body.id, req.body.btitle, req.body.btext, req.body.bcate, 0, 0, req.body.bnickname, 0, req.body.bdate],
     function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
  }
  else{
    // var img1 = "http://minback.com/upimg/" + req.body.bimage;
    client.query("insert into board (bcode, id, btitle, btext, bcate, bimage, blike, bnickname, bviews, bdate) values (?,?,?,?,?,?,?,?,?,?);",
    [null, req.body.id, req.body.btitle, req.body.btext, req.body.bcate, req.body.bimage, 0, req.body.bnickname, 0, req.body.bdate],
     function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
  }  
});


// 게시판 글 삭제하기
router.post('/boarddel', (req, res, next) => {
  client.query("delete from board where bcode = ?;" , [req.body.bcode] , 
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
router.post('/boardupdate', (req, res, next) => {
  client.query("update board set btitle = ?, btext = ?, bcate = ? where bcode = ?;",
   [req.body.btitle, req.body.btext, req.body.bcate, req.body.bcode],
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});


// 게시판 이미지만 수정하기
router.post('/boardimgadd', (req, res, next) => {
  client.query("update board set bimage = ? where bcode = ?;",
   [req.body.bimage, req.body.bcode],
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
router.post('/boardchange', (req, res, next) => {
  client.query("update board set btitle = ?, btext = ?, bcate = ?, bimage = ? where bcode = ?;",
   [req.body.btitle, req.body.btext, req.body.bcate, req.body.bimage, req.body.bcode],
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});


//게시판 검색하기
router.post('/boardsearch', (req, res, next) => {
  var data = "%" + req.body.boardsearch + "%";
  client.query("select * from board where btitle like ? or btext like ?;",
  [data, data],
  function(err, result, fields){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 게시판 댓글 작성하기
router.post('/bcadd', (req, res, next) => {
  client.query("insert into comment (ccode, bcode, id, cname, ctext, cdate, cprivate) values (?,?,?,?,?,?,?);",
   [null, req.body.bcode, req.body.id, req.body.cname, req.body.ctext, req.body.cdate, req.body.cprivate],
    function(err){
     if(err){
       res.send(err);
     }
     else{
       client.query("update board set bcomment = bcomment + 1 where bcode = ?;", [req.body.bcode], 
       function(err2){
        if(err2){
          res.send(err2);
        }
        else{
          res.send('1');
        }
       });
     }
   });
});

// 게시판 댓글 목록 불러오기
router.post('/bclist', (req, res, next) => {
  client.query("select * from comment where bcode = ?;", [req.body.bcode], function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 게시판 댓글 삭제하기
router.post('/bcdel', (req, res, next) => {
  client.query("delete from comment where ccode = ?;" ,
   [req.body.ccode] , function(err){
    if(err){
      res.send(err);
    }
    else{
      client.query("update board set bcomment = bcomment - 1 where bcode = ?;", [req.body.bcode], 
      function(err2, result2){
        if(err2){
          res.send(err2);
        }
        else{
          res.send('1');
        }
      });
    }
  });
});


// 좋아요 클릭 이벤트
router.post('/boardlike', (req, res, next) => {
  client.query("select * from board_history where bcode = ? and id = ?;", [req.body.bcode, req.body.id], 
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        if(!result.length){ //사용자가 좋아요를 누르지 않은 상태일때
          client.query("insert into board_history (bcode, id) values (?, ?);", 
          [req.body.bcode, req.body.id], function(err2){
            if(err2){
              res.send(err2);
            }
            else{
              client.query("update board set blike = blike + 1 where bcode = ?;", [req.body.bcode], 
              function(err3){
                if(err3){
                  res.send(err3);
                }
                else{
                  res.send('1');  //좋아요 성공
                }
              });
            }
          })
        }
        else{ // 사용자가 해당 게시글의 좋아요를 누른상태
          res.send('2'); // 좋아요 실패 : 이미 좋아요를 누른 상태
        }
      }
    });
  });



module.exports = router;
