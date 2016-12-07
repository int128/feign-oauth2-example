package example.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.web.filter.CommonsRequestLoggingFilter

import javax.servlet.Filter

@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
class App {
    @Bean
    Filter logFilter() {
        def filter = new CommonsRequestLoggingFilter()
        filter
    }

    static void main(String[] args) {
        SpringApplication.run(App, args)
    }
}
