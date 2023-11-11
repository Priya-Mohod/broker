package com.broker.modal;



import javax.persistence.*;

@Entity
@Table(name = "stock_details")
public class StockDetails {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "stock_name")
	private String stockName;
	
	@Column(name = "stock_code")
	private String stockCode;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "available_quantity")
	private Long availableQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockName() {
		return stockName;
	}

	@Override
	public String toString() {
		return "StockDetails [id=" + id + ", stockName=" + stockName + ", stockCode=" + stockCode + ", price=" + price
				+ ", availableQuantity=" + availableQuantity + "]";
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
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

	public Long getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Long availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
}
