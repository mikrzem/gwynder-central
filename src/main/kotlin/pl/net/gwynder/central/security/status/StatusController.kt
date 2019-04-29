package pl.net.gwynder.central.security.status

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider

@Controller
@RequestMapping("/status")
class StatusController(
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    @GetMapping("/alive", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun alive(): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @GetMapping("/authorization", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun authorization(): ResponseEntity<String> {
        return userProvider.findCurrentDetails()
                .filter { details -> details.authorities.any { authority -> authority.authority == "ROLE_USER" } }
                .map { details -> ResponseEntity.ok(details.displayName) }
                .orElseGet { ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized") }
    }

}