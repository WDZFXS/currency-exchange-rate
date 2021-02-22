package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.CurrencyFeignClient;
import com.wdzfxs.currencyexchangerate.dto.Currency;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.wdzfxs.currencyexchangerate.service.CurrencyResponseException.*;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyFeignClient client;

    public CurrencyServiceImpl(CurrencyFeignClient client) {
        this.client = client;
    }

    @Override
    public Map<String, String> allCurrencies() {
        return client.allCurrencies().getBody();
    }

    @Override
    public Currency latestSymbolRate(String symbol) {
        return handle(null, symbol);
    }

    @Override
    public Currency symbolRateByDate(String date, String symbol) {
        return handle(date, symbol);
    }

    private Currency handle(String date, String symbol) {
        try {
            if (date != null) {
                return client.symbolRateByDate(date, symbol).getBody();
            } else return client.latestSymbolRate(symbol).getBody();
        }
        catch (FeignException.BadRequest e) {
            throw new InvalidBase();
        }
        catch (FeignException.Forbidden e) {
            throw new AccessRestricted();
        }
        catch (FeignException.Unauthorized e) {
            String message = e.getMessage();

            if (message.contains("invalid_app_id")) {
                throw new InvalidAppId(message);
            } else if (message.contains("missing_app_id")) {
                throw new MissingAppId(message);
            } else throw new RuntimeException();
        }
        catch (FeignException.NotFound e) {
            throw new NotFound();
        }
        catch (FeignException.TooManyRequests e) {
            throw new NotAllowed();
        }
    }
}