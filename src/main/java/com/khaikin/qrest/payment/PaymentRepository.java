package com.khaikin.qrest.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.totalPrice) FROM Payment p WHERE DATE(p.paymentTime) = DATE(:date)")
    Double calculateDailyRevenue(@Param("date") LocalDateTime date);

    @Query("SELECT SUM(p.totalPrice) FROM Payment p WHERE p.paymentTime BETWEEN :startDate AND :endDate")
    Double calculateRevenueBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Payment p WHERE DATE(p.paymentTime) = DATE(:date)")
    List<Payment> findPaymentsByDate(@Param("date") LocalDateTime date);
}
