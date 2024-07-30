package com.example.exchangerate.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import com.example.exchangerate.response.ExchangeRateResponse;
import com.example.exchangerate.response.ExchangeRateResponseInRange;
import com.example.exchangerate.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ExchangeRateService exchangeRateService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetExchangeCurrenciesForLatestSuccess() throws Exception {
    // Setup mock behavior
    ExchangeRateResponse exchangeRateResponse = setUpResponseForApiV1();
    when(exchangeRateService.fetchExchangeRateForCurrencies()).thenReturn(exchangeRateResponse);

    // Perform the GET request
    mockMvc.perform(get("/exchange-rate/fx")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$.exchangeRates", hasSize(4)))// since 4 currencies are used
        .andExpect(status().isOk());
  }

  @Test
  public void testGetExchangeCurrenciesForLast3DaysSuccess() throws Exception {
    // Setup mock behavior
    ExchangeRateResponseInRange exchangeRateResponse = setUpResponseForApiV2();
    when(exchangeRateService.fetchExchangeRateForCurrencies("CZK")).thenReturn(exchangeRateResponse);

    // Perform the GET request
    mockMvc.perform(get("/exchange-rate/fx/{targetCurrency}","CZK")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$.exchangeRates", hasSize(3)))
        .andExpect(status().isOk());
  }

  public static ExchangeRateResponse setUpResponseForApiV1() throws IOException {
    String jsonStr = "{\n" +
        "    \"sourceCurrency\": \"USD\",\n" +
        "    \"exchangeRates\": [\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-25\",\n" +
        "            \"targetCurrency\": \"CZK\",\n" +
        "            \"rate\": 23.398\n" +
        "        },\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-25\",\n" +
        "            \"targetCurrency\": \"EUR\",\n" +
        "            \"rate\": 0.92157\n" +
        "        },\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-25\",\n" +
        "            \"targetCurrency\": \"GBP\",\n" +
        "            \"rate\": 0.7767\n" +
        "        },\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-25\",\n" +
        "            \"targetCurrency\": \"JPY\",\n" +
        "            \"rate\": 152.63\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    ObjectMapper objectMapper = new ObjectMapper();
    ExchangeRateResponse exchangeRateResponse = objectMapper.readValue(jsonStr,ExchangeRateResponse.class);
    return  exchangeRateResponse;
  }

  public static ExchangeRateResponseInRange setUpResponseForApiV2() throws IOException {
    String jsonStr = "{\n" +
        "    \"sourceCurrency\": \"USD\",\n" +
        "    \"fromDate\": \"2024-07-24\",\n" +
        "    \"toDate\": \"2024-07-26\",\n" +
        "    \"exchangeRates\": [\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-24\",\n" +
        "            \"targetCurrency\": \"CZK\",\n" +
        "            \"rate\": 23.438\n" +
        "        },\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-25\",\n" +
        "            \"targetCurrency\": \"CZK\",\n" +
        "            \"rate\": 23.398\n" +
        "        },\n" +
        "        {\n" +
        "            \"conversionDate\": \"2024-07-26\",\n" +
        "            \"targetCurrency\": \"CZK\",\n" +
        "            \"rate\": 23.198\n" +
        "        }\n" +
        "    ]\n" +
        "}";
    ObjectMapper objectMapper = new ObjectMapper();
    ExchangeRateResponseInRange exchangeRateResponse = objectMapper.readValue(jsonStr,ExchangeRateResponseInRange.class);
    return  exchangeRateResponse;
  }
}
