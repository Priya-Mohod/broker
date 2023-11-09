package com.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.broker.modal.CurrencyMetadata;

public interface CurrencyMetadataRepository extends JpaRepository<CurrencyMetadata, Long> {

	CurrencyMetadata findByCurrencyName(String currency);

}
