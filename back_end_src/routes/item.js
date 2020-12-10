var express = require('express');
var router = express.Router();


router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express Item_page' });
});


var mysql = require("mysql");
var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});


// 아이템 추가
router.post('/itemadd', (req, res, next) => {
    client.query("insert into bookitem (icode, iname, icate, ipublisher, iimage, ikeyword, itext, idate, icount, ireview, iratingavg) values (?,?,?,?,?,?,?,?,?,?,?);",
    [null, req.body.iname, req.body.icate, req.body.ipublisher, req.body.iimage, req.body.ikeyword, req.body.itext, req.body.idate, 0, 0, 0],
     function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
});


// 아이템 정보 수정하기
router.post('/itemupdate', (req, res, next) => {
  client.query("update bookitem set iname = ?, itext = ?, icate = ?, ikeyword = ?, ipublisher = ?, icount = ? where icode = ?;",
   [req.body.iname, req.body.itext, req.body.icate, req.body.ikeyword, req.body.ipublisher, req.body.icount, req.body.icode],
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 아이템 이미지만 수정하기
router.post('/itemimgadd', (req, res, next) => {
  client.query("update bookitem set iimage = ? where icode = ?;",
   [req.body.iimage, req.body.icode],
   function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});

// 아이템 삭제
router.post('/itemdel', (req, res, next) => {
  client.query("delete from bookitem where icode = ?;" , [req.body.icode] , 
    function(err){
      if(err){
        res.send(err);
      }
      else{
        res.send('1');
      }
    });
});


// 아이템 목록 불러오기
router.post('/itemlist', (req, res, next) => {
  var number = Number(req.body.number)
  var num1 = 0;
  if(number > 1){
    num1 = (number - 1) * 9;
  }
  var num2 = 9;
  client.query("select * from bookitem order by icode DESC limit " + num1 + ", " + num2 + ";", 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 아이템 카테고리 별 데이터 개수
router.post('/itemlen', (req, res, next) => {
  client.query("select COUNT(*) as cnt from bookitem where icate = ?;", [req.body.icate],
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send(result);
      }
    });
});

// 아이템 전체 데이터 개수
router.post('/itemlenall', (req, res, next) => {
  client.query("select COUNT(*) as cnt from bookitem;",
  function(err, result){
      if(err){
        res.send(err);
      }
      else{
        res.send(result);
      }
    });
});

// 아이템 카테고리 리스트
router.post('/itemcatelist', (req, res, next) => {
  var number = Number(req.body.number)
  var num1 = 0;
  if(number > 1){
    num1 = (number - 1) * 9;
  }
  var num2 = 9;
  client.query("select * from bookitem where icate = ? order by icode DESC limit " + num1 + ", " + num2 + ";", 
  [req.body.icate], function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 아이템 전체 검색하기
router.post('/itemsearch', (req, res, next) => {
  var data = "%" + req.body.itemsearch + "%";
  client.query("select * from bookitem where iname like ? or icate like ? or ipublisher like ? or ikeyword like ? or itext like ?;",
  [data, data, data, data, data],
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


// 아이템 이름 검색하기
router.post('/namesearch', (req, res, next) => {
  var data = "%" + req.body.itemsearch + "%";
  client.query("select * from bookitem where iname like ?;",
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

// 아이템 내용 검색하기
router.post('/textsearch', (req, res, next) => {
  var data = "%" + req.body.itemsearch + "%";
  client.query("select * from bookitem where itext like ?;",
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


// 아이템 출판사 검색하기
router.post('/publishersearch', (req, res, next) => {
  var data = "%" + req.body.itemsearch + "%";
  client.query("select * from bookitem where ipublisher like ?;",
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



// 아이템 상세보기
router.post('/itemview', (req, res, next) => {
  client.query("select * from bookitem where icode = ?;", [req.body.icode], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.send(result[0]);
    }
  });
});

// 아이템 리뷰 작성하기
router.post('/reviewadd', (req, res, next) => {
  client.query("insert into review (rcode, icode, id, rname, rtext, rrating, rdate) values (?,?,?,?,?,?,?);",
   [null, req.body.icode, req.body.id, req.body.rname, req.body.rtext, req.body.rrating, req.body.rdate],
    function(err){
     if(err){
       res.send(err);
     }
     else{
      client.query("update bookitem set ireview = ireview + 1 where icode = ?;", [req.body.icode], 
      function(err2){
       if(err2){
         res.send(err2);
       }
       else{
        client.query("update bookitem set iratingavg = (select ROUND(AVG(rrating), 2) from review where icode = ?) where icode = ?;", 
        [req.body.icode, req.body.icode], function(err3){
         if(err3){
           res.send(err3);
         }
         else{
           res.send('1');
         }
        });
       }
      });
     }
   });
});

// 아이템 리뷰 가져오기
router.post('/reviewlist', (req, res, next) => {
  client.query("select * from review where icode = ?;", [req.body.icode], function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});

// 아이템 리뷰 삭제하기
router.post('/reviewdel', (req, res, next) => {
  client.query("delete from review where rcode = ?;" ,
   [req.body.rcode] , function(err){
    if(err){
      res.send(err);
    }
    else{
      client.query("update bookitem set ireview = ireview - 1 where icode = ?;", [req.body.icode], 
      function(err2){
       if(err2){
         res.send(err2);
       }
       else{
        client.query("update bookitem set iratingavg = (select ROUND(AVG(rrating), 2) from review where icode = ?) where icode = ?;", 
        [req.body.icode, req.body.icode], function(err3){
         if(err3){
           res.send(err3);
         }
         else{
           res.send('1');
         }
        });
       }
      });
    }
  });
});


// 위시리스트 버튼 이벤트(추가, 삭제)
router.post('/wishlistadd', (req, res, next) => {
  // 위시리스트에 기존의 상품이 있는지 없는지 판단
  client.query("select * from wishlist where id = ? and icode =?;", [req.body.id, req.body.icode], 
  function(err, result){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        //위시리스트에 상품 추가 sql
        client.query("insert into wishlist (id, icode) values (?, ?);", [req.body.id, req.body.icode], 
        function(err){
          if(err){
            res.send(err);
          }
          else{
            res.send('1'); // 위시리스트에 해당 상품을 추가함
          }
        });
      }
      else{ //위시리스트에 이미 상품이 존재함
        client.query("delete from wishlist where id = ? and icode = ?;" ,
        [req.body.id, req.body.icode] , function(err){
          if(err){
            res.send(err);
          }
          else{
            res.send('0'); // 상품이 이미 있어서 삭제 완료
          }
        });
      }
    }
  });
});


// 위시리스트 상태 확인하기
router.post('/wishcheck', (req, res, next) => {
  client.query("SELECT * from wishlist where id = ? and icode = ?;",
  [req.body.id, req.body.icode], function(err, result){
    if(err){
      res.send(err);
    }
    else{
      if(!result.length){
        res.send('0');
      }
      else{
        res.send('1');
      }
    }
  });
});


// 위시리스트 목록 불러오기
router.post('/wishlist', (req, res, next) => {
  client.query("SELECT * from bookitem where icode IN (SELECT icode FROM wishlist where id = ?);",
  [req.body.id], function(err, result){
    if(err){
      res.send(err);
    }
    else{
      res.json(result);
    }
  });
});


// 위시리스트 삭제
router.post('/wishdel', (req, res, next) => {
  client.query("delete from wishlist where id = ? and icode = ?;" ,
   [req.body.id, req.body.icode] , function(err){
    if(err){
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});



module.exports = router;
