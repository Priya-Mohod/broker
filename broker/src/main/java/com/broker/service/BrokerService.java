package com.broker.service;

import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.broker.dto.CurrencyConverterDTO;
import com.broker.dto.PortfolioDetailsDTO;
import com.broker.dto.PortfolioMetadataDTO;
import com.broker.dto.StockDetailsDTO;
import com.broker.dto.StockDetailsJosnMapper;
import com.broker.dto.StockMetadata;
import com.broker.modal.CurrencyMetadata;
import com.broker.modal.StockDetails;
import com.broker.modal.TransactionDetails;
import com.broker.modal.User;
import com.broker.repository.CurrencyMetadataRepository;
import com.broker.repository.StockDetailsRepository;
import com.broker.repository.TransactionDetailsRepository;
import com.broker.repository.UserRepository;
import com.broker.util.StockBrokerUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Service
public class BrokerService {
	
	@Autowired
	private StockDetailsRepository stockDetailsRepository;
	
	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurrencyMetadataRepository currencyMetadataRepository;
	
	
	
	@Autowired
	private StockBrokerUtil util;
	
	public List<StockDetails> getStockDetails() {
		 List<StockDetails> stockDetails = stockDetailsRepository.findAll();
		 Double currencyValue = util.getCurrencyValueForLoggedInUser();
		 if(Objects.nonNull(stockDetails))
			 stockDetails.forEach(e->e.setPrice((float)(e.getPrice()*currencyValue)));
		 return stockDetails;
	}

	public StockDetails findByStockId(Long id) {
		StockDetails details = stockDetailsRepository.findById(id).get();
		details.setPrice((float)(details.getPrice()*util.getCurrencyValueForLoggedInUser()));
		return details;
	}

	public void executePurchaseOrder(Long id, Long orderQuantity) {
		StockDetails details = stockDetailsRepository.findById(id).get();
		
		details.setAvailableQuantity(details.getAvailableQuantity()-orderQuantity);
		stockDetailsRepository.save(details);
		
		User user = userRepository.findById(1L).get();
		user.setAccountBalance(user.getAccountBalance() - 
				Long.valueOf(Float.valueOf((util.getCurrencyValueForLoggedInUser().floatValue() * Float.valueOf(orderQuantity*details.getPrice()))).longValue()));
		userRepository.save(user);
		
		//add order details in transaction details
		TransactionDetails transaction = new TransactionDetails();
		transaction.setOrderAmount(orderQuantity*details.getPrice());
		transaction.setOrderedQuantity(orderQuantity);
		transaction.setPrice(details.getPrice());
		transaction.setStockCode(details.getStockCode());
		transaction.setTransactionType("Purchase");
		transaction.setOrderDate(Instant.now());
		transactionDetailsRepository.save(transaction);
	}

public void executeSellOrder(Long id, Long orderQuantity) {
		
		StockDetails details = stockDetailsRepository.findById(id).get();
		
		details.setAvailableQuantity(details.getAvailableQuantity()+orderQuantity);
		stockDetailsRepository.save(details);
		
		User user = userRepository.findById(1L).get();
		user.setAccountBalance(user.getAccountBalance() + 
				Long.valueOf(Float.valueOf((util.getCurrencyValueForLoggedInUser().floatValue() * Float.valueOf(orderQuantity*details.getPrice()))).longValue()));

		//user.setAccountBalance(user.getAccountBalance() + Float.valueOf(orderQuantity*details.getPrice()).longValue());
		
		userRepository.save(user);
		
		//add order details in transaction details
		TransactionDetails transaction = new TransactionDetails();
		transaction.setOrderAmount(orderQuantity*details.getPrice());
		transaction.setOrderedQuantity(orderQuantity);
		transaction.setPrice(details.getPrice());
		transaction.setStockCode(details.getStockCode());
		transaction.setTransactionType("Sell");
		transaction.setOrderDate(Instant.now());
		transactionDetailsRepository.save(transaction);
		
	
		
	}

public List<TransactionDetails> getOrderDetails() {
	List<TransactionDetails> trans = transactionDetailsRepository.findAll();
	if(Objects.nonNull(trans)) {
		Double currencyValue = util.getCurrencyValueForLoggedInUser();
		trans.forEach(t->{
			t.setOrderAmount((float)(t.getOrderAmount()*currencyValue));
			t.setPrice(((float)(t.getPrice()*currencyValue)));
		});
	}
		
		
	return transactionDetailsRepository.findAll();
}

public PortfolioDetailsDTO getPortfolioForUser() {
	
	Double currencyValue = util.getCurrencyValueForLoggedInUser();
	
	PortfolioDetailsDTO portfolio = new PortfolioDetailsDTO();
	
	List<Map<String,Object>> purchase = transactionDetailsRepository.getPurchaseOrderDetails();
	Map<String, PortfolioMetadataDTO> purchaseMap = new HashMap<>();
	if(!CollectionUtils.isEmpty(purchase))
		purchase.forEach(e-> purchaseMap.put((String) e.get("stock_code"), new PortfolioMetadataDTO((String) 
				e.get("stock_code"), Float.valueOf(e.get("transaction_amount").toString()).longValue(), 
				Long.parseLong(e.get("ordered_quantity").toString()))));
	
	List<Map<String,Object>> sell = transactionDetailsRepository.getSellOrderDetails();
	Map<String, PortfolioMetadataDTO> sellMap = new HashMap<>();
	if(!CollectionUtils.isEmpty(sell))
		sell.forEach(e-> sellMap.put((String) e.get("stock_code"), new PortfolioMetadataDTO((String) 
				e.get("stock_code"), Float.valueOf(e.get("transaction_amount").toString()).longValue(), 
				Long.parseLong(e.get("ordered_quantity").toString()))));
	
	try {
		System.out.println("Purchase orders : "+new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(purchaseMap));
		System.out.println("Sell Order : "+new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(sellMap));
		
	} catch(Exception e) {}
	
	
	purchaseMap.keySet().forEach(e-> {
		if(sellMap.containsKey(e)) {
			purchaseMap.get(e).setOrderAmount(purchaseMap.get(e).getOrderAmount() - sellMap.get(e).getOrderAmount());
			purchaseMap.get(e).setOrderQuantity(purchaseMap.get(e).getOrderQuantity() - sellMap.get(e).getOrderQuantity());
//			purchaseMap.get(e).setOrderAmount(purchaseMap.get(e).getOrderAmount() - sellMap.get(e).getOrderAmount());
			
		}
	});
	List<StockDetailsDTO> stockList = new ArrayList<>();
	purchaseMap.keySet().forEach(e->{
		if(purchaseMap.get(e).getOrderQuantity()>0) {
			StockDetailsDTO dto = new StockDetailsDTO();
			StockDetails sd = stockDetailsRepository.findByStockCode(e);
			//dto.setPurchasedValue(((Double)(purchaseMap.get(e).getOrderAmount()*currencyValue)).longValue());
			dto.setId(sd.getId());
			dto.setPurchasedQuantity(purchaseMap.get(e).getOrderQuantity());
			dto.setStockName(sd.getStockName());
			stockList.add(dto);
		}
			
		
	});
	portfolio.setAccountBalance(userRepository.findById(1L).get().getAccountBalance());
	portfolio.setStocks(stockList);
	

	return portfolio;
}

public void updateStockDetailsFromJson() throws Exception{
	
    StockDetailsJosnMapper mapperArr[] = new ObjectMapper().readValue(Paths.get("src/main/resources/StockUpdate.json").toFile(), StockDetailsJosnMapper[].class);
    
    for (StockDetailsJosnMapper itr : mapperArr) {
       StockDetails stockDetails = stockDetailsRepository.findByStockCode(itr.getStockCode());
       stockDetails.setStockName(itr.getStockName());
       stockDetails.setPrice(Float.parseFloat(itr.getStockPrice()));
       stockDetailsRepository.save(stockDetails);
    }
    
    try {
    System.out.println("Calling get stock details API");
    OkHttpClient client = new OkHttpClient();
    System.setProperty("com.sun.security.enableAIAcaIssuers", "true");
    Request request = new Request.Builder()
            .url("https://financialmodelingprep.com/api/v3/stock/list?apikey=ee2f81cc2fe3e765f615ac7af1ab6bd8")
            .get().build();
    Response response = client.newCall(request).execute();
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    List<StockMetadata> metadata = Arrays.asList(mapper.readValue(response.body().string(), StockMetadata[].class));
    metadata = metadata.stream().filter(e -> e.getSymbol().equalsIgnoreCase("TMX") || e.getSymbol().equalsIgnoreCase("TLS")).collect(Collectors.toList());
    for (StockMetadata record : metadata) {
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(record));
        System.out.println("Updating stock details for : "+record.getName());
        StockDetails stockDetails=stockDetailsRepository.findByStockCode(record.getSymbol());
        stockDetails.setStockName(record.getName());
        stockDetails.setPrice(record.getPrice().floatValue());
        stockDetailsRepository.save(stockDetails);
        
    }
    }catch (Exception e){
    	System.out.println("Unable to connect Stock Service API - " +e.getMessage());
    }

}


