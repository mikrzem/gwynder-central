package pl.net.gwynder.central.security.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import java.util.*


@Service
class CommonUserDetailsProvider : BaseService() {

    fun findCurrent(): Optional<String> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            if (auth.principal is CommonUserDetails) {
                return Optional.of((auth.principal as CommonUserDetails).displayName)
            }
        }
        return Optional.empty()
    }

}