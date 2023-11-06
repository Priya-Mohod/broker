package com.broker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.broker.modal.CurrencyMetadata;
import com.broker.modal.User;
import com.broker.repository.CurrencyMetadataRepository;
import com.broker.repository.UserRepository;

@Component
public class StockBrokerUtil {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurrencyMetadataRepository currencyMetadataRepository;

	public Double getCurrencyValueForLoggedInUser() {
		User user = userRepository.findById(1L).get();
		CurrencyMetadata currencyMetadata = currencyMetadataRepository.findByCurrencyName(user.getCurrency());
		return currencyMetadata.getCurrencyValue();
	}

	public String getCurrencyNameForLoggedInUser() {

		return userRepository.findById(1L).get().getCurrency();
	}

	public Long getAvailableBalanceForLoggedInUser() {
		return userRepository.findById(1L).get().getAccountBalance();
	}
}
