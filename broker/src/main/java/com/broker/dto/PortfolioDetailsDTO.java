package com.broker.dto;

import java.util.List;

public class PortfolioDetailsDTO {
	
	List<StockDetailsDTO> stocks;
	Long accountBalance;
	
	public List<StockDetailsDTO> getStocks() {
		return stocks;
	}
	public void setStocks(List<StockDetailsDTO> stocks) {
		this.stocks = stocks;
	}
	public Long getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}
	
}
