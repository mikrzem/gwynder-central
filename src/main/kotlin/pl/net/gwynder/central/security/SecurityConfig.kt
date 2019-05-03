package pl.net.gwynder.central.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import pl.net.gwynder.central.security.internal.services.InternalUserFilter
import pl.net.gwynder.central.security.user.services.CentralUserDetailsService
import pl.net.gwynder.central.security.user.services.CentralUserTokenFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val userService: CentralUserDetailsService,
        private val internalUserFilter: InternalUserFilter,
        private val passwordEncoder: PasswordEncoder,
        private val userTokenFilter: CentralUserTokenFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement()
                ?.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                ?.and()
                ?.authorizeRequests()
                ?.antMatchers(
                        "/resources/**",
                        "/auth/**",
                        "/login",
                        "/user/token/confirmation/**",
                        "/status/**"
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
                ?.addFilterBefore(internalUserFilter, BasicAuthenticationFilter::class.java)
                ?.addFilterBefore(userTokenFilter, BasicAuthenticationFilter::class.java)
                ?.csrf()?.disable()
                ?.headers()
                ?.frameOptions()
                ?.sameOrigin()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
                ?.userDetailsService(userService)
                ?.passwordEncoder(passwordEncoder)
    }

}