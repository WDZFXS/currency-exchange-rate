package com.wdzfxs.currencyexchangerate.client;

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
    private static ResponseEntity<Currency> symbolRateByDateCurrency;
    @Autowired
    private CurrencyFeignClient client;
    @Value("${open-exchange-rates.base-currency}")
    private String baseCurrency;
    @Value("${open-exchange-rates.test-currency}")
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
    public class SymbolRateByDate {

        @BeforeAll
        void setUp() {
            symbolRateByDateCurrency = client.symbolRateByDate(new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date(Instant.now()
                            .minus(1, ChronoUnit.DAYS).toEpochMilli())), testCurrency);
            log.info("Yesterday currency rate = " + symbolRateByDateCurrency);

            if (symbolRateByDateCurrency.getBody() == null) {
                fail("symbolRateByDateCurrency body is null");
            }
        }

        @Test
        void symbolRateByDate_httpStatus200() {
            assertEquals(HttpStatus.OK, symbolRateByDateCurrency.getStatusCode());
        }

        @Test
        void symbolRateByDate_notEmpty() {
            final Map<String, Double> rates = symbolRateByDateCurrency.getBody().getRates();
            assertFalse(rates.isEmpty());
        }

        @Test
        void symbolRateByDate_containsBaseCurrencyValue() {
            assertEquals(baseCurrency, symbolRateByDateCurrency.getBody().getBase());
        }

        @Test
        void symbolRateByDate_containsTestCurrencyValue() {
            final Map<String, Double> rates = symbolRateByDateCurrency.getBody().getRates();
            assertTrue(containsKeyIgnoreCase(rates, testCurrency));
        }
    }
}