package com.khaikin.qrest.order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(OrderRequest orderRequest);
    Order updateOrder(Long id, Order order);
    void deleteOrderById(Long id);
    List<Order> getCompletedOrders();
    List<Order> getCompletedOrdersWithoutPayment();
}
