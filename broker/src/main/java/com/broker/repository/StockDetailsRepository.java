package com.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.broker.modal.StockDetails;

public interface StockDetailsRepository extends JpaRepository<StockDetails, Long> {

	StockDetails findByStockCode(String stockCode);

	

}
