package example.server

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @GetMapping('/user')
    def user(Authentication authentication) {
        authentication
    }
}
