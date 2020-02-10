package com.ma.currencyconverter.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForeignExchangeRateServiceTest {

    private ForeignExchangeRateService foreignExchangeRateService;
    @BeforeAll
    void init(){
        foreignExchangeRateService = new ForeignExchangeRateService();
    }

    @Test
    void getExchangeCurrencyInfoTest() throws IOException {
        ExchangeCurrencyInfo exchangeCurrencyInfo =  foreignExchangeRateService.getExchangeCurrencyInfo("eur","gbp", 10d);
        assertTrue(exchangeCurrencyInfo.getRate() > 0);
        assertTrue(exchangeCurrencyInfo.getExchangeAmount() > 0);
    }

    @Test
    void exchangeRateFormServer() {
    }
}