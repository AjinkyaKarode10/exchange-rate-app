package com.example.exchangerate.service;

import com.example.exchangerate.apiresponse.ExchangeRateApiResponse;
import com.example.exchangerate.apiresponse.ExchangeRateApiResponseInRange;
import com.example.exchangerate.domain.CurrencyExchangeRate;
import com.example.exchangerate.repository.ExchangeRateRepository;
import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;
import com.example.exchangerate.utility.DateUtility;
import com.example.exchangerate.utility.ExternalApiUtility;
import com.example.exchangerate.utility.ResponseTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{

  private static final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
  @Autowired
  ExchangeRateRepository exchangeRateRepository;

  @Autowired
  ExternalApiUtility externalApiUtility;

  @Autowired
  ResponseTransformer responseTransformer;

  @Value("${exchange.rate.from.currency}")
  private String fromCurrency;

  @Value("${exchange.rate.to.currency}")
  private List<String> toCurrency;

  @Override
  public ExchangeRateResponse fetchExchangeRateForCurrencies() {
    LocalDate currentDate = LocalDate.now();
    List<CurrencyExchangeRate> dbExchangeRateList =
        exchangeRateRepository.findByConversionDate(currentDate);
    if (dbExchangeRateList != null && !dbExchangeRateList.isEmpty()) {
      // form response body and return
      log.info("Data present in DB for date :: {}", currentDate);
      return responseTransformer.buildResponse(dbExchangeRateList, fromCurrency, currentDate);
    } else {
      // fetch response from API , save it in DB and return
      log.info("Data fetch from API for date :: {}", currentDate);
      ExchangeRateApiResponse exchangeRateApiResponse =
          fetchExchangeRatesFromExternalAPI(fromCurrency, toCurrency);
      List<CurrencyExchangeRate> dbExchangeRateListForConversionDate =
          exchangeRateRepository.findByConversionDate(
              DateUtility.convertStringToLocalDate(exchangeRateApiResponse.getDate()));
      List<CurrencyExchangeRate> currencyExchangeRateDbList = new ArrayList<>();
      List<CurrencyExchangeRate> currencyExchangeRateResponseList = new ArrayList<>();
      if (exchangeRateApiResponse.getRates() != null) {
        for (Map.Entry<String, Double> entry : exchangeRateApiResponse.getRates().entrySet()) {
          if (dbExchangeRateListForConversionDate != null &&
              compareData(dbExchangeRateListForConversionDate, entry.getKey(),
                  exchangeRateApiResponse.getDate())) {// to avoid duplicate
            currencyExchangeRateDbList.add(
                buildObject(exchangeRateApiResponse, entry.getKey(), entry.getValue()));
          }
          currencyExchangeRateResponseList.add(
              buildObject(exchangeRateApiResponse, entry.getKey(), entry.getValue()));
        }
      }
      saveExchangeRateData(currencyExchangeRateDbList);
      return responseTransformer.buildResponse(currencyExchangeRateResponseList, fromCurrency,
          currentDate);
    }
  }

  @Override
  public ExchangeRateResponseInRange fetchExchangeRateForCurrencies(String targetCurrency) {
    LocalDate currentDate = LocalDate.now();
    LocalDate currentMinusThree = LocalDate.now().minusDays(2);
    List<CurrencyExchangeRate> dbExchangeRateList =
        exchangeRateRepository.findByTargetCurrencyAndConversionDateBetween(targetCurrency,
            currentMinusThree, currentDate);
    if (dbExchangeRateList != null && dbExchangeRateList.size() == 3) {
      // form response body and return
      log.info("Data present in DB for date-range :: {} - {}", currentDate, currentMinusThree);
      return responseTransformer.buildResponse(dbExchangeRateList, fromCurrency,
          DateUtility.convertLocalDateToString(currentMinusThree),
          DateUtility.convertLocalDateToString(currentDate));
    } else {
      // fetch response from API , save it in DB and return
      log.info("Data fetch from API for date :: {} - {}", currentDate, currentMinusThree);
      ExchangeRateApiResponseInRange exchangeRateApiResponse =
          fetchExchangeRatesInRangeFromExternalAPI(
              DateUtility.convertLocalDateToString(currentMinusThree),
              DateUtility.convertLocalDateToString(currentDate), fromCurrency,
              targetCurrency);
      List<CurrencyExchangeRate> currencyExchangeRateDbList = new ArrayList<>();
      List<CurrencyExchangeRate> currencyExchangeRateResponseList = new ArrayList<>();
      if(exchangeRateApiResponse != null){
        for (Map.Entry<String, Map<String, Double>> mainEntry : exchangeRateApiResponse.getRates()
            .entrySet()) {
          CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
          currencyExchangeRate.setSourceCurrency(fromCurrency);
          currencyExchangeRate.setConversionDate(
              DateUtility.convertStringToLocalDate(mainEntry.getKey()));
          Map.Entry<String, Double> nestedEntry = mainEntry.getValue().entrySet().iterator().next();
          currencyExchangeRate.setTargetCurrency(nestedEntry.getKey());
          currencyExchangeRate.setExchangeRate(nestedEntry.getValue());
          if (dbExchangeRateList != null && compareData(dbExchangeRateList, mainEntry.getKey())) {
            currencyExchangeRateDbList.add(currencyExchangeRate);// to avoid duplicate entry in DB
          }
          currencyExchangeRateResponseList.add(currencyExchangeRate);
          saveExchangeRateData(currencyExchangeRateDbList);
        }
      }else{
        throw new RuntimeException("Data Not found in API");
      }
      return responseTransformer.buildResponse(currencyExchangeRateResponseList,
          fromCurrency, DateUtility.convertLocalDateToString(currentMinusThree),
          DateUtility.convertLocalDateToString(currentDate));
    }
  }

  @Override
  public ExchangeRateApiResponse fetchFromExternalApi(String fromCurrency,
                                                      List<String> toCurrencies) {
    return fetchExchangeRatesFromExternalAPI(fromCurrency,toCurrencies);
  }

  private void saveExchangeRateData(List<CurrencyExchangeRate> currencyExchangeRateList){
    exchangeRateRepository.saveAll(currencyExchangeRateList);
  }

  public ExchangeRateApiResponse fetchExchangeRatesFromExternalAPI(String fromCurrency, List<String> toCurrencies){
    return externalApiUtility.fetchResponse(fromCurrency,toCurrencies);
  }

  public ExchangeRateApiResponseInRange fetchExchangeRatesInRangeFromExternalAPI(String fromDate, String toDate, String fromCurrency, String targetCurrency){
    return externalApiUtility.fetchResponseInRange(fromDate,toDate,fromCurrency,targetCurrency);
  }

  private CurrencyExchangeRate buildObject(ExchangeRateApiResponse exchangeRateApiResponse,
                                           String key, Double value) {
    CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
    currencyExchangeRate.setSourceCurrency(exchangeRateApiResponse.getBase());
    currencyExchangeRate.setConversionDate(DateUtility.convertStringToLocalDate(exchangeRateApiResponse.getDate()));
    currencyExchangeRate.setTargetCurrency(key);
    currencyExchangeRate.setExchangeRate(value);
    return currencyExchangeRate;
  }

  private boolean compareData(List<CurrencyExchangeRate> dbExchangeRateList,String dbDate){
    for(CurrencyExchangeRate currencyExchangeRate : dbExchangeRateList){
      if(DateUtility.convertLocalDateToString(currencyExchangeRate.getConversionDate()).equals(dbDate)){
        return false;
      }
    }
    return true;
  }

  private boolean compareData(List<CurrencyExchangeRate> dbExchangeRateList, String currency,
                              String dbDate) {
    for (CurrencyExchangeRate currencyExchangeRate : dbExchangeRateList) {
      if (DateUtility.convertLocalDateToString(currencyExchangeRate.getConversionDate())
          .equals(dbDate) && currency.equals(currencyExchangeRate.getTargetCurrency())) {
        return false;
      }
    }
    return true;
  }
}
