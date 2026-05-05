package com.gindho.service;

import com.gindho.model.RendezVous;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendRappelRDV(RendezVous rdv) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("patientNom", rdv.getPatient().getNom() + " " + rdv.getPatient().getPrenom());
            context.setVariable("medecinNom", rdv.getMedecin().getNom() + " " + rdv.getMedecin().getPrenom());
            context.setVariable("dateRdv", rdv.getDateHeureDebut());
            context.setVariable("motif", rdv.getMotif());

            String htmlContent = templateEngine.process("rappel-email", context);

            helper.setTo(rdv.getPatient().getUser().getEmail());
            helper.setSubject("Rappel de rendez-vous médical");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
