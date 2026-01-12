package com.fat.GUI.Utils;

import com.fat.Contract.Exceptions.BadRequestException;
import com.fat.Contract.Exceptions.NotFoundException;
import com.fat.Contract.Exceptions.ValidationException;

import javax.swing.*;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        switch (e) {
            case NotFoundException notFoundException ->
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Không tìm thấy", JOptionPane.WARNING_MESSAGE);
            case BadRequestException badRequestException ->
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Yêu cầu không hợp lệ", JOptionPane.WARNING_MESSAGE);
            case ValidationException validationException ->
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi xác thực", JOptionPane.WARNING_MESSAGE);
            default ->
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi không xác định: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
