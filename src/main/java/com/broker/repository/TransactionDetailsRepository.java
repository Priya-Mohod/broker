package com.broker.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.broker.modal.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
	
	@Query(nativeQuery = true, value = "select td.stock_code, sum(td.order_amount) transaction_amount, sum(td.ordered_quantity) ordered_quantity from transaction_details td where td.transaction_type = 'Purchase' group by td.stock_code")
	List<Map<String,Object>> getPurchaseOrderDetails();

	@Query(nativeQuery = true, value = "select td.stock_code, sum(td.order_amount) transaction_amount, sum(td.ordered_quantity) ordered_quantity from transaction_details td where td.transaction_type = 'Sell' group by td.stock_code")
	List<Map<String,Object>> getSellOrderDetails();


			

}
