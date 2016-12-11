package example.client

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.inMemoryAuthentication()
            .withUser('theUser1').password('thePassword1').roles('USER')
        .and()
            .withUser('theUser2').password('thePassword2').roles('USER')
    }

    @Override
    protected void configure(HttpSecurity http) {
        http.sessionManagement().sessionFixation().newSession()

        http.authorizeRequests().anyRequest().permitAll()
        http.formLogin()
    }
}
