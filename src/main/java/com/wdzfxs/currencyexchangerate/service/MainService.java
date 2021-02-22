package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.OwnUtils;
import com.wdzfxs.currencyexchangerate.dto.Currency;
import com.wdzfxs.currencyexchangerate.model.InvalidSymbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
public class MainService {

    private final CurrencyService currencyService;
    private final GiphyService giphyService;
    private final MediaService mediaService;
    @Value("${giphy.tag.positive}")
    private String positiveTag;
    @Value("${giphy.tag.negative}")
    private String negativeTag;

    public MainService(CurrencyService currencyService, GiphyService giphyService, MediaService mediaService) {
        this.currencyService = currencyService;
        this.giphyService = giphyService;
        this.mediaService = mediaService;
    }

    public byte[] rate(String symbol) {
        Currency byDate = currencyService.symbolRateByDate(OwnUtils.formattedDateDayAgo(), symbol);
        Currency current = currencyService.latestSymbolRate(symbol);

        if (estimate(byDate, current)) {
            return mediaService.gif(randomGifIdByTag(positiveTag));
        } else {
            return mediaService.gif(randomGifIdByTag(negativeTag));
        }
    }

    public ResponseEntity invalidSymbol() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON.toString());
        InvalidSymbol body = new InvalidSymbol("Invalid symbol", currencyService.allCurrencies());
        return new ResponseEntity(body, httpHeaders, 400);
    }

    private String randomGifIdByTag(String tag) {
        return giphyService.randomGif(tag).getId();
    }

    /*
    Returns true if current currency rate above than currency rate by date
     */
    protected boolean estimate(Currency currencyByDate, Currency currentCurrency) {
        if (currencyByDate.getRates().size() != currentCurrency.getRates().size()) {
            log.error("Unacceptable Currency.getRates() size");
        }

        return currentCurrency.getRates().entrySet().stream()
                .allMatch(e -> e.getValue() > currencyByDate.getRates().get(e.getKey()));
    }
}