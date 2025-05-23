package com.khaikin.qrest.combo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComboService {
    List<Combo> getAllCombos();
    Combo getComboById(Long id);
    Combo createCombo(ComboRequest comboRequest);
    Combo updateCombo(Long id, Combo combo);

    public Combo createCombo(ComboRequest comboRequest, MultipartFile imageFile, HttpServletRequest request)
            throws IOException;

    public Combo updateCombo(Long id, Combo combo, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException;
    
    void deleteComboById(Long id);
}