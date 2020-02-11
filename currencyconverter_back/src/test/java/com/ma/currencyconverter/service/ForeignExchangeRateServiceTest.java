package com.ma.currencyconverter.service;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ForeignExchangeRateServiceTest {

    private static ForeignExchangeRateService foreignExchangeRateService;

    @BeforeAll
    static void init(){
        foreignExchangeRateService = new ForeignExchangeRateService();
    }

    @Test
    void getExchangeCurrencyInfoTest() throws IOException {
        double amount = 10d;
        ExchangeCurrencyInfo exchangeCurrencyInfo =  foreignExchangeRateService
                .getExchangeCurrencyInfo("eur","gbp", amount);
        assertTrue(exchangeCurrencyInfo.getRate() > 0);
        assertTrue(exchangeCurrencyInfo.getExchangeAmount() > 0);
        assertEquals(amount, exchangeCurrencyInfo.getAmount());
    }

    @Test
    void exchangeRateFormServer() {
        JsonObject allRates = new JsonObject();
        double usdRate = 1.6;
        ForeignExchangeRateService mockForeignExchangeRateService = Mockito
                .mock(ForeignExchangeRateService.class);
        when(mockForeignExchangeRateService
                .exchangeRateFormServer(allRates, "usd" ))
                .thenReturn(usdRate);
        assertEquals(usdRate, mockForeignExchangeRateService
                .exchangeRateFormServer(allRates, "usd" ));
    }

    @Test
    void roundTest(){
        double expectedResult = 10.15;
        double res =  foreignExchangeRateService.round(10.1460, 2);
        assertEquals(expectedResult, res, "Expected Result "+expectedResult +" should be equal to "+res);

    }
}