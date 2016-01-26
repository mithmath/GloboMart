package com.globomart.pricing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.globomart.pricing.service.CouchDataManagerService;

@Configuration
public class BeanInitializer {
	
	@Bean
	public CouchDataManagerService couchDataManagerService() {
		return new CouchDataManagerService();
	}
	

}
