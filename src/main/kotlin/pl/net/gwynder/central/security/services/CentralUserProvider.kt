package pl.net.gwynder.central.security.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.entities.CentralUser
import java.util.*


@Service
class CentralUserProvider : BaseService() {

    fun findCurrent(): Optional<CentralUser> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            if (auth.principal is CentralUser) {
                return Optional.of(auth.principal as CentralUser)
            }
        }
        return Optional.empty()
    }

}