package pl.net.gwynder.central.proxy.application.controllers

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.ValidationError
import pl.net.gwynder.central.proxy.application.entities.ProxyApplicationData
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService

@RestController
@RequestMapping("/proxy/application")
@Secured("ROLE_INTERNAL")
class ProxyApplicationController(
        private val service: ProxyApplicationService
) : BaseService() {

    @PutMapping("/{name}")
    fun setProxy(
            @PathVariable("name") name: String,
            @RequestBody data: ProxyApplicationData
    ): ProxyApplicationData {
        if (data.name != name) {
            throw ValidationError("Name in url and data should be equal")
        }
        return service.saveApplication(data)
    }

    @DeleteMapping("/{name}")
    fun deleteProxy(
            @PathVariable("name") name: String
    ) {
        service.removeApplication(name)
    }

}