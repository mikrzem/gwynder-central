package pl.net.gwynder.central.security.user.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.user.entities.CentralUserTokenConfirmationRequest
import pl.net.gwynder.central.security.user.entities.CentralUserTokenConfirmed
import pl.net.gwynder.central.security.user.services.CentralUserTokenService

@RestController
@RequestMapping("/user/token")
class CentralUserTokenController(
        private val service: CentralUserTokenService
) : BaseService() {

    @PostMapping("/confirmation")
    fun confirmation(@RequestBody data: CentralUserTokenConfirmationRequest): CentralUserTokenConfirmed {
        val token = service.confirm(data.code, data.applicationName)
        return CentralUserTokenConfirmed(token.responseCode!!, token.authorizationToken!!)
    }

}