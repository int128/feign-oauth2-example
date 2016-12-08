package example.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

import javax.inject.Inject

@EnableOAuth2Client
@EnableFeignClients
@SpringBootApplication
class App {
    @Inject
    HelloService helloService

    @Inject
    TwitterService twitterService

    void run(String... args) {
        def hello = helloService.hello(args)
        twitterService.search(hello.name)
    }

    static void main(String[] args) {
        def applicationContext = SpringApplication.run(App, args)
        def app = applicationContext.getBean(App)
        app.run(args)
    }
}
