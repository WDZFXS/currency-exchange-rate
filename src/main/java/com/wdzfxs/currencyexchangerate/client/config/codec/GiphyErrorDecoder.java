package com.wdzfxs.currencyexchangerate.client.config.codec;

import com.wdzfxs.currencyexchangerate.client.GiphyResponseException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class GiphyErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 403:
                return new GiphyResponseException.Unauthorized();
            case 429:
                return new GiphyResponseException.TooManyRequests();
        }

        return new RuntimeException();
    }
}