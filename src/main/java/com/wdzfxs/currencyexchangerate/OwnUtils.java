package com.wdzfxs.currencyexchangerate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class OwnUtils {

    public static String formattedDateDayAgo() {
        return new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date(Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli()));
    }
}