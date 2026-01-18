package com.fat.GUI.Utils;

import javax.swing.text.NumberFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatterUtil {
    private  static final NumberFormat VN_CURRENCY_FORMATTER = NumberFormat.getInstance(new Locale("vi", "VN"));

    public static String toVND(BigDecimal money) {
        if (money == null) return "0 VND";
        return VN_CURRENCY_FORMATTER.format(money) + " VND";
    }

    public static String toVND(Double money) {
        if (money == null) return "0 VND";
        return VN_CURRENCY_FORMATTER.format(money) + " VND";
    }
}
