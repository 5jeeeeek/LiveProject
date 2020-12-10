var express = require('express');
var router = express.Router();

var mysql = require("mysql");
var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});




router.get('/', function(req, res, next) {
    res.render('index', { title: 'Express IoT sensor Page' });
  });



// 인체감지 센서 감지
router.get('/piron', (req, res, next) => {
    var color = "#76FF03";
    client.query("update seat set sstate = 1, color = '" + color + "' where snum = 1;",
    function(err){
        if(err){
            res.send(err);
        }
        else{
            res.send('sensor on');
        }
    });
});


// 인체감지 센서 감지 없음 -> 타임아웃됨
var color = "#E0E0E0";
router.get('/piroff', (req, res, next) => {
    client.query("update seat set sstate = 0, color = '" + color + "' where snum = 1;",
    function(err){
        if(err){
            res.send(err);
        }
        else{
            res.send('sensor off');
        }
    });
});


// 인체감지 센서 값 가져오기
router.post('/sensorlist', (req, res, next) => {
    client.query("select * from seat;",
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
