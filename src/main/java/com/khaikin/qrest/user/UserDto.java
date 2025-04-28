package com.khaikin.qrest.user;

import com.khaikin.qrest.staff.Staff;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Staff staff;
    private Role role;
}
