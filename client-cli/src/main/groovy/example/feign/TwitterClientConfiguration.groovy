package example.feign

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails

@Configuration
class TwitterClientConfiguration {

    @Value('${security.oauth2.twitter.access-token-uri}')
    private String accessTokenUri

    @Value('${security.oauth2.twitter.client-id}')
    private String clientId

    @Value('${security.oauth2.twitter.client-secret}')
    private String clientSecret

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
        new OAuth2FeignRequestInterceptor(oAuth2ClientContext(), resource())
    }

    @Bean
    OAuth2ClientContext oAuth2ClientContext() {
        new DefaultOAuth2ClientContext()
    }

    @Bean
    OAuth2ProtectedResourceDetails resource() {
        def details = new ClientCredentialsResourceDetails()
        details.accessTokenUri = accessTokenUri
        details.clientId = clientId
        details.clientSecret = clientSecret
        details
    }
}
