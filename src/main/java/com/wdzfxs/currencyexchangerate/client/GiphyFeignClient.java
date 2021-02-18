package com.wdzfxs.currencyexchangerate.client;

import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy-client", url = "https://api.giphy.com")
public interface GiphyFeignClient {

    @GetMapping("/v1/gifs/random?api_key=${giphy.app-id}")
    ResponseEntity<RandomGif> randomGif(@RequestParam("tag") String tag);

}