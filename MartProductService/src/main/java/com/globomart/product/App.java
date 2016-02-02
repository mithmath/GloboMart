package com.globomart.product;




import com.globomart.product.resources.ProductResource;


import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<ProductCouchConfiguration> {
	
	//private static final Logger LOGGER =LoggerFactory.getLogger(App.class);

		
		  @Override
		  public void initialize(Bootstrap<ProductCouchConfiguration> b) {}
		
		  @Override
		  public void run(ProductCouchConfiguration c, Environment e) throws Exception {
		   // LOGGER.info("Method App#run() called");
		    System.out.println( "Hello world, by Dropwizard!" );
		    System.out.println("Coucbase Bucket : " + c.getCouchbaseBucket());
			e.jersey().register(new ProductResource(c));
		  }
	
		  public static void main( String[] args ) throws Exception{
		        new App().run(args);
		      }
}
