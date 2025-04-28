package com.khaikin.qrest.payment;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    Payment createPayment(PaymentRequest paymentRequest);
    Payment getPaymentById(Long id);
    List<Payment> getAllPayments();
    Payment updatePayment(Long id, PaymentRequest paymentRequest);
    void deletePayment(Long id);
    
    // Revenue calculation methods
    RevenueResponse calculateDailyRevenue(LocalDateTime date);
    RevenueResponse calculateWeeklyRevenue(LocalDateTime startDate);
    RevenueResponse calculateMonthlyRevenue(LocalDateTime startDate);
    RevenueResponse calculateQuarterlyRevenue(LocalDateTime startDate);
    RevenueResponse calculateYearlyRevenue(LocalDateTime startDate);
    
    // PDF generation
    byte[] generateInvoicePdf(Long paymentId) throws Exception;
    byte[] generateQrCode(Long paymentId);
}
