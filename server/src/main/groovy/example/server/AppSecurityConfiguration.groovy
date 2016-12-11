package example.server

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter

@Configuration
class AppSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Override
    void init(AuthenticationManagerBuilder auth) {
        auth.inMemoryAuthentication()
            .withUser('theUser1').password('theResourceOwnerPassword').roles('USER')
        .and()
            .withUser('theUser2').password('theResourceOwnerPassword').roles('USER')
    }
}
