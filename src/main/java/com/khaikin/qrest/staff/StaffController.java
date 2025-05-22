package com.khaikin.qrest.staff;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/staffs")
@RequiredArgsConstructor
@Validated
public class StaffController {
    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaffs() {
        return ResponseEntity.ok(staffService.getAllStaffs());
    }

    @GetMapping("/getStaffSalaries")
    public ResponseEntity<List<StaffDTO>> getStaffSalaries() {
        List<Staff> staffList = staffService.getAllStaffs();
        List<StaffDTO> staffDTOs = staffList.stream().map(staff -> new StaffDTO(staff.getId(), staff.getFullName(), staff.getSalary(), staff.getPosition())).collect(Collectors.toList());
        return ResponseEntity.ok(staffDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable @Positive Long id, @RequestBody Staff staff) {
        return ResponseEntity.ok(staffService.updateStaff(id, staff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable @Positive Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
