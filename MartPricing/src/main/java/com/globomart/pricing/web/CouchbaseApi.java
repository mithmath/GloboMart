package com.globomart.pricing.web;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.document.json.JsonObject;
import com.globomart.pricing.domain.Price;
import com.globomart.pricing.service.CouchDataManagerService;

@RestController
@RequestMapping("/price")
public class CouchbaseApi {
	private static final Logger logger = LoggerFactory.getLogger(CouchbaseApi.class);
	@Autowired
    CouchDataManagerService couchDataManagerService;
	
	//Service returns the price of the requestd Product
	
	@RequestMapping(method = RequestMethod.GET, value="/{productId}")
	public  Response getPrice(@PathVariable String productId)throws Exception{
		
		JsonObject price=couchDataManagerService.getItem(productId);
		if (price == null){
			String result="Requested Product Not Available";
			logger.info("Requested Product Not Available");
			return Response.status(404).entity(result).build();
		}
		else{
		String result=price.toString();
		logger.info("Result:"+result);
		return Response.status(200).entity(result).build();
		}
		
	}
	
	//Service -add the new price and product as a new record
	@RequestMapping(method=RequestMethod.POST,value="/add")
	public boolean addPrice(@RequestBody Price prc) throws Exception{
		
		
		boolean result =couchDataManagerService.addItem(prc);
		logger.info("Product Added Successfully..");
		return result;
				
	}
	
	//Service -update the new price and product as a new record
	@RequestMapping(method=RequestMethod.PUT,value="/update")
	public Response updatePrice(@RequestBody Price prc) throws Exception{
		
		
		String result =couchDataManagerService.updateItem(prc);
		if (result == null){
			String msg="Requested Product Not Available";
			logger.info("Requested Product Not Available.....");
			return Response.status(404).entity(msg).build();
		}
		else{
			logger.info("Requested Product Updated.....");
			return Response.status(200).entity(result).build();
		
		}
				
	}
	
	//Service -delete the  price and product record
		@RequestMapping(method=RequestMethod.DELETE,value="/delete/{productId}")
		public Response deletePrice(@PathVariable String productId) throws Exception{
			
			
			String result =couchDataManagerService.removeItem(productId);
			if (result == "error"){
				String msg="Requested Resource Not Available";
				logger.info("Requested Resource Not Available.....");
				return Response.status(404).entity(msg).build();
			}
			else{
				logger.info("Requested Resource Removed.....");
				return Response.status(200).entity(result).build();
			
			}
					
		}

}
