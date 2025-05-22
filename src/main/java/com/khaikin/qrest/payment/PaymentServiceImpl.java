package com.khaikin.qrest.payment;

import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.order.OrderRepository;
import com.khaikin.qrest.tableorder.TableOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private QrCodeService qrCodeService;

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setTotalPrice(order.getTotalPrice());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentTime(LocalDateTime.now());
        
        // Lưu thanh toán trước để có ID
        payment = paymentRepository.save(payment);
        
        try {
            // Tạo và lưu file PDF, lấy đường dẫn
            String pdfPath = pdfService.generateAndSaveInvoice(order, payment);
            payment.setInvoicePdfPath(pdfPath);
            // Cập nhật lại thanh toán với đường dẫn PDF
            payment = paymentRepository.save(payment);
        } catch (Exception e) {
            // Log lỗi nhưng vẫn tiếp tục
            System.err.println("Không thể tạo file PDF: " + e.getMessage());
        }

        return payment;
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

    // tinh tu dau thang den cuoi thang duoc, phuc vu cho viec tao bieu do so sanh
    @Override
    public Double calculateWeeklyRevenue(LocalDateTime date) {
        // Lấy thứ hai đầu tuần (ngày 1 của tuần)
        LocalDateTime startOfWeek = date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0);
        // Lấy chủ nhật cuối tuần (ngày 7 của tuần)
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
        
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startOfWeek, endOfWeek);
        return revenue;
    }

    @Override
    public Double calculateMonthRevenue(LocalDateTime date) {
        // Lấy ngày 1 của tháng
        LocalDateTime startOfMonth = date.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // Lấy thời điểm hiện tại
        LocalDateTime endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startOfMonth, endOfMonth);
        return revenue;
    }

    @Override
    public List<Double> calculateMonthlyRevenue(LocalDateTime date) {
        // Get first day of the month
        LocalDateTime startOfMonth = date.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        // Get last day of the month
        LocalDateTime endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        List<Double> monthlyRevenue = new ArrayList<>();
        // Iterate through each day of the month
        for (LocalDateTime currentDate = startOfMonth; 
             !currentDate.isAfter(endOfMonth); 
             currentDate = currentDate.plusDays(1)) {
            Double revenue = paymentRepository.calculateDailyRevenue(currentDate);
            // Handle null case (if no revenue for that day)
            monthlyRevenue.add(revenue != null ? revenue : 0.0);
        }
        
        return monthlyRevenue;
    }

    @Override
    public List<Double> calculateQuarterlyRevenue(LocalDateTime date) {
        // Determine the quarter and set start/end dates
        int month = date.getMonthValue();
        int quarter = (month - 1) / 3 + 1;
        LocalDateTime startOfQuarter = date.withMonth((quarter - 1) * 3 + 1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        LocalDateTime endOfQuarter = startOfQuarter.plusMonths(3)
                .minusDays(1)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    
        List<Double> weeklyRevenues = new ArrayList<>();
        
        // Start from the first Monday of the quarter
        LocalDateTime currentWeekStart = startOfQuarter
                .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        
        // Iterate through weeks until reaching or passing end of quarter
        while (!currentWeekStart.isAfter(endOfQuarter)) {
            // Calculate revenue for the week
            Double weeklyRevenue = calculateWeeklyRevenue(currentWeekStart);
            weeklyRevenues.add(weeklyRevenue != null ? weeklyRevenue : 0.0);
            
            // Move to next Monday
            currentWeekStart = currentWeekStart.plusWeeks(1);
        }
        
        return weeklyRevenues;
    }

    @Override
    public List<Double> calculateYearlyRevenue(LocalDateTime date) {
        // Set start of year
        LocalDateTime startOfYear = date.withMonth(1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        
        List<Double> monthlyRevenues = new ArrayList<>();
        
        // Iterate through each month of the year
        LocalDateTime currentMonth = startOfYear;
        for (int i = 0; i < 12; i++) {
            // Calculate revenue for the current month
            Double monthlyRevenue = calculateMonthRevenue(currentMonth);
            monthlyRevenues.add(monthlyRevenue != null ? monthlyRevenue : 0.0);
            
            // Move to the first day of the next month
            currentMonth = currentMonth.plusMonths(1);
        }
        
        return monthlyRevenues;
    }

    //tinh tu dau thang den ngay hien tai thoi
    @Override
    public RevenueResponse calculateCurrentMonthRevenue() {
        // Lấy thời điểm hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Lấy ngày 1 của tháng
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startOfMonth, now);
        return new RevenueResponse(
            startOfMonth,
            now,
            revenue != null ? revenue : 0.0,
            "MONTHLY"
        );
    }
    
    @Override
    public RevenueResponse calculateCurrentQuarterRevenue() {
        // Lấy thời điểm hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Xác định quý
        int month = now.getMonthValue();
        int quarter = (month - 1) / 3 + 1;
        // Lấy ngày đầu tiên của quý
        LocalDateTime startOfQuarter = now.withMonth((quarter - 1) * 3 + 1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startOfQuarter, now);
        return new RevenueResponse(
            startOfQuarter,
            now,
            revenue != null ? revenue : 0.0,
            "QUARTERLY"
        );
    }
    
    @Override
    public RevenueResponse calculateCurrentYearRevenue() {
        // Lấy thời điểm hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Lấy ngày 1 tháng 1 của năm
        LocalDateTime startOfYear = now.withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        Double revenue = paymentRepository.calculateRevenueBetweenDates(startOfYear, now);
        return new RevenueResponse(
            startOfYear,
            now,
            revenue != null ? revenue : 0.0,
            "YEARLY"
        );
    }

    @Override
    public byte[] generateInvoicePdf(Long paymentId) throws Exception {
        Payment payment = getPaymentById(paymentId);
        return pdfService.generateInvoice(payment.getOrder(), payment);
    }

    @Override
    public byte[] generateQrCode(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        String serverUrl;
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            int port = 8080; // tự set port nếu biết
            serverUrl = "http://" + ip + ":" + port;
        } catch (UnknownHostException e) {
            serverUrl = "http://localhost:8080";
            throw new RuntimeException("Cannot determine server IP address", e);
        }
        // Tạo URL đầy đủ cho QR code, dẫn đến file PDF hóa đơn
        
        String appDir = System.getProperty("user.dir");
        System.out.println(appDir);
        String fullUrl = serverUrl + payment.getInvoicePdfPath();
        
        // Nếu không có đường dẫn PDF, tạo JSON với thông tin thanh toán
        if (payment.getInvoicePdfPath() == null || payment.getInvoicePdfPath().isEmpty()) {
            Order order = payment.getOrder();
            List<TableOrder> tableOrders = order.getTableOrders();
            List<String> tableName = new ArrayList<>();
            for (TableOrder it : tableOrders) {
                tableName.add(it.getRestaurantTable().getName());
            }
            fullUrl = String.format(
                "{\"invoice_id\":%d,\"order_id\":%d,\"total_price\":%.2f,\"payment_method\":\"%s\",\"payment_time\":\"%s\",\"table\":\"%s\"}",
                paymentId,
                order.getId(),
                payment.getTotalPrice(),
                payment.getPaymentMethod(),
                payment.getPaymentTime(),
                tableName
            );
        }
        
        // Tạo QR code từ đường dẫn hoặc dữ liệu
        return qrCodeService.generateQrCode(fullUrl);
    }

    @Override
    public List<Payment> getPaymentByDate(LocalDateTime date) {
        return paymentRepository.findPaymentsByDate(date);
    }
}
