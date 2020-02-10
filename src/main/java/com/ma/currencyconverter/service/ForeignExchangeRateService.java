package com.ma.currencyconverter.service;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ma.currencyconverter.controller.CurrencyConverterController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ForeignExchangeRateService {

    private static final Logger logger = LogManager.getLogger(CurrencyConverterController.class);

    private static String europeanCentralBankExchangeUrl = "https://api.exchangeratesapi.io/latest";
    private static String exchangeRateApi = "https://api.exchangerate-api.com/v4/latest/";
    private ExchangeCurrencyInfo exchangeCurrencyInfo = new ExchangeCurrencyInfo() ;

    public ExchangeCurrencyInfo getExchangeCurrencyInfo(String currency, String exchangeCurrency, Double amount ) throws IOException {
        String strUrl;
        // define which url to use for exchange rate
        if (!currency.isEmpty() && !exchangeCurrency.isEmpty()) {
            strUrl = exchangeRateApi + currency;
        } else {
            strUrl = europeanCentralBankExchangeUrl;
        }
        // Making Request
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
        } catch (MalformedURLException e) {
            throw new MalformedURLException(e.getLocalizedMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        // Convert to JSON
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) connection.getContent()));
        JsonObject members = root.getAsJsonObject();
        // Accessing object
        JsonElement rates = members.get("rates");
        JsonObject allRates = rates.getAsJsonObject();
        double rate = exchangeRateFormServer(allRates, exchangeCurrency);
        exchangeCurrencyInfo.setRate(rate);
        exchangeCurrencyInfo.setAmount(amount);
        exchangeCurrencyInfo.setCurrency(currency);
        exchangeCurrencyInfo.setExchangeCurrency(exchangeCurrency);
        exchangeCurrencyInfo.setExchangeAmount(rate*amount);
        exchangeCurrencyInfo.setSymbolLocaleInfo(allRates, exchangeCurrency );
        return exchangeCurrencyInfo;
    }

    double exchangeRateFormServer(JsonObject allRates, String currency) {
        AtomicReference<Double> rate = new AtomicReference<>(0d);
        allRates.keySet().stream().filter(key -> key.equalsIgnoreCase(currency)).forEach(key -> {
            Object value = allRates.get(key);
            logger.info("key: " + key + " value: " + value);
            try {
                rate.set(Double.parseDouble(value.toString()));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException(ex.getMessage());
            }
        });
        return rate.get();
    }
}