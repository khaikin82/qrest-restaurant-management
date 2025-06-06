package com.khaikin.qrest.order;

import com.khaikin.qrest.foodorder.FoodOrder;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(OrderRequest orderRequest);
    Order updateOrder(Long id, Order order);
    Order updateOrderStatus(Long id, OrderStatus orderStatus);
    FoodOrder updateFoodOrderStatus(Long foodOrderId, boolean isCompleted);
    void deleteOrderById(Long id);
    List<Order> getCompletedOrders();
    List<Order> getCompletedOrdersWithoutPayment();
}
