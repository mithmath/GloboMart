package com.globomart.product.service;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.ViewResult;
import com.globomart.product.entity.Product;

import rx.Observable;

public interface CouchbaseDataManager {
	
   public Observable<JsonDocument> addAsyncItem(String id,JsonObject jsonPrd);
	
	public JsonDocument addSyncItem(Product product);
	
	public JsonDocument removeItem(String productId);
	
	public JsonDocument getItem(String productId);
	
	public ViewResult searchByCriteria(String designDoc, String myView,String category);
	
	public ViewResult getBatchProducts(String designDoc, String myView,int limitValue);
	


}
