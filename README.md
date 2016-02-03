# GloboMart

It include 3 Services
1)Mart Product Service
2)Mart Price Service
3)ClentService-GloboMart

Requirements:
  Product Service Bucket (eg: GMartProduct)
  Price Service Bucket (eg: GMartPrice)
  Views: 1)To search products based on category ( eg: design doc:"dev_category", View Name:  "category_view" )
         2)To search a batch of products( eg: design doc:"dev_allProducts", View Name: "allProduct_view")

Build:
  1)Mart Product Service -Using Dropwizard and gradle and embedded jetty server running on port 8080
  2)Mart Price Service -Using Spring Boot and Maven and embedded tomcat running on port 9090
  3)ClentService-GloboMart -Using NodeJs.Start running from command prompt using index.js
  
REST API:

Mart Product Service

1)To Add a Product asynchronously (method:POST)
URI: http://localhost:8080/product/addAsync
Body:{
  "unitsInStock": 9,
  "category": "smartphone",
  "productName": "Xperia 560",
  "manufacturer": "Sony",
  "productId": "PE2100"
}
2)To Add a Product Synchronously using BlockingObservable (method:POST)
URI: http://localhost:8080/product/addSync
3)To get a product using product id (method:GET)
 URI: http://localhost:8080/product/{id}
3)Retrieve the list of products based onsearch criteria (category)(method:GET)
URI: http://localhost:8080/product/query?category=smartphone      (eg: category as smartphone)
4)Get a batch products from couchbase (method:GET)
URI: http://localhost:8080/product/batch?offset=10   (eg: 10 producs in  batch)
5)To remove a product (method:DELETE)
URI: http://localhost:8080/product//delete/{id}

Mart Price Service

 1) To get price information of a product (method:GET)
    http://localhost:9090/price/{productId}
 2) To add a product with price to couchbase bucket (method:POST) 
    http://localhost:9090/price/add
    Body:{
         "productName":"Canvas 4",
         "productPrice":15000,
         "productId":"PE1002"
        }
 3)To update price details of an existin product (Method PUT)
    http://localhost:9090/price/update
 4)To remove price (method:DELETE)
    http://localhost:9090/price/delete/{productId}
 
  

