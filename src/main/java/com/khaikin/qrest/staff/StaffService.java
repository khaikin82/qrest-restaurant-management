package com.khaikin.qrest.staff;

import java.util.List;

public interface StaffService {
    List<Staff> getAllStaffs();
    Staff getStaffById(Long id);
    Staff createStaff(Staff staff);
    Staff updateStaff(Long id, Staff updatedStaff);
    void deleteStaff(Long id);
}

