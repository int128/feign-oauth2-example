package example.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter

import javax.servlet.Filter

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
