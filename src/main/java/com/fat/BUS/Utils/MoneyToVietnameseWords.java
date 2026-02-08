package com.fat.BUS.Utils;

import java.math.BigDecimal;

/**
 * Chuyển số tiền sang chữ tiếng Việt.
 * VD: 10400000 -> "Mười triệu bốn trăm nghìn đồng"
 */
public class MoneyToVietnameseWords {

    private static final String[] DIGITS = {
        "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"
    };

    /**
     * Chuyển số tiền (BigDecimal) sang chữ tiếng Việt.
     * @param amount Số tiền
     * @return Chuỗi chữ viết bằng tiếng Việt, ví dụ: "Mười triệu bốn trăm nghìn đồng"
     */
    public static String convert(BigDecimal amount) {
        if (amount == null) return "";
        long number = amount.longValue();
        if (number == 0) return "Không đồng";
        if (number < 0) return "Âm " + convert(BigDecimal.valueOf(-number));

        String result = readNumber(number);
        // Capitalize first letter
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result + " đồng";
    }

    private static String readNumber(long number) {
        if (number == 0) return "không";

        StringBuilder sb = new StringBuilder();
        
        // Tỷ (billion)
        if (number >= 1_000_000_000L) {
            long billions = number / 1_000_000_000L;
            sb.append(readGroup(billions)).append(" tỷ");
            number %= 1_000_000_000L;
            if (number > 0) {
                sb.append(" ");
                if (number < 100_000_000L) {
                    sb.append("không trăm ");
                    if (number < 10_000_000L) {
                        // skip
                    }
                }
            }
        }

        // Triệu (million)
        if (number >= 1_000_000L) {
            long millions = number / 1_000_000L;
            sb.append(readGroup(millions)).append(" triệu");
            number %= 1_000_000L;
            if (number > 0) {
                sb.append(" ");
            }
        }

        // Nghìn (thousand)
        if (number >= 1_000L) {
            long thousands = number / 1_000L;
            sb.append(readGroup(thousands)).append(" nghìn");
            number %= 1_000L;
            if (number > 0) {
                sb.append(" ");
            }
        }

        // Đơn vị
        if (number > 0) {
            sb.append(readGroup(number));
        }

        return sb.toString().trim().replaceAll("\\s+", " ");
    }

    /**
     * Đọc một nhóm 3 chữ số (0-999).
     */
    private static String readGroup(long number) {
        if (number == 0) return "";

        StringBuilder sb = new StringBuilder();
        int hundreds = (int) (number / 100);
        int tens = (int) ((number % 100) / 10);
        int units = (int) (number % 10);

        if (hundreds > 0) {
            sb.append(DIGITS[hundreds]).append(" trăm");
            if (tens == 0 && units > 0) {
                sb.append(" lẻ");
            }
        }

        if (tens > 0) {
            if (sb.length() > 0) sb.append(" ");
            if (tens == 1) {
                sb.append("mười");
            } else {
                sb.append(DIGITS[tens]).append(" mươi");
            }
        }

        if (units > 0) {
            if (sb.length() > 0) sb.append(" ");
            if (units == 1 && tens > 1) {
                sb.append("mốt");
            } else if (units == 5 && tens > 0) {
                sb.append("lăm");
            } else if (units == 4 && tens > 1) {
                sb.append("tư");
            } else {
                sb.append(DIGITS[units]);
            }
        }

        return sb.toString();
    }
}
