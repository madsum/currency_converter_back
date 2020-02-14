package com.ma.currencyconverter.service;

import com.google.gson.JsonObject;
import com.ma.currencyconverter.controller.CurrencyConverterController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public class ExchangeCurrencyInfo {

    private static final Logger logger = LogManager.getLogger(ExchangeCurrencyInfo.class);
/**
 * This class holds all necessary values for exchange currency
 * @since 11-02-2020
 * */
    private String currencySymbol;
    private Locale numberLocale;
    private double rate;
    private double amount;
    private String currency;
    private String exchangeCurrency;
    private double exchangeAmount;
    private String localNumberFormatStr;

    /**
     * This will set currency symbol and local number format based on
     * the exchange currency
     * @since 10-02-2020
     * @param allRates
     * @param exchangeCurrency
     */
    public void setSymbolLocaleInfo(JsonObject allRates, String exchangeCurrency){
        Optional<String> matchCurrency =  allRates.keySet()
                .stream()
                .filter(key -> key.equalsIgnoreCase(exchangeCurrency))
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
            logger.info("currencySymbol: "+currencySymbol);
            setLocalNumberFormatStr();
        }
    }

    /**
     * This will set a localized string with local number format and
     * the currency symbol
     * the exchange currency
     * @since 10-02-2020
     */
    private void setLocalNumberFormatStr(){
        NumberFormat nF = NumberFormat.getInstance(numberLocale);
        nF.setMinimumFractionDigits(2);
        nF.setMaximumFractionDigits(4);
        localNumberFormatStr = "Exchange rate is: "+nF.format(rate)+".\n"
                + amount +" "+currency+" exchanged amount will be "
                + nF.format(exchangeAmount)+" "+currencySymbol;
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
        if(currency.isEmpty()){
            this.currency = "euro";
        }else{
            this.currency = currency;
        }

    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }
}
