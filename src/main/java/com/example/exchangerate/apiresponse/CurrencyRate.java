package com.example.exchangerate.apiresponse;

import java.util.Map;

public class CurrencyRate {

  private Map<String,Double> map;

  // Constructors, getters, and setters
  public CurrencyRate() {}

  public Map<String, Double> getMap() {
    return map;
  }

  public void setMap(Map<String, Double> map) {
    this.map = map;
  }
}

