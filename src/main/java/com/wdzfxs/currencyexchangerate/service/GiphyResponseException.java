package com.wdzfxs.currencyexchangerate.service;

/*
List of potential API errors https://developers.giphy.com/docs/api/#response-codes
 */
public class GiphyResponseException {

    public static class Unauthorized extends RuntimeException {
    }

    public static class TooManyRequests extends RuntimeException {
    }
}