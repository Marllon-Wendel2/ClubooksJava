package com.example.Clubooks.email.Service;

import com.example.Clubooks.email.dtos.EmailDTOs;
import com.example.Clubooks.email.utils.EmailUtils;
import com.example.Clubooks.user.model.User;
import com.example.Clubooks.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailUtils emailUtils = new EmailUtils();

    @Autowired
    private UserRepository userRepository;

    public String sendEmail(EmailDTOs emailDTOs) {

        Optional<User> user = userRepository.findByEmail(emailDTOs.destinatario());

        if(user.isPresent()) {
        String link = "http://localhost:8080/confirmation/" + user.get().getTokenConfirmation();
        String message = """
                <html>
                          <body>
                              <p>Bem-vindo ao CLUBOOKS!</p>
                              <p>Por favor, clique no link abaixo para confirmar seu e-mail:</p>
                              <p><a href="%s">Confirmar e-mail</a></p>
                              <p>Se você não solicitou este e-mail, ignore esta mensagem.</p>
                              <br>
                              <p>Atenciosamente,</p>
                              <p>Equipe CLUBOOKS</p>
                          </body>
                      </html>
                """.formatted(link);
        String assunto = "E-mail de confirmação";
        return emailUtils.sendEmailText(emailDTOs.destinatario(), assunto, message);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }

    }
}
