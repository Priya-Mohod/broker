package com.broker.dto;

public class StockDetailsJosnMapper {
	    String stockCode;
	    String stockName;
	    String stockPrice;

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

	    public String getStockPrice() {
	        return stockPrice;
	    }

	    public void setStockPrice(String stockPrice) {
	        this.stockPrice = stockPrice;
	    }

	    @Override
	    public String toString() {
	        return "StockDetailsDTO{" +
	                "stockCode='" + stockCode + '\'' +
	                ", stockName='" + stockName + '\'' +
	                ", stockPrice='" + stockPrice + '\'' +
	                '}';
	    }
	

}
