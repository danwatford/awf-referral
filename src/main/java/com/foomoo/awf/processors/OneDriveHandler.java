package com.foomoo.awf.processors;

import com.foomoo.awf.onedrive.OneDriveService;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.render.PdfRenderer;
import com.foomoo.awf.render.XmlReferralRenderer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class OneDriveHandler {
    @Autowired
    OneDriveService oneDriveService;

    public void handleSubmission(final Referral referral, final Collection<MultipartFile> multipartFiles) {

        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
        final String referralXml = xmlReferralRenderer.render(referral);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PdfRenderer pdfRenderer = new PdfRenderer();
        pdfRenderer.render(referralXml, baos);

        final ZonedDateTime submissionDateTime = referral.getSubmissionDateTime();
        final String formattedSubmissionDateTime = submissionDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final String folderName = referral.getApplicantName() + " - " + formattedSubmissionDateTime;
        final String fileName = referral.getApplicantName() + ".pdf";
        writeFileToFolder(folderName, fileName, baos.toByteArray());

        for (final MultipartFile mf : multipartFiles) {
            try {
                writeFileToFolder(folderName, mf.getOriginalFilename(), mf.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Write any url files.

        final List<URI> links = new ArrayList<>();
        if (StringUtils.isNotBlank(referral.getMusicLink1().getPath())) {
            final URI link = referral.getMusicLink1();
            final String content = "[InternetShortcut]\nURL=" + link + "\n";
            writeFileToFolder(folderName,  "link1.url", content.getBytes(StandardCharsets.UTF_8));
        }
        if (StringUtils.isNotBlank(referral.getMusicLink2().getPath())) {
            final URI link = referral.getMusicLink2();
            final String content = "[InternetShortcut]\nURL=" + link + "\n";
            writeFileToFolder(folderName,  "link2.url", content.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void writeFileToFolder(final String folderName, final String fileName, final byte[] content) {
        oneDriveService.writeFile(stripFilename(folderName) + "/" + stripFilename(fileName), content);
    }

    private String stripFilename(String filename) {
        final String[] invalidSequences = {"~", "\"", "#", "%", "&", "*", ":", "<", ">", "?", "/", "\\", "{", "|", "}"};
        for (String invalidSequence : invalidSequences) {
            filename = StringUtils.remove(filename, invalidSequence);
        }
        return filename;
    }
}
