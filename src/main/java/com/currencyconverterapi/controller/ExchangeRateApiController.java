package com.currencyconverterapi.controller;

import com.currencyconverterapi.dto.ExchangeRateApiResponse;
import com.currencyconverterapi.service.ExchangeRateApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateApiController {


    private final ExchangeRateApiService service;

    public ExchangeRateApiController(ExchangeRateApiService service) {
        this.service = service;
    }

    @GetMapping("/pair/{from}/{to}")
    public ExchangeRateApiResponse convertFromTo(@PathVariable String from, @PathVariable String to){
        return service.convertFromTo(from, to);
    }

    @GetMapping("/latest/{to}")
    public ExchangeRateApiResponse getExchangeRatesTo(@PathVariable String to){
        return service.getExchangeRatesTo( to);
    }

}
