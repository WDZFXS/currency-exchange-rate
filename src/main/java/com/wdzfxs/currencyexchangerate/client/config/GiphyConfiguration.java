package com.wdzfxs.currencyexchangerate.client.config;

import com.wdzfxs.currencyexchangerate.client.config.codec.GiphyErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GiphyConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GiphyErrorDecoder();
    }
}