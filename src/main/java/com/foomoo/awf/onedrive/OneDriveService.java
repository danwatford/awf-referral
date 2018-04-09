package com.foomoo.awf.onedrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foomoo.awf.config.AppOneDriveConfig;
import com.foomoo.awf.oauth2.AccessTokenRepository;
import com.foomoo.awf.oauth2.OAuth2RestTemplateFactory;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

public class OneDriveService {

    private volatile RestTemplate restTemplate;

    private final OAuth2RestTemplateFactory oAuth2RestTemplateFactory;

    private final AppOneDriveConfig oneDriveConfig;

    /**
     * Construct the service based on the given configuration.
     * Loads the application's access token from the given {@link AccessTokenRepository}.
     *
     * @param oneDriveConfig            The configuration for interacting with OneDrive.
     * @param restTemplate              The initial RestTemplate to use for communicating the OneDrive.
     * @param oAuth2RestTemplateFactory The RestTemplate factory to use to create a replacement RestTemplate based
     *                                  on an existing OAuth2RestTemplate with a valid access token.
     */
    public OneDriveService(final AppOneDriveConfig oneDriveConfig,
                           final RestTemplate restTemplate,
                           final OAuth2RestTemplateFactory oAuth2RestTemplateFactory) {
        this.oneDriveConfig = oneDriveConfig;
        this.oAuth2RestTemplateFactory = oAuth2RestTemplateFactory;
        this.restTemplate = restTemplate;
    }

    /**
     * Prove the connection to OneDrive works with the given RestTemplate. The connection is exercised by
     * retrieving the root information for the user's OneDrive. If the user has not granted appropriate permissions
     * then an exception will be thrown.
     * <p>
     * If the connection is successful the access token will be saved for use by the web app when writing files to
     * OneDrive.
     *
     * @param oAuth2RestTemplate The RestTemplate to use when testing connections to OneDrive.
     * @return The JSON string read from OneDrive.
     */
    public String testConnection(final OAuth2RestTemplate oAuth2RestTemplate) {

        final String driveRoot =
                oAuth2RestTemplate.getForObject(oneDriveConfig.getBaseUri().resolve("root"), String.class);

        this.restTemplate = oAuth2RestTemplateFactory.createOAuth2RestTemplateFromTemplate(oAuth2RestTemplate);

        return driveRoot;
    }

    /**
     * Creates the parent folder under which all files and folders will be created by this service.
     */
    public void ensureAppRootFolderExists() {
        final String folderName = oneDriveConfig.getFolder();
        final FolderDriveItem folderDriveItem = new FolderDriveItem(folderName);

        final URI driveRootChildrenUri = oneDriveConfig.getBaseUri().resolve("root/children");

        try {
            final ResponseEntity<FolderDriveItem> response = restTemplate.postForEntity(driveRootChildrenUri, folderDriveItem, FolderDriveItem.class);
        } catch (HttpClientErrorException clientError) {
            if (HttpStatus.CONFLICT == clientError.getStatusCode()) {
                System.out.println("Folder already exists. Looking up DriveItem.");
            } else {
                throw clientError;
            }
        }
    }

    /**
     * Write the given file content into OneDrive at a path relative the folder specified in the OneDrive configuration.
     *
     * @param relativePath The path to the file to write to in OneDrive.
     * @param content      The content of the file.
     */
    public void writeFile(final String relativePath, byte[] content) {
        if (null == restTemplate) {
            throw new IllegalStateException("No access configured for OneDrive");
        }

        final String folderName = oneDriveConfig.getFolder();
        final String uriString = oneDriveConfig.getBaseUri().toString() + "root:/" + folderName + "/" + relativePath + ":/createUploadSession";
        final String encodedUriString = uriString.replaceAll(" ", "%20");
        final URI createUploadSessionUri = URI.create(encodedUriString);

        final ResponseEntity<UploadSession> uploadSessionResponse = restTemplate.postForEntity(createUploadSessionUri, Collections.emptyMap(), UploadSession.class);

        final URI uploadUrl = uploadSessionResponse.getBody().getUploadUrl();

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Range", "bytes 0-" + (content.length - 1) + "/" + content.length);

        final HttpEntity<byte[]> httpRequest = new HttpEntity<>(content, headers);

        restTemplate.exchange(uploadUrl, HttpMethod.PUT, httpRequest, String.class);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class UploadSession {
        @JsonProperty
        private URI uploadUrl;

        public void setUploadUrl(URI uploadUrl) {
            this.uploadUrl = uploadUrl;
        }

        public URI getUploadUrl() {
            return uploadUrl;
        }
    }
}
