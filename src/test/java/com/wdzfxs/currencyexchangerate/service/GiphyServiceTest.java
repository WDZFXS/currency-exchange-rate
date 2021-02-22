package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.GiphyFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GiphyServiceTest {

    @Autowired
    private GiphyService service;
    @MockBean
    private GiphyFeignClient client;

    @Test
    void throw_unauthorized_when_forbidden() {
        when(client.randomGif(anyString())).thenThrow(FeignException.Forbidden.class);

        assertThrows(GiphyResponseException.Unauthorized.class, () -> service.randomGif("rich"));
    }

    @Test
    void throw_tooManyRequests_when_tooManyRequests() {
        when(client.randomGif(anyString())).thenThrow(FeignException.TooManyRequests.class);

        assertThrows(GiphyResponseException.TooManyRequests.class, () -> service.randomGif("rich"));
    }
}