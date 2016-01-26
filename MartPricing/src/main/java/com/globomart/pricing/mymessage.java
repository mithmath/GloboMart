package com.globomart.pricing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mymessage {
	
	//	
	    @RequestMapping("/")
	    public String index() {
	        return "Hai All ,, Its a boot test!!!";
	    }

}
