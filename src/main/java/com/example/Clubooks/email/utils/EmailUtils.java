package com.example.Clubooks.email.utils;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

//    @Value("${spring.mail.username}")
    private String remetente = "marllonestudo9@gmail.com";

    public String sendEmailText(String destinatario, String assunto, String message) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(message, true);

            javaMailSender.send(mimeMessage);
            return "E-mail enviado com sucesso";
        } catch (Exception e) {
            return  "Erro ao tentar enviar o e-mail" + e.getLocalizedMessage();
        }
    }
}

