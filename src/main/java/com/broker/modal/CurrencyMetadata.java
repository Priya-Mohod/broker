package com.broker.modal;
import javax.persistence.*;

@Entity
@Table(name = "currency_metadata")
public class CurrencyMetadata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "currency_name")
	private String currencyName;
	
	@Column(name = "currency_value")
	private Double currencyValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Double getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(Double currencyValue) {
		this.currencyValue = currencyValue;
	}	
	
}
