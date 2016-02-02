package com.globomart.product.entity;

public class Product {
	 
		  private String productId;
		  
		  
		  private String productName;
		  private String manufacturer;
		  private String category;
		  private long unitsInStock;
		 
		public Product() {
		    //super();
		}

		/*  public Product(String productId, String productName, String category) {
		    this.productId = productId;
		    this.productName = productName;
		    this.category = category;
		  }*/
		  
		  
	     public String getprodId(){
	    	 return productId;
	     }
	     
	     public void setproductId(String productId ){
	    	 this.productId=productId;
	     }
	     public String getproductName(){
	    	 return productName;
	     }
	     
	     public void setproductName(String productName ){
	    	 this.productName=productName;
	     }
	     public String getManufacturer(){
	    	 return manufacturer;
	     }
	     
	     public void setManufacturer(String manufacturer ){
	    	 this.manufacturer=manufacturer;
	     }
	     public String getCategory(){
	    	 return category;
	     }
	     
	     public void setCategory(String category ){
	    	 this.category=category;
	     }
	     
	     public long getUnitsInStock(){
	    	 return unitsInStock;
	     }
	     
	     public void setUnitsInStock(long unitsInStock ){
	    	 this.unitsInStock=unitsInStock;
	     }
		  
	
}
