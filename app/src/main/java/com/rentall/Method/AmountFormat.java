package com.rentall.Method;

import java.text.NumberFormat;
import java.util.Locale;

public class AmountFormat {

    public static String formatAmount(double amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(amount);
    }
}
