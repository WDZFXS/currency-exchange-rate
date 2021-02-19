package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.GiphyFeignClient;
import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import org.springframework.stereotype.Service;

@Service
public class GiphyServiceImpl implements GiphyService {

    private final GiphyFeignClient client;

    public GiphyServiceImpl(GiphyFeignClient client) {
        this.client = client;
    }

    @Override
    public RandomGif randomGif(String tag) {
        return client.randomGif(tag).getBody();
    }
}