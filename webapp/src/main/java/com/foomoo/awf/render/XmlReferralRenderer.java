package com.foomoo.awf.render;

import com.foomoo.awf.pojo.Referral;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Class to render a {@link com.foomoo.awf.pojo.Referral} as an XML string.
 */
public class XmlReferralRenderer {

    public String render(final Referral referral) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Referral.class);

            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();
            marshaller.marshal(referral, stringWriter);

            return stringWriter.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Cannot configure or use JAXB.", e);
        }
    }
}
