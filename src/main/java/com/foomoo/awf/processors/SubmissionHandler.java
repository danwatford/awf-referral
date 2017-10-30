package com.foomoo.awf.processors;

import com.foomoo.awf.config.MailConfig;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.render.PdfRenderer;
import com.foomoo.awf.render.XmlReferralRenderer;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Named;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Class to accept a submission and forward it to its recipients by email.
 */
//@Named
//@RequestScoped
public class SubmissionHandler {

//    @Resource(mappedName = "java:/mail/awf-mail")
//    private Session session;

//    public void handleSubmission(final Referral referral) throws MessagingException {
//
//        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
//        final String referralXml = xmlReferralRenderer.render(referral);
//
//        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        final PdfRenderer pdfRenderer = new PdfRenderer();
//        pdfRenderer.render(referralXml, baos);
//
//        final MimeBodyPart pdfAttachmentBodyPart = new MimeBodyPart();
//        pdfAttachmentBodyPart.setFileName(String.format("%s Referral.pdf", referral.getApplicantName()));
//        final DataSource pdfDataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
//        pdfAttachmentBodyPart.setDataHandler(new DataHandler(pdfDataSource));
//
//        final MimeBodyPart textBodyPart = new MimeBodyPart();
//        textBodyPart.setText("New Referral: " + referral.getApplicantName(), StandardCharsets.UTF_8.toString());
//
//        final Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(textBodyPart);
//        multipart.addBodyPart(pdfAttachmentBodyPart);
//
//        final MimeMessage message = new MimeMessage(session);
//        message.addFrom(InternetAddress.parse(MailConfig.FROM));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailConfig.RECIPIENT_ADDRESS, false));
//        message.setSubject(MailConfig.SUBJECT);
//        message.setSentDate(new Date());
//        message.setContent(multipart);
//
//        Transport.send(message);
//
//    }

}
