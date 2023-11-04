package com.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.broker.modal.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
