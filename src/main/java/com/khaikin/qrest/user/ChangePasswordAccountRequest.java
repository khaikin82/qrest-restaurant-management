package com.khaikin.qrest.user;


import lombok.Data;

@Data
public class ChangePasswordAccountRequest {
    private String username;
    private String newPassword;
}
