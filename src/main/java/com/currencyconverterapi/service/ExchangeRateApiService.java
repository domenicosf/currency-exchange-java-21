package com.currencyconverterapi.service;

import com.currencyconverterapi.dto.ExchangeRateApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class ExchangeRateApiService {

    public static final String EXCHANGE_API_PAIR_URL = "pair/%s/%s";
    public static final String EXCHANGE_API_LATEST_URL = "latest/%s";

    private final WebClient webClient;

    public ExchangeRateApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public WebClient.ResponseSpec retrieveData(String URI) {
        return webClient.get().uri(URI).retrieve();
    }

    public ExchangeRateApiResponse convertFromTo(String from, String to) {
        return retrieveData(
                String.format(EXCHANGE_API_PAIR_URL, from, to)).bodyToMono(
                ExchangeRateApiResponse.class).block();
    }

    public ExchangeRateApiResponse getExchangeRatesTo(String to) {
        return retrieveData(
                String.format(EXCHANGE_API_LATEST_URL, to)).bodyToMono(
                ExchangeRateApiResponse.class).block();
    }

}
