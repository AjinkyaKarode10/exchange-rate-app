package com.example.exchangerate.utility;

import com.example.exchangerate.domain.CurrencyExchangeRate;
import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;
import com.example.exchangerate.response.ExchangeRates;
import com.example.exchangerate.response.ExchangeRatesWithDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ResponseTransformer {

  private static final Logger log = LoggerFactory.getLogger(ResponseTransformer.class);

  public ExchangeRateResponse buildResponse(List<CurrencyExchangeRate> currencyExchangeRateList, String sourceCurrency,
                                            LocalDate conversionDate) {
    List<ExchangeRates> exchangeRateList = new ArrayList<>();
    ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
    try{
      for(CurrencyExchangeRate currencyExchangeRate : currencyExchangeRateList){
        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setTargetCurrency(currencyExchangeRate.getTargetCurrency());
        exchangeRates.setRate(currencyExchangeRate.getExchangeRate());
        exchangeRates.setConversionDate(DateUtility.convertLocalDateToString(currencyExchangeRate.getConversionDate()));
        exchangeRateList.add(exchangeRates);
      }
      exchangeRateResponse.setSourceCurrency(sourceCurrency);
      exchangeRateResponse.setExchangeRates(exchangeRateList);
    }catch (Exception e){
      log.error("Error while building Response Object");
    }
    return exchangeRateResponse;
  }

  public ExchangeRateResponseInRange buildResponse(List<CurrencyExchangeRate> currencyExchangeRateList, String sourceCurrency,String fromDate,String toDate) {
    List<ExchangeRates> exchangeRateList = new ArrayList<>();
    ExchangeRateResponseInRange exchangeRateResponse = new ExchangeRateResponseInRange();
    try{
      for(CurrencyExchangeRate currencyExchangeRate : currencyExchangeRateList){
        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setTargetCurrency(currencyExchangeRate.getTargetCurrency());
        exchangeRates.setRate(currencyExchangeRate.getExchangeRate());
        exchangeRates.setConversionDate(DateUtility.convertLocalDateToString(currencyExchangeRate.getConversionDate()));
        exchangeRateList.add(exchangeRates);
      }
      exchangeRateResponse.setSourceCurrency(sourceCurrency);
      exchangeRateResponse.setFromDate(fromDate);
      exchangeRateResponse.setToDate(toDate);
      exchangeRateResponse.setExchangeRates(exchangeRateList);
    }catch (Exception e){
      log.error("Error while building Response Object");
    }
    return exchangeRateResponse;
  }
}
