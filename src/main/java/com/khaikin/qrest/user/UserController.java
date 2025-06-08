package com.khaikin.qrest.user;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}/staff/{staffId}")
    public ResponseEntity<UserDto> updateUserStaff(@PathVariable @Positive Long id, @PathVariable @Positive Long staffId) {
        return ResponseEntity.ok(userService.updateUserStaff(id, staffId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable @Positive Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/change-password-account")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordAccountRequest request) {
        String username = request.getUsername();
        userService.changePasswordAccount(username, request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully! " + username);
    }

}
