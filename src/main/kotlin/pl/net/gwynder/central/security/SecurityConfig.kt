package pl.net.gwynder.central.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import pl.net.gwynder.central.security.user.services.CentralUserDetailsService


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val userService: CentralUserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement()
                ?.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers(
                        "/resources/**",
                        "/auth/**",
                        "/actuator/**",
                        "/login"
                )?.permitAll()
                ?.antMatchers("/admin/**")?.hasRole("ADMIN")
                ?.anyRequest()?.authenticated()
                ?.and()
                ?.formLogin()
                ?.loginPage("/auth/login")
                ?.successForwardUrl("/home")
                ?.failureForwardUrl("/auth/login?error=true")
                ?.loginProcessingUrl("/auth/login/default")
                ?.permitAll()
                ?.and()
                ?.logout()
                ?.permitAll()
                ?.and()
//                .addFilterBefore(applicationTokenFilter, BasicAuthenticationFilter::class.java)
                ?.csrf()?.disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
                ?.userDetailsService(userService)
                ?.passwordEncoder(passwordEncoder())
    }

}