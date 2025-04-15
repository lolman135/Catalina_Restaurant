package com.coursework.app.catalinarestaurant.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    public static double round(double number, int n) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
