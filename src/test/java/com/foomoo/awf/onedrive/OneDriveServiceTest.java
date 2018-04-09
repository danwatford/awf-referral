package com.foomoo.awf.onedrive;

import com.foomoo.awf.config.AppOneDriveConfig;
import com.foomoo.awf.oauth2.OAuth2RestTemplateFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.net.URI;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest
@AutoConfigureWebClient(registerRestTemplate = true)
public class OneDriveServiceTest {

    @MockBean
    private AppOneDriveConfig appOneDriveConfig;

    @MockBean
    private OAuth2RestTemplateFactory oAuth2RestTemplateFactory;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private OneDriveService oneDriveService;

    @Test
    public void writesFileWithSquareBrackets() {
        final String baseUrl = "https://graph.microsoft.com/v1.0/me/drive/";
        final String folder = "folder";
        final String uploadUrl = "http://aaa/bbb";
        when(appOneDriveConfig.getFolder()).thenReturn(folder);
        when(appOneDriveConfig.getBaseUri()).thenReturn(URI.create(baseUrl));

        server.expect(requestTo(startsWith(baseUrl)))
                .andExpect(requestTo(not(containsString("["))))
                .andRespond(withSuccess(String.format("{\"uploadUrl\": \"%s\"}", uploadUrl), MediaType.APPLICATION_JSON_UTF8));
        server.expect(requestTo(uploadUrl))
                .andRespond(withSuccess());
        oneDriveService.writeFile("square[", new byte[0]);
    }

    @Configuration
    @Import(OneDriveService.class)
    static class Config {
    }
}