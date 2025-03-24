package com.khaikin.qrest.combo;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;

    @Override
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    @Override
    public Combo getComboById(Integer id) {
        return comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo", "comboId", String.valueOf(id)));
    }

    @Override
    public Combo createCombo(Combo combo) {
        return comboRepository.save(combo);
    }

    @Override
    public Combo updateCombo(Integer id, Combo combo) {
        Combo existingCombo = getComboById(id);
        existingCombo.setName(combo.getName());
        existingCombo.setDescription(combo.getDescription());
        existingCombo.setPrice(combo.getPrice());
        return comboRepository.save(existingCombo);
    }

    @Override
    public String deleteCombo(Integer id) {
        Combo combo = getComboById(id);
        comboRepository.deleteById(id);
        return "Combo with comboId: " + id + " deleted successfully !!!";
    }
}