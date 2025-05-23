package com.khaikin.qrest.order;

import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.combo.ComboRepository;
import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.comboorder.ComboOrderRepository;
import com.khaikin.qrest.exception.ConflictException;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.food.FoodRepository;
import com.khaikin.qrest.foodorder.FoodOrder;
import com.khaikin.qrest.foodorder.FoodOrderRepository;
import com.khaikin.qrest.reservation.Reservation;
import com.khaikin.qrest.reservation.ReservationRepository;
import com.khaikin.qrest.table.RestaurantTable;
import com.khaikin.qrest.table.RestaurantTableService;
import com.khaikin.qrest.table.RestaurantTableStatus;
import com.khaikin.qrest.tableorder.TableOrder;
import com.khaikin.qrest.tableorder.TableOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final FoodRepository foodRepository;
    private final ComboRepository comboRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final ComboOrderRepository comboOrderRepository;
    private final TableOrderRepository tableOrderRepository;
    private final RestaurantTableService restaurantTableService;


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
        Order order = new Order();
        Reservation reservation = null;
        List<TableOrder> tableOrders = new ArrayList<>();

        if (orderRequest.getRestaurantTableIds() != null) {
            for (Long restaurantTableId : orderRequest.getRestaurantTableIds()) {
                RestaurantTable restaurantTable = restaurantTableService.updateTableStatus(restaurantTableId,
                                                                                           RestaurantTableStatus.OCCUPIED,
                                                                                           RestaurantTableStatus.OCCUPIED);
                TableOrder tableOrder = new TableOrder();
                tableOrder.setRestaurantTable(restaurantTable);
                tableOrder.setOrder(order);

                tableOrders.add(tableOrder);
            }
            tableOrderRepository.saveAll(tableOrders);
            order.setTableOrders(tableOrders);
        }

        if (orderRequest.getReservationId() != null) {
            reservation =  reservationRepository.findById(orderRequest.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id",
                                                                     orderRequest.getReservationId()));
            if (reservation.getOrder() != null) {
                throw new ConflictException("Reservation (ID: " + reservation.getId() + ") is already linked to an order.");
            }
        }

        
        order.setReservation(reservation);
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());

        List<FoodOrder> foodOrders = new ArrayList<>();
        List<ComboOrder> comboOrders = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);
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
                totalPrice = totalPrice.add(foodOrder.getPrice().multiply(BigDecimal.valueOf(quantity)));
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

                totalPrice = totalPrice.add(comboOrder.getPrice().multiply(BigDecimal.valueOf(quantity)));
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
        existingOrder.setTableOrders(order.getTableOrders());


        if (order.getReservation() != null) {
            Reservation reservation = reservationRepository.findById(order.getReservation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", order.getReservation().getId()));
            existingOrder.setReservation(reservation);
        }

        return orderRepository.save(existingOrder);
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        existingOrder.setOrderStatus(orderStatus);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        for (TableOrder tableOrder : existingOrder.getTableOrders()) {
            restaurantTableService.updateTableStatus(tableOrder.getRestaurantTable().getId(),
                                                     RestaurantTableStatus.AVAILABLE);
        }
        orderRepository.delete(existingOrder);
    }

    @Override
    public List<Order> getCompletedOrders() {
        return orderRepository.findByOrderStatus(OrderStatus.COMPLETED);
    }

    @Override
    public List<Order> getCompletedOrdersWithoutPayment() {
        return orderRepository.findCompletedOrdersWithoutPayment(OrderStatus.COMPLETED);
    }
}
