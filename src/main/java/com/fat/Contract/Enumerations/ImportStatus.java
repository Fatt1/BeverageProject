package com.fat.Contract.Enumerations;

public enum ImportStatus {
    PENDING,
    COMPLETED,
    CANCELLED;

    /**
     * Map từ chuỗi tiếng Việt trong DB sang enum constant.
     * @param vietnameseStatus Chuỗi status tiếng Việt từ DB
     * @return ImportStatus enum tương ứng
     * @throws IllegalArgumentException nếu không tìm thấy mapping
     */
    public static ImportStatus fromVietnameseString(String vietnameseStatus) {
        if (vietnameseStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        
        return switch (vietnameseStatus) {
            case "Chờ xử lý", "Chờ xử lí", "Đang xử lý" -> PENDING;
            case "Hoàn thành" -> COMPLETED;
            case "Đã hủy", "Hủy" -> CANCELLED;
            default -> {
                // Fallback: try parsing as enum name directly
                try {
                    yield ImportStatus.valueOf(vietnameseStatus);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown ImportStatus: " + vietnameseStatus);
                }
            }
        };
    }

    /**
     * Chuyển enum sang chuỗi tiếng Việt để lưu vào DB.
     * @return Chuỗi tiếng Việt tương ứng
     */
    public String toVietnameseString() {
        return switch (this) {
            case PENDING -> "Chờ xử lý";
            case COMPLETED -> "Hoàn thành";
            case CANCELLED -> "Đã hủy";
        };
    }
}
