package com.wdzfxs.currencyexchangerate.client.config;

import com.wdzfxs.currencyexchangerate.client.config.codec.CurrencyErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class CurrencyConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CurrencyErrorDecoder();
    }
}