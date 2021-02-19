package com.wdzfxs.currencyexchangerate.service;

public interface MediaService {

    /*
    Returns byte[] image of picture that was received by ID on the https://i.giphy.com
     */
    byte[] gif(String id);
}