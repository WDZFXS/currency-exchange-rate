package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.dto.RandomGif;

public interface GiphyService {

    /*
    Returns DTO RandomGif that contains data of random gif image that was found by tag on the https://giphy.com
     */
    RandomGif randomGif(String tag);
}