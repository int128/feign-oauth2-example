package example.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails

import javax.inject.Inject

@Slf4j
class MultiGrantOAuth2FeignRequestInterceptor implements RequestInterceptor {
    @Value('${security.oauth2.hello.access-token-uri}')
    private String accessTokenUri

    @Value('${security.oauth2.hello.client-id}')
    private String clientId

    @Value('${security.oauth2.hello.client-secret}')
    private String clientSecret

    @Value('${security.oauth2.hello.scope}')
    private String scope

    @Inject
    private OAuth2ClientContext oAuth2ClientContext

    @Override
    void apply(RequestTemplate template) {
        new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource()).apply(template)
    }

    OAuth2ProtectedResourceDetails resource() {
        def authentication = SecurityContextHolder.context.authentication
        if (!authentication || authentication instanceof AnonymousAuthenticationToken) {
            def details = new ClientCredentialsResourceDetails()
            details.accessTokenUri = accessTokenUri
            details.clientId = clientId
            details.clientSecret = clientSecret
            details.scope = [scope]
            log.debug("ClientCredentialsResourceDetails")
            details
        } else {
            def details = new ResourceOwnerPasswordResourceDetails()
            details.accessTokenUri = accessTokenUri
            details.clientId = clientId
            details.clientSecret = clientSecret
            details.scope = [scope]
            details.username = (authentication.principal as UserDetails).username
            details.password = 'theResourceOwnerPassword'  // TODO
            log.debug("ResourceOwnerPasswordResourceDetails: username=${details.username}")
            details
        }
    }
}
