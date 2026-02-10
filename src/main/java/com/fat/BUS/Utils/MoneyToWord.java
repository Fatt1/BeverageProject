package com.fat.BUS.Utils;
import java.text.DecimalFormat;
public class MoneyToWord {
    private static final String[] CHU_SO = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] DON_VI = {"", "nghìn", "triệu", "tỷ", "nghìn tỷ", "triệu tỷ"};

    /**
     * Hàm public để gọi từ bên ngoài
     * @param soTien Số tiền cần đọc (long)
     * @return Chuỗi chữ đọc số tiền
     */
    public static String doc(long soTien) {
        if (soTien == 0) return "Không đồng";
        if (soTien < 0) return "Âm " + doc(Math.abs(soTien));

        String strSoTien = String.valueOf(soTien);

        // Tạo danh sách các nhóm 3 số (Lớp con của tư duy chia để trị)
        // Ví dụ: 123456 -> [456, 123]
        java.util.List<String> groups = new java.util.ArrayList<>();
        for (int i = strSoTien.length(); i > 0; i -= 3) {
            groups.add(strSoTien.substring(Math.max(0, i - 3), i));
        }

        StringBuilder ketQua = new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            String group = groups.get(i);
            int n = Integer.parseInt(group);

            // Nếu nhóm là 000 thì bỏ qua, trừ khi nó là nhóm duy nhất (0 đồng)
            if (n == 0 && groups.size() > 1) continue;

            // Đọc số của nhóm đó
            String textGroup = doc3So(n, i == groups.size() - 1);

            // Ghép đơn vị (nghìn, triệu, tỷ)
            if (!textGroup.isEmpty()) {
                if (i > 0 && !DON_VI[i].isEmpty()) {
                    ketQua.insert(0, textGroup + " " + DON_VI[i] + " ");
                } else {
                    ketQua.insert(0, textGroup + " ");
                }
            }
        }

        // Chuẩn hóa văn bản (Viết hoa chữ cái đầu, thêm chữ đồng)
        String res = ketQua.toString().trim().replaceAll("\\s+", " ");
        return res.substring(0, 1).toUpperCase() + res.substring(1) + " đồng";
    }

    /**
     * Hàm phụ: Chỉ chuyên đọc nhóm 3 số (Ví dụ: 105, 215)
     */
    private static String doc3So(int n, boolean isFirstGroup) {
        int tram = n / 100;
        int chuc = (n % 100) / 10;
        int donvi = n % 10;

        StringBuilder sb = new StringBuilder();

        // 1. Hàng Trăm
        if (tram > 0 || !isFirstGroup) {
            sb.append(CHU_SO[tram]).append(" trăm ");
        }

        // 2. Hàng Chục
        if (chuc == 0 && donvi > 0 && (tram > 0 || !isFirstGroup)) {
            sb.append("lẻ ");
        } else if (chuc == 1) {
            sb.append("mười ");
        } else if (chuc > 1) {
            sb.append(CHU_SO[chuc]).append(" mươi ");
        }

        // 3. Hàng Đơn vị
        if (donvi > 0) {
            if (chuc > 1 && donvi == 1) sb.append("mốt");
            else if (chuc > 1 && donvi == 4) sb.append("tư");
            else if (chuc > 0 && donvi == 5) sb.append("lăm");
            else sb.append(CHU_SO[donvi]);
        }

        return sb.toString();
    }
}

