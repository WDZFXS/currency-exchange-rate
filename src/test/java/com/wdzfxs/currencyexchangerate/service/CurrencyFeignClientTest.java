package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.dto.Currency;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CurrencyFeignClientTest {

    private static ResponseEntity<Map<String, String>> allCurrencies;
    private static ResponseEntity<Currency> latestSymbolRateCurrency;
    private static ResponseEntity<Currency> yesterdaySymbolRateCurrency;
    private static boolean isInit = false;
    @Autowired
    private CurrencyFeignClient client;
    @Value("${base-currency}")
    private String baseCurrency;
    @Value("${test-currency}")
    private String testCurrency;

    private boolean containsKeyIgnoreCase(Map<String, ?> map, String key) {
        return map.containsKey(key.toLowerCase()) || map.containsKey(key.toUpperCase());
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AllCurrencies {

        @BeforeAll
        void setUp() {
            allCurrencies = client.allCurrencies();
            log.info("Currencies = " + allCurrencies);

            if (allCurrencies.getBody() == null) {
                fail("allCurrencies body is null");
            }
        }

        @Test
        void allCurrencies_httpStatus200() {
            assertEquals(HttpStatus.OK, allCurrencies.getStatusCode());
        }

        @Test
        void allCurrencies_notEmpty() {
            assertFalse(allCurrencies.getBody().isEmpty());
        }

        @Test
        void allCurrencies_containsBaseCurrency() {
            assertTrue(containsKeyIgnoreCase(allCurrencies.getBody(), baseCurrency));
        }

        @Test
        void allCurrencies_containsTestCurrency() {
            assertTrue(containsKeyIgnoreCase(allCurrencies.getBody(), testCurrency));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class LatestSymbolRate {

        @BeforeAll
        void setUp() {
            latestSymbolRateCurrency = client.latestSymbolRate(testCurrency);
            log.info("Currency rate = " + latestSymbolRateCurrency);

            if (latestSymbolRateCurrency.getBody() == null) {
                fail("latestSymbolRateCurrency body is null");
            }
        }

        @Test
        void latestSymbolRate_httpStatus200() {
            assertEquals(HttpStatus.OK, latestSymbolRateCurrency.getStatusCode());
        }

        @Test
        void latestSymbolRate_notEmpty() {
            final Map<String, Double> rates = latestSymbolRateCurrency.getBody().getRates();
            assertFalse(rates.isEmpty());
        }

        @Test
        void latestSymbolRate_containsBaseCurrencyValue() {
            assertEquals(baseCurrency, latestSymbolRateCurrency.getBody().getBase());
        }

        @Test
        void latestSymbolRate_containsTestCurrencyValue() {
            final Map<String, Double> rates = latestSymbolRateCurrency.getBody().getRates();
            assertTrue(containsKeyIgnoreCase(rates, testCurrency));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class YesterdaySymbolRate {

        @BeforeAll
        void setUp() {
            yesterdaySymbolRateCurrency = client.yesterdaySymbolRate(new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date(Instant.now()
                            .minus(1, ChronoUnit.DAYS).toEpochMilli())), testCurrency);
            log.info("Yesterday currency rate = " + yesterdaySymbolRateCurrency);

            if (yesterdaySymbolRateCurrency.getBody() == null) {
                fail("yesterdaySymbolRateCurrency body is null");
            }
        }

        @Test
        void yesterdaySymbolRate_httpStatus200() {
            assertEquals(HttpStatus.OK, yesterdaySymbolRateCurrency.getStatusCode());
        }

        @Test
        void yesterdaySymbolRate_notEmpty() {
            final Map<String, Double> rates = yesterdaySymbolRateCurrency.getBody().getRates();
            assertFalse(rates.isEmpty());
        }

        @Test
        void yesterdaySymbolRate_containsBaseCurrencyValue() {
            assertEquals(baseCurrency, yesterdaySymbolRateCurrency.getBody().getBase());
        }

        @Test
        void yesterdaySymbolRate_containsTestCurrencyValue() {
            final Map<String, Double> rates = yesterdaySymbolRateCurrency.getBody().getRates();
            assertTrue(containsKeyIgnoreCase(rates, testCurrency));
        }
    }
}