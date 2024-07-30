package com.example.exchangerate.utility;

import com.example.exchangerate.apiresponse.ExchangeRateApiResponse;
import com.example.exchangerate.apiresponse.ExchangeRateApiResponseInRange;
import com.example.exchangerate.service.ExchangeRateServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ExternalApiUtility {

  @Autowired
  RestTemplate restTemplate;

  @Value("${exchange.rate.baseurl}")
  private String baseUrl;

  private static final Logger log = LoggerFactory.getLogger(ExternalApiUtility.class);

  public ExchangeRateApiResponse fetchResponse(String fromCurrency, List<String> toCurrency){
    StringBuilder urlBuilder = new StringBuilder(baseUrl);
    urlBuilder.append("/latest?from=").append(fromCurrency).append("&to=").append(String.join(",",toCurrency));
    String finalUrl = urlBuilder.toString();//baseUrl+"/latest?from="+fromCurrency+"&to="+String.join(",",toCurrency);
    System.out.println("Final Url "+finalUrl);// Replace with the actual URL
    try {
      return restTemplate.getForObject(finalUrl, ExchangeRateApiResponse.class);
    } catch (Exception e) {
      log.error("Error in fetching API response {}",e.getMessage());
      return null;
    }
  }

  public ExchangeRateApiResponseInRange fetchResponseInRange(String fromDate,String toDate,String fromCurrency, String targetCurrency){
    StringBuilder urlBuilder = new StringBuilder(baseUrl);
    urlBuilder.append("/");
    urlBuilder.append(fromDate).append("..").append(toDate).append("?from=").append(fromCurrency)
        .append("&to=").append(targetCurrency);
    String finalUrl = urlBuilder.toString();
    System.out.println("Final Url "+finalUrl);
    try {
      return restTemplate.getForObject(finalUrl, ExchangeRateApiResponseInRange.class);
    } catch (Exception e) {
      log.error("Error in fetching API response in Range {}",e.getMessage());
      return null;
    }
  }
}
