package org.springframework.samples.volleymate.jugador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private static final String ACEPTACION_SUBJECT = "Aceptación de Solicitud";
    private static final String DENEGACION_SUBJECT = "Rechazo de Solicitud";
    private static final String GREETING_TEXT = "Buenas, ";
    private static final String SOLICITUD_TEXT_1 = "tu solicitud de centro ";
    private static final String SOLICITUD_TEXT_2 = " ha sido aceptada";
    private static final String SOLICITUD_TEXT_3 = " ha sido denegada";
    private static final String SOLICITUD_TEXT_END = "Un saludo del equipo Volleymate";

    public void aceptarSolicitudEmail(String email, String sender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(ACEPTACION_SUBJECT);
        message.setText(
                GREETING_TEXT + "\n\n" + SOLICITUD_TEXT_1 + sender + SOLICITUD_TEXT_2 + "\n\n" + SOLICITUD_TEXT_END);
        try {
            mailSender.send(message);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void denegarSolicitudEmail(String email, String sender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(DENEGACION_SUBJECT);
        message.setText(GREETING_TEXT + "\n\n" + SOLICITUD_TEXT_1 + SOLICITUD_TEXT_3 + "\n\n" + SOLICITUD_TEXT_END);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
