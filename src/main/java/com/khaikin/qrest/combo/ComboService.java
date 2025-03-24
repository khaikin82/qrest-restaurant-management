package com.khaikin.qrest.combo;

import java.util.List;

public interface ComboService {
    List<Combo> getAllCombos();
    Combo getComboById(Integer id);
    Combo createCombo(Combo combo);
    Combo updateCombo(Integer id, Combo combo);
    String deleteCombo(Integer id);
}