package com.example.exchangerate.response;


import java.util.Date;
import java.util.List;

public class ExchangeRateResponseInRange {

  private String sourceCurrency;

  private String fromDate;

  private String toDate;

  private List<ExchangeRates> exchangeRates;

  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public List<ExchangeRates> getExchangeRates() {
    return exchangeRates;
  }

  public void setExchangeRates(
      List<ExchangeRates> exchangeRates) {
    this.exchangeRates = exchangeRates;
  }
}
