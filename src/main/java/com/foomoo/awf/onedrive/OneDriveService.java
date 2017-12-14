package com.foomoo.awf.onedrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foomoo.awf.config.AppOneDriveConfig;
import com.foomoo.awf.oauth2.AccessTokenRepository;
import com.foomoo.awf.oauth2.PersistableDefaultOAuth2AccessToken;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.Collections;

public class OneDriveService {

    private static final int PERSISTED_TOKEN_ID = 1;
    private volatile OAuth2RestTemplate oAuth2RestTemplate;

    private final AppOneDriveConfig oneDriveConfig;

    private final AccessTokenRepository accessTokenRepository;

    /**
     * Construct the service based on the given configuration.
     * Loads the application's access token from the given {@link AccessTokenRepository}.
     * @param oneDriveConfig
     */
    public OneDriveService(AppOneDriveConfig oneDriveConfig, AccessTokenRepository accessTokenRepository,
                           final OAuth2ProtectedResourceDetails details) {
        this.oneDriveConfig = oneDriveConfig;
        this.accessTokenRepository = accessTokenRepository;

        final PersistableDefaultOAuth2AccessToken accessToken = accessTokenRepository.findOne(PERSISTED_TOKEN_ID);
        this.oAuth2RestTemplate = new OAuth2RestTemplate(details, new DefaultOAuth2ClientContext(accessToken));
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

        final PersistableDefaultOAuth2AccessToken accessToken = new PersistableDefaultOAuth2AccessToken(oAuth2RestTemplate.getAccessToken());
        accessToken.id = PERSISTED_TOKEN_ID;
        final PersistableDefaultOAuth2AccessToken persistedAccessToken = accessTokenRepository.save(accessToken);

        this.oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2RestTemplate.getResource(),
                new DefaultOAuth2ClientContext(persistedAccessToken));

        return driveRoot;
    }

    /**
     * Creates the parent folder under which all files and folders will be created by this service.
     */
    public void ensureFolderExists() {
        final ObjectMapper mapper = new ObjectMapper();
        final String folderName = oneDriveConfig.getFolder();
        final FolderDriveItem folderDriveItem = new FolderDriveItem(folderName);

        final URI driveRootChildrenUri = oneDriveConfig.getBaseUri().resolve("root/children");

        try {
            final ResponseEntity<FolderDriveItem> response = oAuth2RestTemplate.postForEntity(driveRootChildrenUri, folderDriveItem, FolderDriveItem.class);
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
        if (null == oAuth2RestTemplate) {
            throw new IllegalStateException("No access configured for OneDrive");
        }

        final String folderName = oneDriveConfig.getFolder();
        final String uriString = oneDriveConfig.getBaseUri().toString() + "root:/" + folderName + "/" + relativePath + ":/createUploadSession";
        final String encodedUriString = uriString.replaceAll(" ", "%20");
        final URI createUploadSessionUri = URI.create(encodedUriString);

        final ResponseEntity<UploadSession> uploadSessionResponse = oAuth2RestTemplate.postForEntity(createUploadSessionUri, Collections.emptyMap(), UploadSession.class);

        final URI uploadUrl = uploadSessionResponse.getBody().getUploadUrl();

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Range", "bytes 0-" + (content.length - 1) + "/" + content.length);

        final HttpEntity<byte[]> httpRequest = new HttpEntity<byte[]>(content, headers);

        oAuth2RestTemplate.exchange(uploadUrl, HttpMethod.PUT, httpRequest, String.class);
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
