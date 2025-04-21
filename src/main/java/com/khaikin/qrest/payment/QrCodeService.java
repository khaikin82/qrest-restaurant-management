package com.khaikin.qrest.payment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service 
public class QrCodeService {

    /**
     * Tạo QR code từ dữ liệu hóa đơn
     * @param data Dữ liệu hóa đơn dạng JSON
     * @param width Chiều rộng QR code
     * @param height Chiều cao QR code
     * @return Mảng byte chứa hình ảnh QR code
     */
    public byte[] generateQrCode(String data, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Không thể tạo QR code: " + e.getMessage());
        }
    }
    
    /**
     * Tạo QR code từ dữ liệu hóa đơn với kích thước mặc định
     * @param data Dữ liệu hóa đơn dạng JSON
     * @return Mảng byte chứa hình ảnh QR code
     */
    public byte[] generateQrCode(String data) {
        return generateQrCode(data, 350, 350);
    }
} 