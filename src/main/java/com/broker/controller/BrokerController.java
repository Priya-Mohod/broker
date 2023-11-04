package com.broker.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broker.dto.PortfolioDetailsDTO;
import com.broker.modal.StockDetails;
import com.broker.modal.TransactionDetails;
import com.broker.modal.User;
import com.broker.service.BrokerService;
import com.broker.util.StockBrokerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BrokerController {

	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private StockBrokerUtil util;
/*application is starting with Login page */
	
	
	//@ResponseBody
	@RequestMapping("/login")
	public String redirectLogin() {

		System.out.println("hello from login page this is priya");
		return "login";
	}
 
	/*application is starting with Login page */
	
	//@ResponseBody
	@GetMapping("/displayStocks")
	public String displayStocks(Model model) throws Exception {

		List<StockDetails> list = brokerService.getStockDetails();
		System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));
		model.addAttribute("list",list);
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "stocklist";
	}
	@PostMapping("/displayStocks")
	public String displayStocks(Model model, @RequestParam("search") String search) throws Exception {
		
		//return "loginpage";
		List<StockDetails> list = brokerService.getStockDetails();
		if(Objects.nonNull(list) && Objects.nonNull(search))
			list = list.stream().filter(e->e.getStockName().toLowerCase().contains(search.toLowerCase()) || 
					e.getStockCode().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
		System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));
		model.addAttribute("list",list);
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());

		model.addAttribute("search",search);
		return "stocklist";
	}
	
	@GetMapping("/placeOrder")
	public String displayStocks(@RequestParam("type") String type, @RequestParam("id") Long id, Model model) throws Exception {
		
		
		StockDetails details = brokerService.findByStockId(id);
		if(type.equals("buy")) {
			model.addAttribute("type","Purchase Order");
		}
		if(type.equals("sell")) {
			model.addAttribute("type","Sell Order");
		}
		model.addAttribute("stock", details);
		model.addAttribute("stockId", details.getId());
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "placeorder";
	}
	
	@GetMapping("/sellOrder")
	public String displayPurchasedStocks(@RequestParam("type") String type, @RequestParam("id") Long id, @RequestParam("purchasedQuantity") Long purchasedQuantity, Model model) throws Exception {
		
		
		StockDetails details = brokerService.findByStockId(id);
		if(type.equals("buy")) {
			model.addAttribute("type","Purchase Order");
		}
		if(type.equals("sell")) {
			model.addAttribute("type","Sell Order");
			model.addAttribute("purchasedQuantity", purchasedQuantity);
		}
		model.addAttribute("stock", details);
		model.addAttribute("stockId", details.getId());
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		
		return "sellorder";
	}
	
	
	@PostMapping("/executeOrder")
	public String executeOrder(@RequestParam("type") String type, @RequestParam("stockId") Long id, @RequestParam("orderQuantity") Long orderQuantity, Model model) throws Exception {
		
		System.out.println(type);
		System.out.println(id);
		System.out.println(orderQuantity);
		
		if(type.equals("Purchase Order")) {
			brokerService.executePurchaseOrder(id, orderQuantity);
		}
		if(type.equals("Sell Order")) {
				brokerService.executeSellOrder(id, orderQuantity);
		}
		List<TransactionDetails> orderList = brokerService.getOrderDetails();
		orderList.sort(Comparator.comparing(TransactionDetails::getOrderDate).reversed());
		model.addAttribute("orderlist",orderList );
		//System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(orderList));		
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "orderhistory";
	}
	
	@GetMapping("/orderHistory")
	public String orderHistory(Model model) throws Exception {
		
		
		List<TransactionDetails> orderList = brokerService.getOrderDetails();
		orderList.sort(Comparator.comparing(TransactionDetails::getOrderDate).reversed());
		model.addAttribute("orderlist",orderList );
		//System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(orderList));
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "orderhistory";
	}
	
	
	@GetMapping("/portfolio")
	public String portfolio(Model model) throws Exception {
		PortfolioDetailsDTO portfolio = brokerService.getPortfolioForUser();
		System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(portfolio));
		model.addAttribute("portfolio",portfolio );
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "portfolio";
	}
	
	@GetMapping("/updateStockDetailsFromJson")
	public String updateStockDetailsFromJson(Model model) throws Exception {
		brokerService.updateStockDetailsFromJson();
		List<StockDetails> list = brokerService.getStockDetails();
		System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));
		model.addAttribute("list",list);
		//System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(portfolio));
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return "stocklist";
		
	}
	
	@GetMapping("/updateCurrencyForUser")
	public String updateCurrencyForUser(@RequestParam("currency") String currency, @RequestParam("page") String page, Model model) throws Exception {
		brokerService.updateCurrencyForUser(currency);
		String returnPage="";
		if(page.equals("portfolio")) {
			PortfolioDetailsDTO portfolio = brokerService.getPortfolioForUser();
//			System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(portfolio));
			model.addAttribute("portfolio",portfolio );
			returnPage="portfolio";
		}else if(page.equals("orderhistory")) {
			List<TransactionDetails> orderList = brokerService.getOrderDetails();
			orderList.sort(Comparator.comparing(TransactionDetails::getOrderDate).reversed());
			model.addAttribute("orderlist",orderList );
			returnPage= "orderhistory";
		}else if(page.equals("stocklist")){
			List<StockDetails> list = brokerService.getStockDetails();
			model.addAttribute("list",list);
			returnPage="stocklist";
		}
		
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());
		return returnPage;
	}
	
	//@ResponseBody
	@PostMapping("/addAccountBalance")
	public String addAccountBalance( @RequestParam("accountBalance") Long accountBalance , Model model) throws Exception {
		
		brokerService.updateAccountBalanceForUser(accountBalance);
		PortfolioDetailsDTO portfolio = brokerService.getPortfolioForUser();
		//System.out.println( new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(portfolio));
		model.addAttribute("portfolio",portfolio );
		model.addAttribute("currency",util.getCurrencyNameForLoggedInUser());
		model.addAttribute("accountBalance",util.getAvailableBalanceForLoggedInUser());		
		return "portfolio";
	}
	
	@ResponseBody
	@GetMapping("/restOrderHistory")
	public List<TransactionDetails> restOrderHistory(Model model) throws Exception {
		
		List<TransactionDetails> orderList = brokerService.getOrderDetails();
		orderList.sort(Comparator.comparing(TransactionDetails::getOrderDate).reversed());
		
		return orderList;
	}
	
	@ResponseBody
	@GetMapping("/restUpdateCurrencyForUser")
	public String restUpdateCurrencyForUser(@RequestParam("currency") String currency, Model model) throws Exception {
		brokerService.updateCurrencyForUser(currency);
		
		return "success";
	}
	@ResponseBody
	@GetMapping("/restupdateStockDetailsFromJson")
	public List<StockDetails> restupdateStockDetailsFromJson(Model model) throws Exception {
		brokerService.updateStockDetailsFromJson();
		List<StockDetails> list = brokerService.getStockDetails();
		return list;
	}

}

