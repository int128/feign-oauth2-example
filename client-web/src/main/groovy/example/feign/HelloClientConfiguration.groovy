package example.feign

import feign.Logger
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext

@Configuration
class HelloClientConfiguration {
    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
        new HelloOAuth2FeignRequestInterceptor(oAuth2ClientContext)
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        Logger.Level.BASIC
    }
}
