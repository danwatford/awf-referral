package com.foomoo.awf;

import org.wildfly.swarm.mail.MailFraction;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Class to allow submission of referrals to the application.
 */
@Named
@RequestScoped
public class ReferralSubmitter {

    @Inject
    Referral referral;

    @Resource(mappedName = "java:/mail/awf-mail")
    private Session session;

    public String submit() throws MessagingException {


        final MimeMessage message = new MimeMessage(session);
        message.addFrom(InternetAddress.parse(MailConfig.FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailConfig.RECIPIENT_ADDRESS, false));
        message.setSubject(MailConfig.SUBJECT);
        message.setSentDate(new Date());
        message.setContent("example message. " + referral.getApplicantName(), "text/html; charset=UTF-8");
        Transport.send(message);

        return "thanks";
    }

}