/*
 * Line 206 to 214 referred Rapid API site to call the External API
 * 
 * Link - https://api.api-ninjas.com/v1/convertcurrency?have=gbp&want=inr&amount=1
 * */


public void updateCurrencyForUser(String currency) throws Exception {

	try {
		System.out.println("Calling currency exchange API");
		OkHttpClient client = new OkHttpClient();
		System.setProperty("com.sun.security.enableAIAcaIssuers", "true");
		Request request = new Request.Builder()
				.url("https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency?have=GBP&want="
						+ currency + "&amount=1")
				.get().addHeader("X-RapidAPI-Key", "625c47d36emsh407cc32cfaa3b56p1ae628jsn43b12fd6a322")
				.addHeader("X-RapidAPI-Host", "currency-converter-by-api-ninjas.p.rapidapi.com").build();

		Response response = client.newCall(request).execute();

		CurrencyConverterDTO currencyDto = new ObjectMapper().readValue(response.body().string(),
				CurrencyConverterDTO.class);

		System.out.println("Currency Exchange API Response:\n"
				+ new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(currencyDto));

		CurrencyMetadata currencyMetadata = currencyMetadataRepository.findByCurrencyName(currency);
		currencyMetadata.setCurrencyValue(currencyDto.getNew_amount());
		currencyMetadataRepository.save(currencyMetadata);
		System.out.println("Updated latest values for selected currency in the database");
		
	} catch (Exception e) {
		System.out.print("Failed to call currency exchange API. Using already saved values for user selected currency: "
				+ currency);
	}

	User user = userRepository.findById(1L).get();
	user.setCurrency(currency);
	userRepository.save(user);
	System.out.println("Updated user currency prefrence to : "+currency);
	
	
}

public void updateAccountBalanceForUser(Long accountBalance) {
	User user = userRepository.findById(1L).get();
	user.setAccountBalance(user.getAccountBalance()+accountBalance);
	userRepository.save(user);
}

}
