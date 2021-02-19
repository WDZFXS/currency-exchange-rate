package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.MediaFeignClient;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaFeignClient client;

    public MediaServiceImpl(MediaFeignClient client) {
        this.client = client;
    }

    @Override
    public byte[] gif(String id) {
        return client.gif(id).getBody();
    }
}