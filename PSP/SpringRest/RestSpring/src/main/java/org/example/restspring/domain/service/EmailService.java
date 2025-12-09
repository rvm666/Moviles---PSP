package org.example.restspring.domain.service;


import jakarta.mail.internet.MimeMessage;
import org.example.restspring.domain.errores.BadRequestException;
import org.example.restspring.ui.config.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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


    @Value(Constantes.BASE_URL)
    private String baseUrl;

    @Value(Constantes.ACTIVAR_URL)
    private String pathActivacion;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void enviarMail(String destinatario, String usuario, String codigoActivacion) {

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, Constantes.ENCODING);

            helper.setTo(destinatario);
            helper.setSubject(Constantes.ACTIVACION_CUENTA);

            Context context = new Context();
            context.setVariable(Constantes.USUARIO, usuario);
            context.setVariable(Constantes.CODIGO, codigoActivacion);
            context.setVariable(Constantes.ENLACE, baseUrl + pathActivacion + Constantes.INTERROGACION_CODIGO + codigoActivacion);

            String html = templateEngine.process(Constantes.EMAIL, context);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e){
            log.error(Constantes.ERROR_AL_ENVIAR_CORREO1, destinatario, e);
            throw new BadRequestException(Constantes.ERROR_AL_ENVIAR_CORREO2);
        }
    }


}
