package com.foomoo.awf.processors;

import com.foomoo.awf.onedrive.OneDriveService;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.render.PdfRenderer;
import com.foomoo.awf.render.XmlReferralRenderer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
public class OneDriveHandler {
    @Autowired
    OneDriveService oneDriveService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Publish the {@link Referral} submission to OneDrive.
     *
     * @param referral       The {@link Referral} to publish.
     * @param multipartFiles Any audio files that should be published alongside the main Referral content.
     */
    public void handleSubmission(final Referral referral, final Collection<MultipartFile> multipartFiles) {

        logger.info("handleSubmission with {} file(s)", multipartFiles.size());

        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
        final String referralXml = xmlReferralRenderer.render(referral);
        logger.debug("ReferralXml: " + referralXml);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PdfRenderer pdfRenderer = new PdfRenderer();
        pdfRenderer.render(referralXml, baos);
        logger.info("PDF rendered for referral. PDF size: " + baos.size());

        final ZonedDateTime submissionDateTime = referral.getSubmissionDateTime();
        final String formattedSubmissionDateTime = submissionDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final String folderName = referral.getApplicantName() + " - " + formattedSubmissionDateTime;
        final String fileName = referral.getApplicantName() + ".pdf";
        writeFileToFolder(folderName, fileName, baos.toByteArray());
        logger.info("File written to folder. File: {}. Folder: {}.", fileName, folderName);

        for (final MultipartFile mf : multipartFiles) {
            try {
                writeFileToFolder(folderName, mf.getOriginalFilename(), mf.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        writeLinkToFolder(folderName, "Link1", referral.getMusicLink1());
        writeLinkToFolder(folderName, "Link2", referral.getMusicLink2());
    }

    /**
     * Write file content to OneDrive. The file will be written to the given sub-folder relative to the root folder in
     * use by the {@link OneDriveService} and will use the given file name.
     * <p>
     * The folder and file names will have any illegal characters stripped before use.
     *
     * @param folderName Folder to write the file to.
     * @param fileName   The file name to write to.
     * @param content    The content of the file.
     */
    private void writeFileToFolder(final String folderName, final String fileName, final byte[] content) {
        oneDriveService.writeFile(stripFilename(folderName) + "/" + stripFilename(fileName), content);
    }

    /**
     * Write a link (.url file) to OneDrive based on the given base name and URI. The name of the link shall be
     * constructed from the given base name and the host that the link refers to.
     *
     * @param folderName Folder to write the link to.
     * @param baseName   Base name used to construct a name for the link.
     * @param link       The target of the link. If the link is null or the target of the link is empty, i.e.
     *                   StringUtils.isBlank(link.getPath()) is true, then no link is written.
     */
    private void writeLinkToFolder(final String folderName, final String baseName, final URI link) {
        if (link != null && StringUtils.isNotBlank(link.getPath())) {
            final String fileName = String.format("%s-%s.url", baseName, link.getHost());
            final String content = "[InternetShortcut]\nURL=" + link + "\n";
            writeFileToFolder(folderName, fileName, content.getBytes(StandardCharsets.US_ASCII));
        }
    }

    /**
     * Strip any illegal character sequences from folder and file names intended for use on OneDrive.
     *
     * @param name The name of the file or folder to strip illegal sequences from.
     * @return The name with illegal character sequences removed.
     */
    private String stripFilename(String name) {
        final String[] invalidSequences = {"~", "\"", "#", "%", "&", "*", ":", "<", ">", "?", "/", "\\", "{", "|", "}"};
        for (String invalidSequence : invalidSequences) {
            name = StringUtils.remove(name, invalidSequence);
        }
        return name;
    }
}
