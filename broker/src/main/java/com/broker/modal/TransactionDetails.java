package com.broker.modal;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_details")
public class TransactionDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "stock_code")
	private String stockCode;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "ordered_quantity")
	private Long orderedQuantity;

	@Column(name = "order_amount")
	private Float orderAmount;
	
	@Column(name = "order_date")
	private Instant orderDate;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Long orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Float getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Float orderAmount) {
		this.orderAmount = orderAmount;
	}	
	
}
