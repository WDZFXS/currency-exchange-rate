package com.wdzfxs.currencyexchangerate.client;

import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MediaFeignClientTest {

    @Autowired
    private MediaFeignClient mediaClient;
    @Autowired
    private GiphyFeignClient giphyClient;
    private ResponseEntity<byte[]> mediaGiphyResponse;
    @Value("${giphy.tag.negative}")
    private String tag;

    @BeforeAll
    void setUp() {
        RandomGif randomGif = giphyClient.randomGif(tag).getBody();
        mediaGiphyResponse = mediaClient.gif(Objects.requireNonNull(randomGif).getId());

        if (!mediaGiphyResponse.hasBody()) {
            fail("mediaGiphyResponse hasn't body");
        }
    }

    @Test
    void gif_httpStatus200() {
        assertEquals(HttpStatus.OK, mediaGiphyResponse.getStatusCode());
    }

    @Test
    void gif_bodyByteArrayLengthIsMoreThan0() {
        assertTrue(mediaGiphyResponse.getBody().length > 0);
    }
}