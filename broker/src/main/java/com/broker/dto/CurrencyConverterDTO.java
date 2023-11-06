package com.broker.dto;
public class CurrencyConverterDTO {

    private String old_currency;
    private Double old_amount;
    private String new_currency;
    private Double new_amount;

    public String getOld_currency() {
        return old_currency;
    }

    public void setOld_currency(String old_currency) {
        this.old_currency = old_currency;
    }

    public Double getOld_amount() {
        return old_amount;
    }

    public void setOld_amount(Double old_amount) {
        this.old_amount = old_amount;
    }

    public String getNew_currency() {
        return new_currency;
    }

    public void setNew_currency(String new_currency) {
        this.new_currency = new_currency;
    }

    public Double getNew_amount() {
        return new_amount;
    }

    public void setNew_amount(Double new_amount) {
        this.new_amount = new_amount;
    }

    @Override
    public String toString() {
        return "CurrencyConverterDTO{" +
                "old_currency='" + old_currency + '\'' +
                ", old_amount=" + old_amount +
                ", new_currency='" + new_currency + '\'' +
                ", new_amount=" + new_amount +
                '}';
    }
}