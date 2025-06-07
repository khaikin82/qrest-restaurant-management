package com.khaikin.qrest.staff;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.user.User;
import com.khaikin.qrest.user.UserRepository;
import com.khaikin.qrest.util.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final String uploadDir = "images/staff";

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
        existing.setImageUrl(updatedStaff.getImageUrl());
        return staffRepository.save(existing);
    }

    @Override
    public Staff createStaff(Staff staff, MultipartFile imageFile, HttpServletRequest request)
            throws IOException {
        String imageUrl = fileStorageService.storeFile(imageFile, uploadDir, request);
        staff.setImageUrl(imageUrl);
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException {
        Staff existing = getStaffById(id);
        existing.setFullName(staff.getFullName());
        existing.setDob(staff.getDob());
        existing.setPhoneNumber(staff.getPhoneNumber());
        existing.setAddress(staff.getAddress());
        existing.setSalary(staff.getSalary());
        existing.setPosition(staff.getPosition());
        existing.setImageUrl(staff.getImageUrl());

        String imageUrl = fileStorageService.storeFile(updateImageFile, uploadDir, request);
        existing.setImageUrl(imageUrl);

        return staffRepository.save(existing);
    }

    @Transactional
    @Override
    public void deleteStaff(Long id) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "staffId", id));

        // Kiểm tra và hủy liên kết với User (nếu có)
        User user = existingStaff.getUser();
        if (user != null) {
            user.setStaff(null);
            existingStaff.setUser(null);
            userRepository.save(user); // Lưu user để cập nhật liên kết
        }

        staffRepository.delete(existingStaff);
    }
}