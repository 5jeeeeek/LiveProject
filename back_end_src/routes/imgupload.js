var express = require('express');
var router = express.Router();
var fs = require('fs');

router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express ImageUpload_page' });
});


var mysql = require("mysql");
var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});



const path = require('path');
const multer = require('multer');
const upload = multer({
  storage: multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, path.join(__dirname, '../uploads/'));
    },
    filename: function (req, file, cb) {
      //cb(null, new Date().valueOf() + path.extname(file.originalname));
      cb(null, new Date().toISOString().replace(/[\/\\:.]/g, "_") + file.originalname)
    }
  }),
});



// 이미지 업로드
router.post('/boardimg', upload.single('img'), (req, res, next) => {
    res.json(req.file);
});



// 게시판 이미지 삭제
router.post('/boardimgdel', (req, res) => {
    var filePath = "/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads/";
    fs.unlink(filePath + req.body.img, (err) => { 
      if (err) { 
        res.send(err);
      }
      else{
        client.query("update board set bimage = ? where bcode = ?;",
        [0, req.body.bcode],
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
});

// 공지사항 이미지 삭제
router.post('/noticeimgdel', (req, res) => {
  var filePath = "/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads/";
  fs.unlink(filePath + req.body.img, (err) => { 
    if (err) { 
      res.send(err);
    }
    else{
      client.query("update notice set timage = ? where tcode = ?;",
      [0, req.body.tcode],
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
});

// 쪽지 이미지 삭제
router.post('/noteimgdel', (req, res) => {
  var filePath = "/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads/";
  fs.unlink(filePath + req.body.img, (err) => { 
    if (err) { 
      res.send(err);
    }
    else{
      client.query("update note set nimage = ? where ncode = ?;",
      [0, req.body.ncode],
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
});

// 상품 이미지 삭제
router.post('/itemimgdel', (req, res) => {
  var filePath = "/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads/";
  fs.unlink(filePath + req.body.img, (err) => { 
    if (err) { 
      res.send(err);
    }
    else{
      res.send('1');
    }
  });
});


router.post("/getimg", function(req, res){
		var filename = "432.PNG";
		var filePath = "/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads/" + filename;
		fs.readFile(filePath,
			function (err, data){	
				if(err){
					res.end(null);
        }
        else{
          res.end(data);
				}				
      }
		);
});



module.exports = router;
