package com.foomoo.awf;

import com.foomoo.awf.config.AppAdminConfig;
import com.foomoo.awf.config.AppOneDriveConfig;
import com.foomoo.awf.oauth2.AccessTokenRepository;
import com.foomoo.awf.onedrive.OneDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableOAuth2Sso
public class ApplicationWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

    @Autowired
    OAuth2ClientContextFilter oAuth2ClientContextFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/").permitAll();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(final OAuth2ClientContextFilter filter) {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setOrder(-100);
        return  filterRegistrationBean;
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oAuth2ClientContext);
    }

    @Bean
    public UserInfoTokenServices userInfoTokenServices(ResourceServerProperties resourceServerProperties, AppAdminConfig appAdminConfig) {
        final UserInfoTokenServices userInfoTokenServices =
                new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(),
                        resourceServerProperties.getClientId());

        userInfoTokenServices.setPrincipalExtractor(new MsGraphPrincipalExtractor());
        userInfoTokenServices.setAuthoritiesExtractor(new DomainBasedAuthorityExtractor(appAdminConfig.getDomain()));

        return userInfoTokenServices;
    }

    @Bean
    @Scope("singleton")
    public OneDriveService oneDriveService(final AppOneDriveConfig oneDriveConfig,
                                           final AccessTokenRepository accessTokenRepository,
                                           final OAuth2ProtectedResourceDetails details) {
        return new OneDriveService(oneDriveConfig, accessTokenRepository, details);
    }

    /**
     * Extract the principal from the user details map using keys for Microsoft's Graph API. Fall back to the
     * {@link org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor} if no
     * principal is found.
     */
    private static class MsGraphPrincipalExtractor extends FixedPrincipalExtractor {

        static final String PRICIPAL_KEY = "userPrincipalName";

        @Override
        public Object extractPrincipal(final Map<String, Object> map) {
            if (map.containsKey(PRICIPAL_KEY)) {
                return map.get(PRICIPAL_KEY);
            } else {
                return super.extractPrincipal(map);
            }
        }
    }

    private static class DomainBasedAuthorityExtractor implements AuthoritiesExtractor {

        private final String domain;

        public DomainBasedAuthorityExtractor(String domain) {
            this.domain = domain;
        }

        @Override
        public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
            final String name = map.getOrDefault(MsGraphPrincipalExtractor.PRICIPAL_KEY, "").toString();
            if (name.toLowerCase().endsWith(domain.toLowerCase())) {
                return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
            } else {
                return Collections.emptyList();
            }
        }
    }
}
