package com.khaikin.qrest.auth;

import com.khaikin.qrest.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAccountResponse {
    private String username;
    private String password;
    private Role role;
}
