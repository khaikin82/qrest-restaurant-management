package com.khaikin.qrest.user;

import com.khaikin.qrest.auth.CreateAccountResponse;

import java.util.List;

public interface UserService {
    User register(String username, String password, Role role);
    User authenticate(String username, String rawPassword);
    void changePassword(String username, String oldPassword, String newPassword);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    UserDto updateUserStaff(Long id, Long staffId);
    UserDto updateUserStaff(String username, Long staffId);
    void deleteUserById(Long userId);

    public CreateAccountResponse createAccount(Role role);
}
