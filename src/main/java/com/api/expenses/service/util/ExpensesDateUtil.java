package com.api.expenses.service.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by roxana on 23.07.2016.
 */
public final class ExpensesDateUtil {

    private ExpensesDateUtil() {
        throw new UnsupportedOperationException("This should not be invoked");
    }

    public static Pair<String, String> getFirstLastDate() {
        String startDate = ZonedDateTime.now().minusDays(ZonedDateTime.now().getDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = ZonedDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new Pair(startDate, endDate);
    }

}
