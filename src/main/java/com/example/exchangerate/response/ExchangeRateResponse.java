package com.example.exchangerate.response;


import java.util.Date;
import java.util.List;

public class ExchangeRateResponse {

  private String sourceCurrency;

  private List<ExchangeRates> exchangeRates;

  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }


  public List<ExchangeRates> getExchangeRates() {
    return exchangeRates;
  }

  public void setExchangeRates(
      List<ExchangeRates> exchangeRates) {
    this.exchangeRates = exchangeRates;
  }
}
