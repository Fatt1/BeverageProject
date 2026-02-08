package com.fat.GUI.Utils;

import javax.swing.text.NumberFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatterUtil {
    private static final NumberFormat VN_CURRENCY_FORMATTER = NumberFormat.getInstance(new Locale("vi", "VN"));
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String toVND(BigDecimal money) {
        if (money == null) return "0 VND";
        return VN_CURRENCY_FORMATTER.format(money) + " VND";
    }

    public static String toVND(Double money) {
        if (money == null) return "0 VND";
        return VN_CURRENCY_FORMATTER.format(money) + " VND";
    }
    
    public static String toDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
