package com.khaikin.qrest.staff;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "staffId", id));
    }

    @Override
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, Staff updatedStaff) {
        Staff existing = getStaffById(id);
        existing.setFullName(updatedStaff.getFullName());
        existing.setDob(updatedStaff.getDob());
        existing.setPhoneNumber(updatedStaff.getPhoneNumber());
        existing.setAddress(updatedStaff.getAddress());
        existing.setSalary(updatedStaff.getSalary());
        existing.setPosition(updatedStaff.getPosition());
        return staffRepository.save(existing);
    }

    @Override
    public void deleteStaff(Long id) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "staffId", id));
        staffRepository.delete(existingStaff);
    }
}