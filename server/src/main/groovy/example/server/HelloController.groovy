package example.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(produces = 'application/json')
class HelloController {
    @GetMapping('/hello')
    Hello helloWorld() {
        new Hello('world')
    }

    @GetMapping('/hello/{name}')
    Hello helloByName(@PathVariable String name) {
        new Hello(name)
    }
}
