package com.khaikin.qrest.order;

import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.combo.ComboRepository;
import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.comboorder.ComboOrderRepository;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.food.FoodRepository;
import com.khaikin.qrest.foodorder.FoodOrder;
import com.khaikin.qrest.foodorder.FoodOrderRepository;
import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.reservation.ReservationRepository;
import com.khaikin.qrest.restauranttable.RestaurantTable;
import com.khaikin.qrest.restauranttable.RestaurantTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ReservationRepository reservationRepository;
    private final FoodRepository foodRepository;
    private final ComboRepository comboRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final ComboOrderRepository comboOrderRepository;


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
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        RestaurantTable table = restaurantTableRepository.findById(orderRequest.getRestaurantTableId())
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", "id",
                                                                 orderRequest.getRestaurantTableId()));
        Reservation reservation = null;
        if (orderRequest.getReservationId() != null) {
            reservation =  reservationRepository.findById(orderRequest.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id",
                                                                     orderRequest.getReservationId()));
        }
        Order order = new Order();
        order.setRestaurantTable(table);
        order.setReservation(reservation);
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());

        List<FoodOrder> foodOrders = new ArrayList<>();
        List<ComboOrder> comboOrders = new ArrayList<>();
        double totalPrice = 0;
        if (orderRequest.getFoodOrderItems() != null) {
            for (OrderItem foodOrderItem : orderRequest.getFoodOrderItems()) {
                Food food = foodRepository.findById(foodOrderItem.id())
                        .orElseThrow(() -> new ResourceNotFoundException("Food", "foodId", foodOrderItem.id()));
                Integer quantity = foodOrderItem.quantity();
                FoodOrder foodOrder = new FoodOrder();
                foodOrder.setQuantity(quantity);
                foodOrder.setPrice(food.getPrice());
                foodOrder.setFood(food);
                foodOrder.setOrder(order);

                foodOrders.add(foodOrder);
                totalPrice += foodOrder.getPrice() * quantity;
            }
            foodOrderRepository.saveAll(foodOrders);
            order.setFoodOrders(foodOrders);
        }
        if (orderRequest.getComboOrderItems() != null) {
            for (OrderItem comboOrderItem : orderRequest.getComboOrderItems()) {
                Combo combo = comboRepository.findById(comboOrderItem.id())
                        .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", comboOrderItem.id()));
                Integer quantity = comboOrderItem.quantity();
                ComboOrder comboOrder = new ComboOrder();
                comboOrder.setQuantity(quantity);
                comboOrder.setPrice(combo.getPrice());
                comboOrder.setCombo(combo);
                comboOrder.setOrder(order);
                comboOrders.add(comboOrder);

                totalPrice += comboOrder.getPrice() * quantity;
            }
            comboOrderRepository.saveAll(comboOrders);
            order.setComboOrders(comboOrders);
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        existingOrder.setTotalPrice(order.getTotalPrice());
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
