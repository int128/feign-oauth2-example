package example.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients

import javax.inject.Inject

@EnableFeignClients
@SpringBootApplication
class App {
    @Inject
    HelloService helloService

    void run(String... args) {
        helloService.hello(args)
    }

    static void main(String[] args) {
        def applicationContext = SpringApplication.run(App, args)
        def app = applicationContext.getBean(App)
        app.run(args)
    }
}
