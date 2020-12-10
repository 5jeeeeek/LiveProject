var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var cookieRouter = require('./routes/cookie');
var boardRouter = require('./routes/board');
var noteRouter = require('./routes/note');
var noticeRouter = require('./routes/notice');
var itemRouter = require('./routes/item');
var imguploadRouter = require('./routes/imgupload');
var iotRouter = require('./routes/iot');
var emailRouter = require('./routes/email');
const cors = require("cors");


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
// app.engine('html', require('ejs').renderFile);
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(cors());

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/board', boardRouter);
app.use('/note', noteRouter);
app.use('/notice', noticeRouter);
app.use('/imgupload', imguploadRouter);
app.use('/item', itemRouter);
app.use('/email', emailRouter);
app.use('/rlfgyalsrhkwncjfrl', cookieRouter);

app.use('/upimg', express.static('/home/hosting_users/yeonsiwoo2/apps/yeonsiwoo2_minback/uploads'));

app.use('/iot', iotRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
