package com.wdzfxs.currencyexchangerate.client;

import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import io.netty.util.internal.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiphyFeignClientTest {

    @Autowired
    private GiphyFeignClient giphyFeignClient;

    @Value("${giphy.tag.positive}")
    private String positiveTag;
    @Value("${giphy.tag.negative}")
    private String negativeTag;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithPositiveTag {

        private ResponseEntity<RandomGif> randomGifResponseEntity;
        private RandomGif randomGif;

        @BeforeAll
        void setUp() {
            randomGifResponseEntity = giphyFeignClient.randomGif(positiveTag);
            if (!randomGifResponseEntity.hasBody()) {
                fail("randomGifResponseEntity hasn't body");
            }

            randomGif = randomGifResponseEntity.getBody();
        }

        @Test
        void randomGif_httpStatus200() {
            assertEquals(HttpStatus.OK, randomGifResponseEntity.getStatusCode());
        }

        @Test
        void randomGif_dataIsNotNull() {
            assertNotNull(randomGif.getData());
        }

        @Test
        void randomGif_containsId() {
            assertFalse(StringUtil.isNullOrEmpty(randomGif.getId()));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WithNegativeTag {

        private ResponseEntity<RandomGif> randomGifResponseEntity;
        private RandomGif randomGif;

        @BeforeAll
        void setUp() {
            randomGifResponseEntity = giphyFeignClient.randomGif(negativeTag);
            if (!randomGifResponseEntity.hasBody()) {
                fail("randomGifResponseEntity hasn't body");
            }

            randomGif = randomGifResponseEntity.getBody();
        }

        @Test
        void randomGif_httpStatus200() {
            assertEquals(HttpStatus.OK, randomGifResponseEntity.getStatusCode());
        }

        @Test
        void randomGif_dataIsNotNull() {
            assertNotNull(randomGif.getData());
        }

        @Test
        void randomGif_containsId() {
            assertFalse(StringUtil.isNullOrEmpty(randomGif.getId()));
        }
    }
}