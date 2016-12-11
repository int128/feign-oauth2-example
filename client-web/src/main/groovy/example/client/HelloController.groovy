package example.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

import javax.inject.Inject

@RestController
class HelloController {
    @Inject
    HelloClient client

    @GetMapping('/hello')
    Hello helloWorld() {
        client.hello()
    }

    @GetMapping('/hello/{name}')
    Hello helloByName(@PathVariable String name) {
        client.hello(name)
    }
}
