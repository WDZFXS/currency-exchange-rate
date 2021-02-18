package com.wdzfxs.currencyexchangerate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphy-media-client", url = "https://i.giphy.com")
public interface MediaFeignClient {

    @GetMapping(value = "/media/{id}/giphy.gif")
    ResponseEntity<byte[]> gif(@PathVariable("id") String id);
}