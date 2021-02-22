package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.CurrencyFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrencyServiceTest {

    @Autowired
    private CurrencyService service;
    @MockBean
    private CurrencyFeignClient client;

    @Test
    void throw_invalidBase_when_badRequest() {
        when(client.latestSymbolRate(anyString())).thenThrow(FeignException.BadRequest.class);

        assertThrows(CurrencyResponseException.InvalidBase.class,
                () -> service.latestSymbolRate("RUB"));
    }

    @Test
    void throw_invalidAppId_when_unauthorized() {
        FeignException.Unauthorized unauthorized = Mockito.mock(FeignException.Unauthorized.class);
        when(unauthorized.getMessage()).thenReturn("invalid_app_id");
        when(client.latestSymbolRate(anyString())).thenThrow(unauthorized);

        assertThrows(CurrencyResponseException.InvalidAppId.class,
                () -> service.latestSymbolRate("RUB"));
    }

    @Test
    void throw_missingAppId_when_unauthorized() {
        FeignException.Unauthorized unauthorized = Mockito.mock(FeignException.Unauthorized.class);
        when(unauthorized.getMessage()).thenReturn("missing_app_id");
        when(client.latestSymbolRate(anyString())).thenThrow(unauthorized);

        assertThrows(CurrencyResponseException.MissingAppId.class,
                () -> service.latestSymbolRate("RUB"));
    }

    @Test
    void throw_accessRestricted_when_Forbidden() {
        when(client.latestSymbolRate(anyString())).thenThrow(FeignException.Forbidden.class);

        assertThrows(CurrencyResponseException.AccessRestricted.class,
                () -> service.latestSymbolRate("RUB"));
    }

    @Test
    void throw_notFound_when_notFound() {
        when(client.latestSymbolRate(anyString())).thenThrow(FeignException.NotFound.class);

        assertThrows(CurrencyResponseException.NotFound.class,
                () -> service.latestSymbolRate("RUB"));
    }

    @Test
    void throw_notAllowed_when_tooManyRequests() {
        when(client.latestSymbolRate(anyString())).thenThrow(FeignException.TooManyRequests.class);

        assertThrows(CurrencyResponseException.NotAllowed.class,
                () -> service.latestSymbolRate("RUB"));
    }
}