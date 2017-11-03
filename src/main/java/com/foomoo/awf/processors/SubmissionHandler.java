package com.foomoo.awf.processors;

import com.foomoo.awf.config.MailConfig;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.render.PdfRenderer;
import com.foomoo.awf.render.XmlReferralRenderer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.StandardCharsets;

/**
 * Class to accept a submission and forward it to its recipients by email.
 */
@Service
public class SubmissionHandler {

    private final JavaMailSender mailSender;

    public SubmissionHandler(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void handleSubmission(final Referral referral) {

        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
        final String referralXml = xmlReferralRenderer.render(referral);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PdfRenderer pdfRenderer = new PdfRenderer();
        pdfRenderer.render(referralXml, baos);

        final MimeMessage message = mailSender.createMimeMessage();

        try {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(MailConfig.FROM);
            messageHelper.setTo(MailConfig.RECIPIENT_ADDRESS);
            messageHelper.setSubject(MailConfig.SUBJECT);
            messageHelper.setText("New Referral: " + referral.getApplicantName(), StandardCharsets.UTF_8.toString());
            messageHelper.addAttachment(String.format("%s Referral.pdf", referral.getApplicantName()),
                    new ByteArrayDataSource(baos.toByteArray(), "application/pdf"));
        } catch (MessagingException e) {
            throw new RuntimeException("There was a problem constructing or sending the referral message.", e);
        }

        mailSender.send(message);
    }

}
