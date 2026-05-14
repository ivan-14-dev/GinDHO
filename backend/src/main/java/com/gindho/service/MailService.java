package com.gindho.service;

import com.gindho.model.RendezVous;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendRappelRDV(RendezVous rdv) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            String patientNom = "";
            String medecinNom = "";
            
            if (rdv.getPatient() != null && rdv.getPatient().getUser() != null) {
                patientNom = rdv.getPatient().getUser().getNom() + " " + rdv.getPatient().getUser().getPrenom();
            }
            if (rdv.getMedecin() != null && rdv.getMedecin().getUser() != null) {
                medecinNom = rdv.getMedecin().getUser().getNom() + " " + rdv.getMedecin().getUser().getPrenom();
            }
            
            context.setVariable("patientNom", patientNom);
            context.setVariable("medecinNom", medecinNom);
            context.setVariable("dateRdv", rdv.getDateHeureDebut());
            context.setVariable("motif", rdv.getMotif());

            String htmlContent = templateEngine.process("rappel-email", context);

            if (rdv.getPatient() != null && rdv.getPatient().getUser() != null) {
                helper.setTo(rdv.getPatient().getUser().getEmail());
            }
            helper.setSubject("Rappel de rendez-vous médical");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
