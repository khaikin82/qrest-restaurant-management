package com.khaikin.qrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig {

    @Bean
    public Path invoiceStoragePath() {
        // Lấy đường dẫn tới thư mục gốc của ứng dụng
        String rootPath = new File("").getAbsolutePath();
        
        // Tạo đường dẫn đến thư mục lưu trữ hóa đơn
        String invoiceDirPath = rootPath + "/invoices";
        
        // Tạo thư mục lưu trữ nếu chưa tồn tại
        Path path = Paths.get(invoiceDirPath).toAbsolutePath().normalize();
        File directory = path.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        System.out.println("Invoice storage path: " + path);
        return path;
    }
    
    @Bean
    public String baseInvoiceUrl() {
        return "/api/v1/payments/invoices";
    }
} 