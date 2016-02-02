var request = require('request');
var async = require('async');
var express = require('express');
const browser = require('./GmartbrowserService.js');
var app = express();
app.get('/service',function(req,res){
  browser.handler(req,res);
});
app.get('/service/batch',function(req,res){
  browser.batchHandler(req,res);
});
app.listen(3000,function(){
  console.log('Example app listening on port 3000');
});
