package example.feign

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException
import org.springframework.security.oauth2.client.token.AccessTokenProvider
import org.springframework.security.oauth2.client.token.AccessTokenRequest
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails
import org.springframework.security.oauth2.common.OAuth2AccessToken

@Slf4j
class HelloOAuth2FeignRequestInterceptor extends OAuth2FeignRequestInterceptor {
    @Value('${security.oauth2.hello.access-token-uri}')
    private String accessTokenUri

    @Value('${security.oauth2.hello.client-id}')
    private String clientId

    @Value('${security.oauth2.hello.client-secret}')
    private String clientSecret

    @Value('${security.oauth2.hello.scope}')
    private String scope

    private final OAuth2ClientContext oAuth2ClientContext

    def HelloOAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
        super(oAuth2ClientContext, null)
        this.oAuth2ClientContext = oAuth2ClientContext
    }

    @Override
    protected OAuth2AccessToken acquireAccessToken() {
        acquireAccessToken(accessTokenProvider(), resource())
    }

    /**
     * @see OAuth2FeignRequestInterceptor#acquireAccessToken()
     */
    protected OAuth2AccessToken acquireAccessToken(AccessTokenProvider accessTokenProvider, OAuth2ProtectedResourceDetails resource)
        throws UserRedirectRequiredException {
        AccessTokenRequest tokenRequest = oAuth2ClientContext.getAccessTokenRequest();
        if (tokenRequest == null) {
            throw new AccessTokenRequiredException(
                "Cannot find valid context on request for resource '"
                    + resource.getId() + "'.",
                resource);
        }
        String stateKey = tokenRequest.getStateKey();
        if (stateKey != null) {
            tokenRequest.setPreservedState(
                oAuth2ClientContext.removePreservedState(stateKey));
        }
        OAuth2AccessToken existingToken = oAuth2ClientContext.getAccessToken();
        if (existingToken != null) {
            oAuth2ClientContext.setAccessToken(existingToken);
        }
        OAuth2AccessToken obtainableAccessToken;
        obtainableAccessToken = accessTokenProvider.obtainAccessToken(resource,
            tokenRequest);
        if (obtainableAccessToken == null || obtainableAccessToken.getValue() == null) {
            throw new IllegalStateException(
                " Access token provider returned a null token, which is illegal according to the contract.");
        }
        oAuth2ClientContext.setAccessToken(obtainableAccessToken);
        return obtainableAccessToken;
    }

    private accessTokenProvider() {
        def accessTokenProvider = accessTokenProviderByAuthentication()
        // Using HttpClient for logging
        accessTokenProvider.requestFactory = new HttpComponentsClientHttpRequestFactory()
        accessTokenProvider
    }

    private accessTokenProviderByAuthentication() {
        def authentication = SecurityContextHolder.context.authentication
        if (!authentication || authentication instanceof AnonymousAuthenticationToken) {
            new ClientCredentialsAccessTokenProvider()
        } else {
            new ResourceOwnerPasswordAccessTokenProvider()
        }
    }

    private resource() {
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
