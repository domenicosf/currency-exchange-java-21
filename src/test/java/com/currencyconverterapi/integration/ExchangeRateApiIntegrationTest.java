package com.currencyconverterapi.integration;

import com.currencyconverterapi.dto.ExchangeRateApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @ParameterizedTest
    @CsvSource({"USD,EUR", "EUR,USD", "USD,GBP", "GBP,USD", "GBP,EUR", "EUR,GBP"})
    public void testConvertFromTo(String from, String to){
        ExchangeRateApiResponse conversionResult = given()
                .header("Content-Type", APPLICATION_JSON)
                .header("Accept", APPLICATION_JSON)
                .when()
                .get(String .format("api/exchange-rate/pair/%s/%s", from, to))
                .then()
                .contentType(APPLICATION_JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ExchangeRateApiResponse.class);

        Assertions.assertNotNull(conversionResult);
        Assertions.assertTrue(conversionResult.conversionRate().compareTo(BigDecimal.ZERO) > 0 );
    }

    @ParameterizedTest
    @ValueSource(strings = {"USD", "EUR", "GBP"})
    public void testConvertFro(String to){
        ExchangeRateApiResponse conversionResult = given()
                .header("Content-Type", APPLICATION_JSON)
                .header("Accept", APPLICATION_JSON)
                .when()
                .get(String.format("api/exchange-rate/latest/%s", to))
                .then()
                .contentType(APPLICATION_JSON)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ExchangeRateApiResponse.class);

        Assertions.assertNotNull(conversionResult);
        Assertions.assertTrue(!conversionResult.conversionRates().isEmpty() );
    }
}
