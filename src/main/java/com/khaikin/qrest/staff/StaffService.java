package com.khaikin.qrest.staff;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StaffService {
    List<Staff> getAllStaffs();
    Staff getStaffById(Long id);
    Staff createStaff(Staff staff);
    Staff updateStaff(Long id, Staff updatedStaff);

    Staff createStaff(Staff staff, MultipartFile imageFile, HttpServletRequest request)
            throws IOException;

    Staff updateStaff(Long id, Staff staff, MultipartFile updateImageFile, HttpServletRequest request)
            throws IOException;

    void deleteStaff(Long id);
}

