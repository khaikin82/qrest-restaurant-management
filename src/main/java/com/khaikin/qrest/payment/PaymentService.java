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
    Double calculateWeeklyRevenue(LocalDateTime date);
    public Double calculateMonthRevenue(LocalDateTime date);
    List<Double> calculateMonthlyRevenue(LocalDateTime startDate);
    List<Double> calculateQuarterlyRevenue(LocalDateTime startDate);
    List<Double> calculateYearlyRevenue(LocalDateTime startDate);
    RevenueResponse calculateCurrentMonthRevenue();
    RevenueResponse calculateCurrentQuarterRevenue();
    RevenueResponse calculateCurrentYearRevenue();
    public List<Payment> getPaymentByDate(LocalDateTime date);
    // PDF generation
    byte[] generateInvoicePdf(Long paymentId) throws Exception;
    byte[] generateQrCode(Long paymentId);
}
