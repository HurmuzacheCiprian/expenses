package com.api.expenses.util;

import com.api.expenses.service.util.Pair;

import java.time.Month;
import java.time.YearMonth;

public final class MonthUtil {

    public static Pair<String, String> getFirstEndDayOfMonth(Month month) {
        String startDate = month.adjustInto(YearMonth.now()) + "-01";
        String endDate = month.adjustInto(YearMonth.now()) + "-" + month.maxLength();
        return new Pair<>(startDate, endDate);
    }

}
