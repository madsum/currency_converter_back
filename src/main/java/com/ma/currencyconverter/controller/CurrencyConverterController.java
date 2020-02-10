package com.ma.currencyconverter.controller;

import com.ma.currencyconverter.service.ExchangeCurrencyInfo;
import com.ma.currencyconverter.service.ForeignExchangeRateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.NumberFormat;

@RestController
public class CurrencyConverterController {

    private static final Logger logger = LogManager.getLogger(CurrencyConverterController.class);

    @Autowired
    private ForeignExchangeRateService foreignExchangeRateService;

    /**
     * This will send exchanged currency amout in response with HTTP status code 200.
     * @since 10-02-2020
     * @param currency
     * @param exCurrency
     * @param amount
     * @return
     */
    @GetMapping("/excurrency")
    public String getExCurrency(@RequestParam(required=true) String currency,
                                @RequestParam(required=true) String exCurrency,
                                @RequestParam(required=true) String amount) {
        logger.info("amount: "+amount+" currency: "+currency+ " exCurrency: "+exCurrency);
        double doubleAmount = 0d;
        ExchangeCurrencyInfo exchangeCurrencyInfo;
        try {
            doubleAmount = Double.parseDouble(amount);
            exchangeCurrencyInfo = foreignExchangeRateService.getExchangeCurrencyInfo(currency,exCurrency,doubleAmount);
        }catch (NumberFormatException | IOException ex){
            return "It must be a number";
        }
        return exchangeCurrencyInfo.getLocalNumberFormatStr();
    }
}
