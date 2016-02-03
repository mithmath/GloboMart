package com.globomart.pricing.service;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.globomart.pricing.CouchbaseConnection;
import com.globomart.pricing.domain.Price;

public class CouchDataManagerService {
	
	private  CouchbaseConnection config;

    private  Bucket bucket;
    private  Cluster cluster;

    
    
    @Autowired
    public CouchDataManagerService(CouchbaseConnection config) {
    	
        this.config = config;

        //connect to the cluster and open the configured bucket
        
        this.cluster = CouchbaseCluster.create(config.getNodes());
        this.bucket = cluster.openBucket(config.getBucket(), config.getPassword());
       
    }
    
    public CouchDataManagerService() {
		// TODO Auto-generated constructor stub
	}

	
	public boolean addItem(Price prc) throws Exception {
       try{
		JsonObject Price = JsonObject.empty().put("productId", prc.getprductId()).put("productName", prc.getproductName())
				.put("productPrice", prc.getproductPrice());
		bucket.upsert(JsonDocument.create(prc.getprductId(), Price));  
       }catch(Exception ex){
    	   ex.printStackTrace();
			return false;
       }
		return true;
	}
	
	
	public boolean updateItem(Price prc)  {

				
		try{
		JsonObject Price = JsonObject.empty().put("productId", prc.getprductId()).put("productName", prc.getproductName())
				.put("productPrice", prc.getproductPrice());
		 bucket.replace(JsonDocument.create(prc.getprductId(), Price));
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		    return true;
           }
	
	public JsonDocument getItem(String productId){
		
		JsonDocument Product = bucket.get(productId);
		
		if(Product == null){
			return null;
		}else{
			return Product ;
		}
		
		
	}
  public boolean removeItem(String productId){
		
		try{
		JsonDocument Product = bucket.remove(productId);
		 System.out.println("Cas Value: " + Product.cas());
	     System.out.println("Catalog: " + Product.content());
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		    return true;
		
	}
  
  @PreDestroy
  public void preDestroy() {
      if (this.cluster != null) {
          this.cluster.disconnect();
      }
  }
}
