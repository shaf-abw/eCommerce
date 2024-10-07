package com.abw.ecommerce.NotificationService.service;

import com.abw.ecommerce.NotificationService.dto.EmailDetails;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<String> sendSimpleMail(EmailDetails details);
    Mono<String> sendMailWithAttachment(EmailDetails details);
    Mono<String> sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}
