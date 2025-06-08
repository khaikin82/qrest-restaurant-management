package com.khaikin.qrest.email;

import com.khaikin.qrest.payloads.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    // Giả sử admin email
    private static final String ADMIN_EMAIL = "nguyenvannamdeptrai2004@gmail.com";

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendEmail(@RequestBody String message) {
        emailService.sendEmail(ADMIN_EMAIL, message);
        return ResponseEntity.ok( new ApiResponse("Báo cáo sự cố đã được gửi thành công!", true));
    }
}
