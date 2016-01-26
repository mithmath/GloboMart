package com.globomart.pricing.domain;

public class Price {

	private String productId;
	private String productName;
	private Long productPrice;
	
	public String getprductId() {
		return productId;
	}

	public void setproductId(String productId) {
		this.productId = productId;
	}

	public String getproductName() {
		return productName;
	}

	public void setproductName(String productName) {
		this.productName = productName;
	}
	
	public Long getproductPrice() {
		return productPrice;
	}

	public void setproductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}
	@Override
	  public String toString() {
	    return "Product [productId=" + productId + ", productName=" + productName + "+productPrice=" + productPrice + "]";
	    		
	  }
}
