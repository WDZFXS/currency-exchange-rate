package com.wdzfxs.currencyexchangerate.dto;

@lombok.Data
public class RandomGif {

    private Data data;

    public String getId() {
        return data.getId();
    }

    @lombok.Data
    public static class Data {

        private String id;
    }
}