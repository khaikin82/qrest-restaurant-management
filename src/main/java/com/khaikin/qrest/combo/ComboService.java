package com.khaikin.qrest.combo;

import java.util.List;

public interface ComboService {
    List<Combo> getAllCombos();
    Combo getComboById(Long id);
    Combo createCombo(ComboRequest comboRequest);
    Combo updateCombo(Long id, Combo combo);
    void deleteComboById(Long id);
}