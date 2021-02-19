package com.wdzfxs.currencyexchangerate.client.config.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdzfxs.currencyexchangerate.client.CurrencyResponseException;
import com.wdzfxs.currencyexchangerate.dto.CurrencyError;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class CurrencyErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        CurrencyError error = new CurrencyError();
        try {
            error = mapper.readValue(response.body().asInputStream(), CurrencyError.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (response.status()) {
            case 400:
                return new CurrencyResponseException.InvalidBase();
            case 401:
                if (error.getMessage().equalsIgnoreCase("missing_app_id"))
                    return new CurrencyResponseException.MissingAppId(error.getDescription());
                else if (error.getMessage().equalsIgnoreCase("invalid_app_id"))
                    return new CurrencyResponseException.InvalidAppId(error.getDescription());
            case 403:
                return new CurrencyResponseException.AccessRestricted();
            case 404:
                return new CurrencyResponseException.NotFound();
            case 429:
                return new CurrencyResponseException.NotAllowed();
        }

        return new RuntimeException();
    }
}