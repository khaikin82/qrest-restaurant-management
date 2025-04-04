package com.khaikin.qrest.order;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.reservation.ReservationRepository;
import com.khaikin.qrest.restauranttable.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getRestaurantTable() != null) {
            restaurantTableRepository.findById(order.getRestaurantTable().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", "id", order.getRestaurantTable().getId()));
        }

        if (order.getReservation() != null) {
            reservationRepository.findById(order.getReservation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", order.getReservation().getId()));
        }

        order.setOrderTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        existingOrder.setPrice(order.getPrice());
        existingOrder.setNote(order.getNote());
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setOrderTime(order.getOrderTime());

        if (order.getRestaurantTable() != null) {
            existingOrder.setRestaurantTable(restaurantTableRepository.findById(order.getRestaurantTable().getId())
                                                     .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", "id", order.getRestaurantTable().getId())));
        }
        if (order.getReservation() != null) {
            existingOrder.setReservation(reservationRepository.findById(order.getReservation().getId())
                                                 .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", order.getReservation().getId())));
        }

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(existingOrder);
    }
}
