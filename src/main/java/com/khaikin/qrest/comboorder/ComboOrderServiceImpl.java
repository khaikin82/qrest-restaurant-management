package com.khaikin.qrest.comboorder;

import com.khaikin.qrest.combo.ComboRepository;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboOrderServiceImpl implements ComboOrderService {
    private final ComboOrderRepository comboOrderRepository;
    private final ComboRepository comboRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<ComboOrder> getAllComboOrders() {
        return comboOrderRepository.findAll();
    }

    @Override
    public ComboOrder getComboOrderById(Long id) {
        return comboOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComboOrder", "id", id));
    }

    @Override
    public ComboOrder createComboOrder(ComboOrder comboOrder) {
        comboRepository.findById(comboOrder.getCombo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "id", comboOrder.getCombo().getId()));
        orderRepository.findById(comboOrder.getOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", comboOrder.getOrder().getId()));
        return comboOrderRepository.save(comboOrder);
    }

    @Override
    public ComboOrder updateComboOrder(Long id, ComboOrder comboOrder) {
        ComboOrder existingComboOrder = comboOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComboOrder", "id", id));

        existingComboOrder.setQuantity(comboOrder.getQuantity());
        existingComboOrder.setPrice(comboOrder.getPrice());
        existingComboOrder.setCombo(comboRepository.findById(comboOrder.getCombo().getId())
                                            .orElseThrow(() -> new ResourceNotFoundException("Combo", "id", comboOrder.getCombo().getId())));
        existingComboOrder.setOrder(orderRepository.findById(comboOrder.getOrder().getId())
                                            .orElseThrow(() -> new ResourceNotFoundException("Order", "id", comboOrder.getOrder().getId())));

        return comboOrderRepository.save(existingComboOrder);
    }

    @Override
    public void deleteComboOrderById(Long id) {
        ComboOrder existingComboOrder = comboOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ComboOrder", "id", id));
        comboOrderRepository.delete(existingComboOrder);
    }
}