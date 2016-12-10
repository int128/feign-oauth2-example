package example.server

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(produces = 'application/json')
class HelloController {
    @GetMapping('/hello')
    Hello helloWorld(Authentication authentication) {
        new Hello(authentication?.name)
    }

    @GetMapping('/hello/{name}')
    Hello helloByName(@PathVariable String name) {
        new Hello(name)
    }

    @GetMapping('/user')
    def user(Authentication authentication) {
        authentication
    }
}
