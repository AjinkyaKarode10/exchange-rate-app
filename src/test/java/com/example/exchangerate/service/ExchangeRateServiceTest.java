package com.example.exchangerate.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;
import com.example.exchangerate.response.ExchangeRates;
import com.example.exchangerate.utility.DateUtility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class ExchangeRateServiceTest {

  @Value("${exchange.rate.to.currency}")
  private String toCurrency;

  @Autowired
  ExchangeRateService exchangeRateService;

  @Test
  public void testFetchCurrencyForLatestDate(){
    ExchangeRateResponse exchangeRateResponse = exchangeRateService.fetchExchangeRateForCurrencies();
    assertEquals(exchangeRateResponse.getExchangeRates().size(),
        Arrays.asList(toCurrency.split(",")).size());
  }

  @Test
  public void testFetchCurrencyForLast3Days(){
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = LocalDate.now().minusDays(3);
    ExchangeRateResponseInRange exchangeRateResponse = exchangeRateService.fetchExchangeRateForCurrencies("JPY");
    for(ExchangeRates exchangeRates : exchangeRateResponse.getExchangeRates()){
      assertTrue(DateUtility.convertStringToLocalDate(exchangeRates.getConversionDate())
          .isAfter(startDate) ||
          DateUtility.convertStringToLocalDate(exchangeRates.getConversionDate())
              .isEqual(startDate) ||
          DateUtility.convertStringToLocalDate(exchangeRates.getConversionDate())
              .isBefore(endDate));
    }
  }
}
