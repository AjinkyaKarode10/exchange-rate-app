package com.example.exchangerate.response;

public class ExchangeRatesWithDate {

  private String conversionDate;
  private String targetCurrency;
  private Double rate;

  public String getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
  }

  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

  public String getConversionDate() {
    return conversionDate;
  }

  public void setConversionDate(String conversionDate) {
    this.conversionDate = conversionDate;
  }
}
