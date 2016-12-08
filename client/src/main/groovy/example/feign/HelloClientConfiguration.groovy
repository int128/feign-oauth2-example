package example.feign

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails

@Configuration
class HelloClientConfiguration {
    @Value('${security.oauth2.hello.access-token-uri}')
    private String accessTokenUri

    @Value('${security.oauth2.hello.client-id}')
    private String clientId

    @Value('${security.oauth2.hello.client-secret}')
    private String clientSecret

    @Value('${security.oauth2.hello.scope}')
    private String scope

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext) {
        new OAuth2FeignRequestInterceptor(oauth2ClientContext, resource())
    }

    @Bean
    OAuth2ProtectedResourceDetails resource() {
        def details = new ResourceOwnerPasswordResourceDetails()
        details.accessTokenUri = accessTokenUri
        details.clientId = clientId
        details.clientSecret = clientSecret
        details.scope = [scope]
        details.username = 'theUser'
        details.password = 'thePassword'
        details
    }
}
