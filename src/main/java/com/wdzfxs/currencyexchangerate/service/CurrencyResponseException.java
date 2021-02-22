package com.wdzfxs.currencyexchangerate.service;

/*
List of potential API errors https://docs.openexchangerates.org/docs/errors
 */
public class CurrencyResponseException {

    public static class NotFound extends RuntimeException {
    }

    public static class MissingAppId extends RuntimeException {
        public MissingAppId(String message) {
            super(message);
        }
    }

    public static class InvalidAppId extends RuntimeException {
        public InvalidAppId(String message) {
            super(message);
        }
    }

    public static class Unauthorized extends RuntimeException {
        public Unauthorized(String message) {
            super(message);
        }
    }

    public static class NotAllowed extends RuntimeException {
    }

    public static class AccessRestricted extends RuntimeException {
    }

    public static class InvalidBase extends RuntimeException {
    }
}