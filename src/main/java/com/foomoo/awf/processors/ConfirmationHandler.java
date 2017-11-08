package com.foomoo.awf.processors;

import com.foomoo.awf.config.MailConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Class to send confirmation that a referral has been received by the application.
 */
@Service
public class ConfirmationHandler {

    private TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    public ConfirmationHandler(final TemplateEngine templateEngine, final JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    /**
     * Send confirmation that the referral submission has been received to the given email address.
     *
     * @param confirmationAddress The email address to send confirmation to.
     */
    public void sendConfirmation(final String confirmationAddress) {

        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setFrom(MailConfig.FROM);
            messageHelper.setTo(confirmationAddress);
            messageHelper.setSubject(MailConfig.CONFIRMATION_SUBJECT);
            final String content = templateEngine.process("confirmation-template", new Context());
            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException("There was a problem constructing or sending the confirmation message.", e);
        }

        mailSender.send(message);
    }
}
