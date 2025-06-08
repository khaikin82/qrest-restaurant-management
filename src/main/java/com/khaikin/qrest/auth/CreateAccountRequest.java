package com.khaikin.qrest.auth;
import com.khaikin.qrest.user.Role;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String password;
    private Role role;
}
