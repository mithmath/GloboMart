package com.globomart.product.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.globomart.product.ProductCouchConfiguration;
import com.globomart.product.entity.Product;
import com.globomart.product.service.CouchbaseDataManager;
import com.globomart.product.service.CouchbaseDataManagerImpl;


@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
	
	CouchbaseDataManager dataManager;
	
	public ProductResource(ProductCouchConfiguration config){
		dataManager=new CouchbaseDataManagerImpl(config);
		
	}
	
	
	@POST
	@Path("/addAsync")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAsyncProduct(String product){
		try{
			  
			JsonObject jsonPrd =JsonObject.fromJson(product);
			String id =jsonPrd.getString("productId");
			   dataManager.addAsyncItem(id,jsonPrd);
		 return Response.status(Status.CREATED).build();
		 
		}catch(IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (DocumentAlreadyExistsException e) {
        	 return Response.status(Status.CONFLICT).build();
        } catch (Exception e) {
        	e.printStackTrace();
        	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        	
        }
	
    }
	
	@POST
	@Path("/addSync")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSyncProduct(Product product){
		try{
			
			   dataManager.addSyncItem(product);
		return Response.status(Status.CREATED).build();
		
		}catch(IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).build();
        } catch (DocumentAlreadyExistsException e) {
        	 return Response.status(Status.CONFLICT).build();
        } catch (Exception e) {
        	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        	
        }
	
    }
	
	@GET
	@Path("/{id}")
	public Response getProduct(@PathParam("id") String productId) {
		
		
			
			JsonDocument p=dataManager.getItem(productId);
			String result=p.content().toString();
			if(result == null){
				return Response.status(Status.NOT_FOUND).build();
						
			}else{
				return Response.ok(result).build();
			}
	}
		
	@GET
	@Path("/query")
	public Response getProductByCategory(@QueryParam("category") String category) {
		
		System.out.println("category...."+category);
		ViewResult result = dataManager.searchByCriteria("dev_category", "category_view",category);
		
		
		if (!result.success()) {
            return  Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	   }else{
		   
		   JsonArray plist =JsonArray.create();
		   //List<ViewRow> plist=result.allRows();
		   for (ViewRow row : result) {
			   plist.add(row.document().content());
			    
			}
		   if (plist.isEmpty()){
			   return Response.ok(Status.NOT_FOUND).build();
			   
		   }else{
			   
			   return Response.ok(plist.toString()).build();
			   
		   }
	   }
		
	   }
	
	@GET
	@Path("/batch")
	
public Response getProductBatchByCategory(@QueryParam("offset") int limitValue) {
		
		
		ViewResult result = dataManager.getBatchProducts("dev_allProducts", "allProduct_view",  limitValue);
			
		if (!result.success()) {
            return  Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
	   }else{
		   
		   JsonArray plist =JsonArray.create();
		   	for (ViewRow row : result) {
			   plist.add(row.document().content());
			    
			}
		   if (plist.isEmpty()){
			   return Response.ok(Status.NOT_FOUND).build();
			   
		   }else{
			   
			   return Response.ok(plist.toString()).build();
			   
		   }
	   }
		
	   }
	
	
	
	@DELETE
	@Path("/delete/{id}")
	public Response deleteItem(@PathParam("id") String productId) {
		
		try{
			
			   dataManager.removeItem(productId);
		return Response.status(Status.OK).build();
		
		}catch(IllegalArgumentException e) {
         return Response.status(Status.BAD_REQUEST).build();
     } catch (DocumentAlreadyExistsException e) {
     	 return Response.status(Status.CONFLICT).build();
     } catch (Exception e) {
     	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
     	
     }
		
		
	}
	
}	
