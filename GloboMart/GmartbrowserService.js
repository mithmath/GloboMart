var async = require('async');
var request = require('request');
exports.handler = function(req, res) {
  async.parallel([
    /*
     * Product Service endpoint
     */
    function(callback) {
     var id = req.param('id');
       var url = "http://localhost:8080/product/"+id;
	  // var url = "http://localhost:8080/product/PE2100";
	   console.log(id);
	   console.log(url);
	  
      request(url, function(error, response, body) {
        
        if(error) { console.log(error); callback(true); return; }
        obj = JSON.parse(body);
        console.log(body);
        callback(false, obj);
      });
    },
    /*
     * Price Service endpoint
     */
    function(callback) {
      var id = req.param('id');
      var url = "http://localhost:9090/price/"+id;
	//var url = "http://localhost:9090/price/PE2100";
	 console.log(id);
	 console.log(url);
	 
      request(url, function(err, response, body) {
        // JSON body
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        console.log(body);
        callback(false, obj);
      });
    },
  ],
  /*
   * results
   */
  function(err, results) {
    if(err) { console.log(err); res.send(500,"Server Error"); return; }
    res.send({prodcut:results[0], price:results[1]});
  }
  );


};

exports.batchHandler = function(req, res) {
  var prodcutDetailsArray = {products:[]};

  async.parallel([
    /*
     * Product Service
     */
    function(callback) {
      var id = req.param('id');
      //var url = "http://localhost:8080/product/product-"+id;
      var url = "http://localhost:8080/product?category=smartphone";
      request(url, function(error, response, body) {
        if(error) { console.log(error); callback(true); return; }
        var product = JSON.parse(body);
         var requestedCompleted = 0;
        for(var i=0 ;i<product.length;i++){
          console.log("Prodcut");
          console.log(product[i].id);
            async.parallel([
            function(callback) {
              console.log("Price");
              console.log(products[i].id);
              var url = "http://localhost:9090/price/"+id;
              console.log(url);
              request(url, function(error, response, body) {
                if(error) { console.log(error); callback(true); return; }
                price = JSON.parse(body);
                console.log(product[i]);
                prodcutDetailsArray.products.push({"product":i,"price":price});
                console.log(body);
                requestedCompleted++;
                callback(false, obj);
              });
            },
          ],
          /*
           * Results
           */
          function(err, results) {
            if(err) { console.log(err); res.send(500,"Server Error"); return; }
            console.log(i+"---"+(i+1 == products.length)+"---"+products.length);
            if(requestedCompleted == products.length){
              console.log(prodcutDetailsArray);
              res.send(prodcutDetailsArray);
            }
          //  res.send({prodcut:products[i], price:results[0]});
          }
          );
        }
        callback(false, products);
      });
    },
    /*
     * Price Service
     */
    function(callback) {
      var id = req.param('id');
      var url = "http://localhost:9090/price/price-"+id;
      request(url, function(err, response, body) {
        if(err) { console.log(err); callback(true); return; }
        obj = JSON.parse(body);
        console.log(body);
        callback(false, obj);
      });
    },
  ],
  /*
   *  Results
   */
  function(err, results) {
    if(err) { console.log(err); res.send(500,"Server Error"); return; }
        res.send(prodcutDetailsArray);
  }
  );


};
