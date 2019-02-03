package pl.net.gwynder.central.security.internal.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import pl.net.gwynder.central.security.internal.data.InternalUserDetails
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

const val TOKEN_HEADER = "InternalToken"

@Component
class InternalUserFilter(
        private val service: InternalUserService
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request is HttpServletRequest) {
            val token = request.getHeader(TOKEN_HEADER)
            if (service.validateToken(token)) {
                val details = InternalUserDetails()
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