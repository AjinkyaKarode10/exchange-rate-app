package com.example.exchangerate.apiresponse;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeRateApiResponseInRange {
  private int amount;
  private String base;
  @JsonProperty(value = "start_date")
  private String startDate;
  @JsonProperty(value = "end_date")
  private String endDate;
  private Map<String, Map<String, Double>> rates;

  // Constructors, getters, and setters
  public ExchangeRateApiResponseInRange() {}

  // Getters and Setters

  public Map<String, Map<String, Double>> getRates() {
    return rates;
  }

  public void setRates(
      Map<String, Map<String, Double>> rates) {
    this.rates = rates;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "ExchangeRateApiResponseInRange{" +
        "amount=" + amount +
        ", base='" + base + '\'' +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", rates=" + rates +
        '}';
  }
}

