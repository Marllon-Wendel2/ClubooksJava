package com.example.Clubooks.email.Controller;

import com.example.Clubooks.email.Service.EmailService;
import com.example.Clubooks.email.dtos.EmailDTOs;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirmation")
@SecurityRequirement(name = "bearer-key")
public class EmailController {

    @Autowired
    private EmailService emailService = new EmailService();

    @PostMapping("/send")
    public ResponseEntity<String> sendEmailConfirmation (@Valid @RequestBody EmailDTOs emailDTOs){
        try {
            emailService.sendEmail(emailDTOs);
            return ResponseEntity.ok("E-mail enviado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
