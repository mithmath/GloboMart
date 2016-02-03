package com.globomart.pricing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.globomart.pricing.service.CouchDataManagerService;


@Configuration
public class Initializer {
	
	private  CouchbaseConnection config;
	
	@Bean
	public CouchDataManagerService couchDataManagerService() {
		return new CouchDataManagerService();
	}
	@Bean
	public CouchDataManagerService couchDataManagerService(CouchbaseConnection config) {
		return new CouchDataManagerService(config);
	}
}
