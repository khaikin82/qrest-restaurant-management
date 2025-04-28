package com.khaikin.qrest.combo;

import com.khaikin.qrest.exception.ConflictException;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/combos")
@RequiredArgsConstructor
@Validated
public class ComboController {
    private final ComboService comboService;

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        return ResponseEntity.ok(comboService.getAllCombos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> getComboById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(comboService.getComboById(id));
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody ComboRequest comboRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comboService.createCombo(comboRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable @Positive Long id, @RequestBody Combo combo) {
        return ResponseEntity.ok(comboService.updateCombo(id, combo));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImageByComboId(@PathVariable Long id) {
        try {
            Combo combo = comboService.getComboById(id);
            String imagePath = combo.getImagePath();
            Path filePath = Paths.get(imagePath);
            Resource resource = new FileSystemResource(filePath);

            // Kiểm tra xem tệp có tồn tại không
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType != null ? contentType : "image/jpeg"))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/with-image")
    public ResponseEntity<Combo> createCombo(@RequestPart ComboRequest comboRequest, @RequestPart MultipartFile imageFile) {
        try {
            Combo newCombo = comboService.createCombo(comboRequest, imageFile);
            return new ResponseEntity<>(newCombo, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ConflictException("Conflict Error: Create Combo with Image");
        }
    }

    @PutMapping("/with-image/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable Long id, @RequestPart Combo combo,
                                           @RequestPart MultipartFile imageFile) {
        try {
            Combo newCombo = comboService.updateCombo(id, combo, imageFile);
            return new ResponseEntity<>(newCombo, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("combo", "comboId", id);
        } catch (Exception e) {
            throw new ConflictException("Conflict Error: Update Combo");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComboById(@PathVariable @Positive Long id) {
        comboService.deleteComboById(id);
        return ResponseEntity.noContent().build();
    }
}