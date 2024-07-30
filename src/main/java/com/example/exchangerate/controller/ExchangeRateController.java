package com.example.exchangerate.controller;

import com.example.exchangerate.apiresponse.ExchangeRateApiResponse;
import com.example.exchangerate.domain.CurrencyExchangeRate;
import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;
import com.example.exchangerate.service.ExchangeRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

  @Autowired
  ExchangeRateService exchangeRateService;

  @GetMapping("/fx")
  public ExchangeRateResponse findExchangeRates() {
    return exchangeRateService.fetchExchangeRateForCurrencies();
  }

  @GetMapping("/fx/{targetCurrency}")
  public ExchangeRateResponseInRange findExchangeRatesInRange(@PathVariable("targetCurrency") String targetCurrency) {
    return exchangeRateService.fetchExchangeRateForCurrencies(targetCurrency);
  }

}
