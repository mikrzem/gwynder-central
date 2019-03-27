package pl.net.gwynder.central.security.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import pl.net.gwynder.central.common.BaseService
import java.net.http.HttpHeaders
import java.util.*

const val USER_HEADER = "CentralUserDisplayName"

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

    fun clearCurrent() {
        SecurityContextHolder.getContext().authentication = null
    }

    fun addUserHeader(headers: MultiValueMap<String, String>) {
        headers.add(
                USER_HEADER,
                current()
        )
    }

    fun current() = findCurrent().orElse("[no user]")

}