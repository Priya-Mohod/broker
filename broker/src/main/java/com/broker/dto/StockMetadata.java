package com.broker.dto;
public class StockMetadata {
    String symbol;
    String name;
    Double price;
    String exchange;
   // String exchangeShortName;
    String type;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

//    public String getExchangeShortName() {
//        return exchangeShortName;
//    }
//
//    public void setExchangeShortName(String exchangeShortName) {
//        this.exchangeShortName = exchangeShortName;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StockMetadata{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", exchange='" + exchange + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}









