package example.client

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import javax.inject.Inject

@Slf4j
@Service
class HelloService {
    @Inject
    HelloClient client

    Hello hello(String... args) {
        if (args.length) {
            def hello = client.hello(args.join(','))
            log.info("Received from API server: $hello")
            hello
        } else {
            def hello = client.hello()
            log.info("Received from API server: $hello")
            hello
        }
    }
}
