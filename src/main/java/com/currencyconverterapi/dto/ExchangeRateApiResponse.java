package com.currencyconverterapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record ExchangeRateApiResponse(
        String result,
        String documentation,
        @JsonProperty("terms_of_use")
        String termsOfUse,
        @JsonProperty("time_last_update_unix")
        Long timeLastUpdateUnix,
        @JsonProperty("time_last_update_utc")
        String timeLastUpdateUtc,
        @JsonProperty("time_next_update_unix")
        Long timeNextUpdateUnix,
        @JsonProperty("time_next_update_utc")
        String timeNextUpdateUtc,
        @JsonProperty("base_code")
        String baseCode,
        @JsonProperty("target_code")
        String targetCode,
        @JsonProperty("conversion_rate")
        BigDecimal conversionRate,
        @JsonProperty("conversion_rates")
        Map<String,BigDecimal> conversionRates
) { }
