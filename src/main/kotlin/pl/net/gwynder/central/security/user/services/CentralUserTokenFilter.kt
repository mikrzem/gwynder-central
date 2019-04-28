package pl.net.gwynder.central.security.user.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

const val TOKEN_HEADER = "ApplicationUserToken"

@Component
class CentralUserTokenFilter(
        private val service: CentralUserTokenService
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request is HttpServletRequest) {
            val token = request.getHeader(TOKEN_HEADER)
            service.checkToken(token).ifPresent { user ->
                val details = CentralUserDetails(user, false)
                SecurityContextHolder.getContext().authentication = PreAuthenticatedAuthenticationToken(
                        details,
                        details,
                        details.authorities
                )
            }
        }
        chain?.doFilter(request, response)
    }

}