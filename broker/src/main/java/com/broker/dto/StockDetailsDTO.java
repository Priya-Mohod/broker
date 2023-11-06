package com.broker.dto;


  public class StockDetailsDTO {
	  private Long id;
	  private String stockCode;
	  private String stockName;
	  private Long purchasedQuantity;
	  private Long purchasedValue;
	  private Long currentValue=0L;
	  private Long profitPercentage = 0L;
	  
	  
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public Long getPurchasedQuantity() {
		return purchasedQuantity;
	}
	public void setPurchasedQuantity(Long purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}
	public Long getPurchasedValue() {
		return purchasedValue;
	}
	public void setPurchasedValue(Long purchasedValue) {
		this.purchasedValue = purchasedValue;
	}
	public Long getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}
	public Long getProfitPercentage() {
		return profitPercentage;
	}
	public void setProfitPercentage(Long profitPercentage) {
		this.profitPercentage = profitPercentage;
	}
	  
	  
	  
  }
 
