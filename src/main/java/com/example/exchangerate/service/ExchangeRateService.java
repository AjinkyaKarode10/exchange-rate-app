package com.example.exchangerate.service;

import com.example.exchangerate.apiresponse.ExchangeRateApiResponse;
import com.example.exchangerate.domain.CurrencyExchangeRate;
import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;

import java.util.List;

public interface ExchangeRateService {

  ExchangeRateResponse fetchExchangeRateForCurrencies();

  ExchangeRateResponseInRange fetchExchangeRateForCurrencies(String targetCurrency);

  ExchangeRateApiResponse fetchFromExternalApi(String fromCurrency, List<String> toCurrencies);
}
