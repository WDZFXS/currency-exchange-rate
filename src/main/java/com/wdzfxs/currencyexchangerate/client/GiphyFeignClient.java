package com.wdzfxs.currencyexchangerate.client;

import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy-client", url = "${giphy.url}")
public interface GiphyFeignClient {

    @GetMapping("${giphy.api-path.random}")
    ResponseEntity<RandomGif> randomGif(@RequestParam("tag") String tag);

}