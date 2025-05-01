package com.khaikin.qrest.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Path invoiceStoragePath;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Map<String, Object> requestMap) {
        System.out.println("Raw payment request: " + requestMap);
        
        // Trích xuất dữ liệu từ request
        Long orderId = null;
        PaymentMethod paymentMethod = null;
        
        try {
            // Xử lý orderId
            if (requestMap.containsKey("orderId")) {
                if (requestMap.get("orderId") instanceof Number) {
                    orderId = ((Number) requestMap.get("orderId")).longValue();
                } else if (requestMap.get("orderId") instanceof String) {
                    orderId = Long.parseLong((String) requestMap.get("orderId"));
                }
            }
            
            // Xử lý paymentMethod
            if (requestMap.containsKey("paymentMethod")) {
                String paymentMethodStr = String.valueOf(requestMap.get("paymentMethod"));
                paymentMethod = PaymentMethod.valueOf(paymentMethodStr);
            }
            
            if (orderId == null) {
                throw new IllegalArgumentException("orderId không thể là null");
            }
            
            if (paymentMethod == null) {
                throw new IllegalArgumentException("paymentMethod không thể là null");
            }
            
            PaymentRequest paymentRequest = new PaymentRequest(orderId, paymentMethod);
            System.out.println("Processed payment request: " + paymentRequest);
            return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
        } catch (Exception e) {
            System.err.println("Error processing payment request: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

    // Revenue calculation endpoints
    @GetMapping("/revenue/daily")
    public ResponseEntity<RevenueResponse> getDailyRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateDailyRevenue(date));
    }

    @GetMapping("/revenue/paymentList")
    public ResponseEntity<List<Payment>> getPaymentList(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.getPaymentByDate(date));
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<List<Double>> getMonthlyRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateMonthlyRevenue(date));
    }

    @GetMapping("/revenue/quarterly")
    public ResponseEntity<List<Double>> getQuarterlyRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateQuarterlyRevenue(date));
    }

    @GetMapping("/revenue/yearly")
    public ResponseEntity<List<Double>> getYearlyRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateYearlyRevenue(date));
    }

    @GetMapping("/revenue/currentMonth")
    public ResponseEntity<RevenueResponse> getCurrentMonthRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateCurrentMonthRevenue());
    }

    @GetMapping("/revenue/currentQuarter")
    public ResponseEntity<RevenueResponse> getCurrentQuarterRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateCurrentQuarterRevenue());
    }

    @GetMapping("/revenue/currentYear")
    public ResponseEntity<RevenueResponse> getCurrentYearRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return ResponseEntity.ok(paymentService.calculateCurrentYearRevenue());
    }

    // PDF generation endpoint
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long id) throws Exception {
        byte[] pdfBytes = paymentService.generateInvoicePdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "invoice-" + id + ".pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    // Endpoint để lấy file PDF đã lưu
    @GetMapping("/invoices/{fileName:.+}")
    public ResponseEntity<Resource> getInvoiceFile(@PathVariable String fileName) {
        Path filePath = invoiceStoragePath.resolve(fileName);
        File file = filePath.toFile();
        
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = new FileSystemResource(file);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", fileName);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    // QR Code generation endpoint
    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Long id) {
        byte[] qrCodeBytes = paymentService.generateQrCode(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("filename", "invoice-" + id + "-qrcode.png");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeBytes);
    }
}
