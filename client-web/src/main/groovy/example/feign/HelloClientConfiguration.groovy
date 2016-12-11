package example.feign

import feign.Logger
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloClientConfiguration {
    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
        new MultiGrantOAuth2FeignRequestInterceptor()
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        Logger.Level.BASIC
    }
}
