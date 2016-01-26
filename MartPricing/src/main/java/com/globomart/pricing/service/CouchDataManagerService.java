package com.globomart.pricing.service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.globomart.pricing.domain.Price;

public class CouchDataManagerService {
	
	public boolean addItem(Price prc) throws Exception {

		// Connect to localhost
		Cluster cluster = CouchbaseCluster.create();

		// Open the default bucket and the "JavaTraining" 
		
		Bucket JavaTraining = cluster.openBucket("JavaTraining", "javatraining");
		JsonObject Price = JsonObject.empty().put("productId", prc.getprductId()).put("productName", prc.getproductName())
				.put("productPrice", prc.getproductPrice());
		JavaTraining.upsert(JsonDocument.create(prc.getprductId(), Price));

		// Disconnect and clear all allocated resources
		cluster.disconnect();
       
		return true;
	}
	
	public String updateItem(Price prc)  {

		// Connect to localhost
		Cluster cluster = CouchbaseCluster.create();

		// Open the default bucket and the "JavaTraining" 
		
		Bucket JavaTraining = cluster.openBucket("JavaTraining", "javatraining");
		
		try{
		JsonObject Price = JsonObject.empty().put("productId", prc.getprductId()).put("productName", prc.getproductName())
				.put("productPrice", prc.getproductPrice());
		JavaTraining.replace(JsonDocument.create(prc.getprductId(), Price));
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		cluster.disconnect(); 
       	return "OK";
           }
	
	public JsonObject getItem(String productId){
		
		
		Cluster cluster = CouchbaseCluster.create();
		Bucket JavaTraining = cluster.openBucket("JavaTraining", "javatraining");
		
		JsonDocument Product = JavaTraining.get(productId);
		
		if(Product == null){
			cluster.disconnect();
			return null;
		}else{
			JsonObject p1=Product.content();
			cluster.disconnect();
			return p1 ;
		}
		
		
	}
  public String removeItem(String productId){
		
		
		Cluster cluster = CouchbaseCluster.create();
		Bucket JavaTraining = cluster.openBucket("JavaTraining", "javatraining");
		try{
		JsonDocument Product = JavaTraining.remove(productId);
		 System.out.println("Cas Value: " + Product.cas());
	     System.out.println("Catalog: " + Product.content());
		}catch(Exception ex){
			ex.printStackTrace();
			return "error";
		}
		cluster.disconnect(); 
       	return "OK";
		
	}
}
