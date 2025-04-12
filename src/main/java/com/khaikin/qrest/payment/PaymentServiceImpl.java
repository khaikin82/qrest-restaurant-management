package com.khaikin.qrest.payment;

import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PdfService pdfService;

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setTotalPrice(order.getTotalPrice());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentTime(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment payment = getPaymentById(id);
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setOrder(order);
        payment.setTotalPrice(order.getTotalPrice());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public RevenueResponse calculateDailyRevenue(LocalDateTime date) {
        Double revenue = paymentRepository.calculateDailyRevenue(date);
        return new RevenueResponse(
            date,
            date,
            revenue != null ? revenue : 0.0,
            "DAILY"
        );
    }

    @Override
    public RevenueResponse calculateWeeklyRevenue(LocalDateTime startDate) {
        LocalDateTime endDate = startDate.plusDays(7);
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startDate, endDate);
        return new RevenueResponse(
            startDate,
            endDate,
            revenue != null ? revenue : 0.0,
            "WEEKLY"
        );
    }

    @Override
    public RevenueResponse calculateMonthlyRevenue(LocalDateTime startDate) {
        LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startDate, endDate);
        return new RevenueResponse(
            startDate,
            endDate,
            revenue != null ? revenue : 0.0,
            "MONTHLY"
        );
    }

    @Override
    public byte[] generateInvoicePdf(Long paymentId) throws Exception {
        Payment payment = getPaymentById(paymentId);
        return pdfService.generateInvoice(payment.getOrder(), payment);
    }
}
