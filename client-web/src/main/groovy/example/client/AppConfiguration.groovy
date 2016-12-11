package example.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

import javax.servlet.Filter

@Configuration
class AppConfiguration {
    @Bean
    Filter commonsRequestLoggingFilter() {
        def filter = new CommonsRequestLoggingFilter()
        filter.includeClientInfo = true
        filter.includeQueryString = true
        filter.includeHeaders = true
        filter.includePayload = true
        filter
    }
}
