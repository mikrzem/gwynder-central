package pl.net.gwynder.central.security.status

import org.springframework.http.MediaType
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider

@RestController
@RequestMapping("/status")
class StatusController(
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    @GetMapping("/alive", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun alive(): String {
        return "OK"
    }

    @Secured("ROLE_USER")
    @GetMapping("/authorization", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun authorization(): String {
        return userProvider.current()
    }

}