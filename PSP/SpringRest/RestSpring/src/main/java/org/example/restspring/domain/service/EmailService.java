package org.example.restspring.domain.service;


import jakarta.mail.internet.MimeMessage;
import org.example.restspring.domain.errores.BadRequestException;
import org.example.restspring.ui.config.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void enviarMail(String destinatario, String usuario, String codigoActivacion) {

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, Constantes.ENCODING);

            helper.setTo(destinatario);
            helper.setSubject("Activación de cuenta");

            Context context = new Context();
            context.setVariable("usuario", usuario);
            context.setVariable("codigo", codigoActivacion);
            context.setVariable("enlaceActivacion", "http://localhost:8080/rest/usuarios/activar?codigo=" + codigoActivacion);

            String html = templateEngine.process("email", context);
            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e){
            log.error("Error al enviar correo a {}", destinatario, e);
            throw new BadRequestException("Error al enviar el correo de activación.");
        }
    }
}
