package com.khaikin.qrest.comboorder;

import java.util.List;

public interface ComboOrderService {
    List<ComboOrder> getAllComboOrders();
    ComboOrder getComboOrderById(Long id);
    ComboOrder createComboOrder(ComboOrder comboOrder);
    ComboOrder updateComboOrder(Long id, ComboOrder comboOrder);
    void deleteComboOrderById(Long id);
}
