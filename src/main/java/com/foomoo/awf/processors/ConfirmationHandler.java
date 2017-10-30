package com.foomoo.awf.processors;

import com.foomoo.awf.config.MailConfig;

import javax.annotation.Resource;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Class to send confirmation that a referral has been received by the application.
 */
public class ConfirmationHandler {

//    @Resource(mappedName = "java:/mail/awf-mail")
//    private Session session;

    /**
     * Send confirmation that the referral submission has been received to the given email address.
     *
     * @param confirmationAddress The email address to send confirmation to.
     * @throws AddressException if the address the confirmation is to be sent to cannot be parsed.
     */
//    public void sendConfirmation(final String confirmationAddress) throws AddressException {
//
//        final MimeMessage message = new MimeMessage(session);
//        try {
//            message.addFrom(InternetAddress.parse(MailConfig.FROM));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(confirmationAddress, false));
//            message.setSubject(MailConfig.CONFIRMATION_SUBJECT);
//            message.setSentDate(new Date());
//
//            String content = MailConfig.CONFIRMATION_BODY_TEMPLATE;
//            message.setContent(content, "text/html; charset=UTF-8");
//
//            Transport.send(message);
//        } catch (final MessagingException e) {
//            throw new RuntimeException("There was a problem constructing or sending the confirmation message.", e);
//        }
//
//    }
}
