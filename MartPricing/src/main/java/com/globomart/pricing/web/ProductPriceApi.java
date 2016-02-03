package com.globomart.pricing.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.document.JsonDocument;
import com.globomart.pricing.domain.Price;
import com.globomart.pricing.service.CouchDataManagerService;

@RestController
@RequestMapping("/price")
public class ProductPriceApi {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductPriceApi.class);
	
	@Autowired
	CouchDataManagerService couchDataManagerService;
	
	/*
	 * Service -get the new price and product as a new record
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{productId}")
	public  ResponseEntity<String> getPrice(@PathVariable String productId)throws Exception{
		
		JsonDocument price=couchDataManagerService.getItem(productId);
		if (price == null){
			//String result="Requested Product Not Available";
			logger.info("Requested Product Not Available");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		else{
		String result=price.toString();
		logger.info("Result:"+result);
		return new ResponseEntity<String>(price.content().toString(), HttpStatus.OK);
		}
		
	}
	
	/*
	 * Service -add the new price and product as a new record
	 */
	
	@RequestMapping(method=RequestMethod.POST,value="/add")
	public ResponseEntity<String> addPrice(@RequestBody Price prc) throws Exception{
		
		
		boolean result =couchDataManagerService.addItem(prc);
		if (result == false){
		logger.info("Requested Product Addition Failed.....");
		 return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}else{
		logger.info("Product Added Successfully..");
		return new ResponseEntity<String>("OK", HttpStatus.OK);
		}
				
	}
	
	/*
	 * Service -update the new price and product as a new record
	 */
	@RequestMapping(method=RequestMethod.PUT,value="/update")
	public  ResponseEntity<String> updatePrice(@RequestBody Price prc) throws Exception{
		
		
		boolean result =couchDataManagerService.updateItem(prc);
		if (result == false){
			logger.info("Requested Product Not Available.....");
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		else{
			logger.info("Requested Product Updated.....");
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		
		}
				
	}
	
	/*
	 * Service -delete the new price and product as a new record
	 */
	
		@RequestMapping(method=RequestMethod.DELETE,value="/delete/{productId}")
		public ResponseEntity<String> deletePrice(@PathVariable String productId) throws Exception{
			
			
			boolean result =couchDataManagerService.removeItem(productId);
			if (result == false){
				logger.info("Requested Resource Not Available.....");
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			else{
				logger.info("Requested Resource Removed.....");
				return new ResponseEntity<String>("OK", HttpStatus.OK);
			
			}
					
		}

}
