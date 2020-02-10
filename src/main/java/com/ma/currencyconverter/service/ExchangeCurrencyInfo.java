package com.ma.currencyconverter.service;

import com.google.gson.JsonObject;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public class ExchangeCurrencyInfo {

    private String currencySymbol;
    private Locale numberLocale;
    private double rate;
    private double amount;
    private String currency;
    private String exchangeCurrency;
    private double exchangeAmount;
    private String localNumberFormatStr;


    public void setSymbolLocaleInfo(JsonObject allRates, String inputCurrency){
        Optional<String> matchCurrency =  allRates.keySet()
                .stream()
                .filter(key -> key.equalsIgnoreCase(inputCurrency))
                .findAny();
        String currency = null;
        if(matchCurrency.isPresent()) {
           currency =  matchCurrency.get();
        }
        // There are 37 countries using euro.
        // I need at least one country to get euro currency symbol
        // and number format. I assume all European country has
        // same number format
        if(null != currency &&  currency.equalsIgnoreCase("eur")){
            currency = "FI";
        }
        if(null != currency){
            String currencyCode = currency.substring(0,2);
            Locale locale = new Locale("EN",currencyCode);
            numberLocale = new Locale(currencyCode);
            Currency symbolCurrency = Currency.getInstance(locale);
            currencySymbol = symbolCurrency.getSymbol();
            System.out.println(currencySymbol);
            setLocalNumberFormatStr();
        }
    }

    private void setLocalNumberFormatStr(){
        NumberFormat nF = NumberFormat.getInstance(numberLocale);
        nF.setMinimumFractionDigits(2);
        nF.setMaximumFractionDigits(4);
        localNumberFormatStr = "Exchange rate is: "+rate+". Amount "
                + amount +" "+currency+" total exchanged amount "
                + exchangeAmount+" "+currencySymbol;

    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Locale getNumberLocale() {
        return numberLocale;
    }

    public void setNumberLocale(Locale numberLocale) {
        this.numberLocale = numberLocale;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(double exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public String getLocalNumberFormatStr() {
        return localNumberFormatStr;
    }

    public void setLocalNumberFormatStr(String localNumberFormatStr) {
        this.localNumberFormatStr = localNumberFormatStr;
    }

    public void setExchangeCurrency(String exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }
}
