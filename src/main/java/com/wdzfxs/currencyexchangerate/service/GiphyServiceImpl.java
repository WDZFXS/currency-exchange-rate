package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.GiphyFeignClient;
import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import feign.FeignException;
import org.springframework.stereotype.Service;

import static com.wdzfxs.currencyexchangerate.service.GiphyResponseException.Unauthorized;

@Service
public class GiphyServiceImpl implements GiphyService {

    private final GiphyFeignClient client;

    public GiphyServiceImpl(GiphyFeignClient client) {
        this.client = client;
    }

    @Override
    public RandomGif randomGif(String tag) {
        try {
            return client.randomGif(tag).getBody();
        } catch (FeignException.Forbidden e) {
            throw new Unauthorized();
        } catch (FeignException.TooManyRequests e) {
            throw new GiphyResponseException.TooManyRequests();
        }
    }
}