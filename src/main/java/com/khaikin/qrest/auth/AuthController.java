package com.khaikin.qrest.auth;

import com.khaikin.qrest.config.JwtService;
import com.khaikin.qrest.user.Role;
import com.khaikin.qrest.user.User;
import com.khaikin.qrest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Nếu role không được gửi, mặc định là USER
        Role role = request.getRole() != null ? request.getRole() : Role.CHEF ;

        User user = userService.register(request.getUsername(), request.getPassword(), role);
        String token = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        User user = userService.authenticate(request.getUsername(), request.getPassword());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        String username = authentication.getName();
        userService.changePassword(username, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully! " + username);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Logout chỉ đơn giản là client xóa token
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/create-account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        CreateAccountResponse response = userService.createAccount(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
