var express = require('express');
var router = express.Router();
var nodemailer = require('nodemailer');
var ejs = require('ejs');

// const path = require('path');
// var appDir = path.dirname(require.main.filename);

var smtpTransport = nodemailer.createTransport({
    service: "",
    host: "",
    port: ,
    secure: false,
    auth: {
        user: "",
        pass: ""
    }
});

var mysql = require("mysql");

var client = mysql.createConnection({
  host: "",
  port: ,
  user: "",
  password: "",
  database: ""
});


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express Email' });
});


router.post('/emailcheck', (req, res) => {

    client.query("select * from user where email = ?;", [req.body.email] , 
    function(err, result){
        if(err){
          res.send(err);
        }
        else{
          if(!result.length){
            let authNum = Math.random().toString().substr(2,6);
            let emailsend;
            ejs.renderFile('/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/views/emailsend.ejs', {authCode : authNum}, 
            function (err, data) {
                if(err){console.log('ejs.renderFile err')}
                emailsend = data;
            });

            var mailOptions = {
                from: "",
                to: req.body.email,
                subject: "[Live Project] 회원가입 이메일 인증번호입니다.",
                html: emailsend
            };
            
            smtpTransport.sendMail(mailOptions, function(error){
                if(error){
                    console.log(error);
                }
                res.send(authNum);
                transporter.close()
            });
          }
          else{
            res.send('0');
          }
        }
      });
});

router.post('/idfind', (req, res) => {
    client.query("select * from user where email = ?;", [req.body.email] , 
    function(err, result){
        if(err){
          res.send(err);
        }
        else{
          if(!result.length){
            res.send("0"); // 데이터가 없음 
          }
          else{
            var response_data = result[0];

            let authNum = Math.random().toString().substr(2,6);
            let idsend;
            ejs.renderFile('/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/views/idsend.ejs', {authCode : authNum}, 
            function (err, data) {
                if(err){console.log('ejs.renderFile err')}
                idsend = data;
            });
            var mailOptions = {
                from: "",
                to: req.body.email,
                subject: "[Live Project] 아이디 찾기 인증번호입니다.",
                html: idsend
            };
            smtpTransport.sendMail(mailOptions, function(error){
                if(error){
                    console.log(error);
                }
                var data = [response_data.id, authNum];
                res.send(data); // id, 인증번호를 보내줌
                transporter.close()
            });
          }
        }
      });


});

module.exports = router;
