package com.broker.dto;

public class PortfolioMetadataDTO {
	private Long stockId; 
	private String stockCode;
	private Long orderAmount;
	private Long orderQuantity;
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public Long getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public PortfolioMetadataDTO(String stockCode, Long orderAmount, Long orderQuantity) {
		super();
		this.stockCode = stockCode;
		this.orderAmount = orderAmount;
		this.orderQuantity = orderQuantity;
	}
	@Override
	public String toString() {
		return "PortfolioMetadataDTO [stockId=" + stockId + ", stockCode=" + stockCode + ", orderAmount=" + orderAmount
				+ ", orderQuantity=" + orderQuantity + "]";
	}
	
		
}
