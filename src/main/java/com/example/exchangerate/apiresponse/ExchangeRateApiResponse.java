package com.example.exchangerate.apiresponse;


import java.util.Map;

public class ExchangeRateApiResponse {
  private int amount;
  private String base;
  private String date;
  private Map<String,Double> rates;

  // Constructors, getters, and setters
  public ExchangeRateApiResponse() {}

  public ExchangeRateApiResponse(int amount, String base, String date, Map<String,Double> rates) {
    this.amount = amount;
    this.base = base;
    this.date = date;
    this.rates = rates;
  }

  // Getters and Setters
  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Map<String, Double> getRates() {
    return rates;
  }

  public void setRates(Map<String, Double> rates) {
    this.rates = rates;
  }
}

