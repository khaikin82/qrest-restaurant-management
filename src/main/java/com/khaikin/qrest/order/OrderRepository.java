package com.khaikin.qrest.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    
    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status AND NOT EXISTS (SELECT p FROM Payment p WHERE p.order.id = o.id)")
    List<Order> findCompletedOrdersWithoutPayment(@Param("status") OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.orderTime BETWEEN :startTime AND :endTime")
    List<Order> findByOrderTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
