package com.globomart.product.service;



import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.globomart.product.ProductCouchConfiguration;
import com.globomart.product.entity.Product;

import rx.Observable;

public class CouchbaseDataManagerImpl implements CouchbaseDataManager {
	
	
	private  CouchbaseCluster cluster;
	private  Bucket bucket;
	//private ProductCouchConfiguration config;
	
	
	
	
	public CouchbaseDataManagerImpl(ProductCouchConfiguration config) {
		   
		cluster = CouchbaseCluster.create(config.getCouchbaseNodes());
		bucket = cluster.openBucket(config.getCouchbaseBucket(),config.getCouchbasePassword());
		System.out.println("Bucket opened.........");
	}
   
   public Observable<JsonDocument> addAsyncItem(String id,JsonObject jsonPrd) {
	
	   JsonDocument doc=JsonDocument.create(id, jsonPrd);
	   
	   Observable<JsonDocument>obsDoc= bucket.async().insert(doc);
	   	   
	   return obsDoc;
        }
	
 
   public JsonDocument addSyncItem(Product prd) {
	   
	   JsonDocument obsDoc = bucket.async().insert(JsonDocument.create(prd.getprodId(), parseJsonObj(prd))).toBlocking().single();
		return obsDoc;
        }
   
   
   public JsonDocument removeItem(String productId){
	   
	   return bucket.remove(productId);
   }
   
 public JsonDocument getItem(String productId){
	 
	   
	   return bucket.get(productId);
   }

 public ViewResult searchByCriteria(String designDoc, String myView,String category){
	 
	 ViewQuery query = ViewQuery.from(designDoc, myView).key(category); 	  
	  ViewResult result = bucket.query(query);
     return result;
	 
 }
 
 public ViewResult getBatchProducts(String designDoc, String myView,int limitValue){
	 ViewQuery query = ViewQuery.from(designDoc, myView).limit(limitValue); 	  
	  ViewResult result = bucket.query(query);
    return result;
	}
  
   public static JsonObject parseJsonObj(Product prd){
	   
	   JsonObject jsonPrd = JsonObject.empty().put("productId", prd.getprodId()).put("productName", prd.getproductName())
				.put("manufacturer", prd.getManufacturer()).put("category", prd.getCategory()).put("unitsInStock", prd.getUnitsInStock());
	   return jsonPrd;
	   
   }


}