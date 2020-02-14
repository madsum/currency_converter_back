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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ForeignExchangeRateService {

    private static final Logger logger = LogManager.getLogger(CurrencyConverterController.class);

    private static String europeanCentralBankExchangeUrl = "https://api.exchangeratesapi.io/latest";
    private static String exchangeRateApi = "https://api.exchangerate-api.com/v4/latest/";
    private ExchangeCurrencyInfo exchangeCurrencyInfo;

    /**
     * This will return ExchangeCurrencyInfo instance with all required value
     * @since 10-02-2020
     * @param currency
     * @param exchangeCurrency
     * @param amount
     * @return
     */

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
        // gets actual exchange rate
        double exchangeRate = exchangeRateFormServer(allRates, exchangeCurrency);
        exchangeRate = round(exchangeRate,2);
        // crate new instance of exchangeCurrencyInfo for each new request
        exchangeCurrencyInfo = new ExchangeCurrencyInfo() ;
        exchangeCurrencyInfo.setRate(exchangeRate);
        exchangeCurrencyInfo.setAmount(amount);
        exchangeCurrencyInfo.setCurrency(currency);
        exchangeCurrencyInfo.setExchangeCurrency(exchangeCurrency);
        exchangeCurrencyInfo.setExchangeAmount(round(exchangeRate*amount, 2));
        exchangeCurrencyInfo.setSymbolLocaleInfo(allRates, exchangeCurrency );
        return exchangeCurrencyInfo;
    }

    /**
     * This will return  exchange rate
     * @since 10-02-2020
     * @param allRates
     * @param currency
     * @return
     */

    public double exchangeRateFormServer(JsonObject allRates, String currency) {
        final Double[] exchangeRate = {0d};
        allRates.keySet().stream().filter(key -> key.equalsIgnoreCase(currency)).forEach(key -> {
            Object value = allRates.get(key);
            logger.info("key: " + key + " value: " + value);
            try {
                exchangeRate[0] = Double.parseDouble(value.toString());
            } catch (NumberFormatException ex) {
                throw new NumberFormatException(ex.getMessage());
            }
        });
        return exchangeRate[0];
    }

    /**
     * This will rounds double value to the n places
     * @since 10-02-2020
     * @param value
     * @param places
     * @return
     */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        if(bd.compareTo(new BigDecimal("0.00")) == 0){
           return value;
        }
        return bd.doubleValue();
    }
}