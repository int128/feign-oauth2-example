package example.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@EnableOAuth2Client
@EnableFeignClients
@SpringBootApplication
class App {
    static void main(String[] args) {
        SpringApplication.run(App, args)
    }
}
