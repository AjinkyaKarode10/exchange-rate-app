package com.example.exchangerate.repository;

import com.example.exchangerate.domain.CurrencyExchangeRate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate,Long> {

  List<CurrencyExchangeRate> findByConversionDate(LocalDate currentDate);

  List<CurrencyExchangeRate> findByTargetCurrencyAndConversionDateBetween(String targetCurrency,LocalDate fromDate, LocalDate toDate);
}
