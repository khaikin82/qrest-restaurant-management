package com.khaikin.qrest.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Override
    public void sendEmail(String toEmail, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject("Báo cáo sự cố nhà hàng");
        mail.setText("Xin chào Admin,\n\nNhà hàng hiện đang gặp phải sự cố như sau:\n" + message + "\n\nTrân trọng!");
        mailSender.send(mail);
    }
}
