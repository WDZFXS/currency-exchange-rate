package com.wdzfxs.currencyexchangerate.service;

public class MainServiceException {

    public static class EmptyCurrencyRate extends RuntimeException {
        public EmptyCurrencyRate(String message) {
            super(message);
        }
    }

    public static class DifferentCurrencyRateSize extends RuntimeException {
        public DifferentCurrencyRateSize(String message) {
            super(message);
        }
    }
}