package pl.net.gwynder.central.proxy.api.controllers

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.ValidationError
import pl.net.gwynder.central.proxy.api.entities.ProxyApiData
import pl.net.gwynder.central.proxy.api.services.ProxyApiService

@RestController
@RequestMapping("/proxy/api")
@Secured("ROLE_INTERNAL")
class ProxyApiController(
        private val service: ProxyApiService
) : BaseService() {

    @PutMapping("/{name}")
    fun setProxy(
            @PathVariable("name") name: String,
            @RequestBody data: ProxyApiData
    ): ProxyApiData {
        if (data.name != name) {
            throw ValidationError("Name in url and data should be equal")
        }
        return service.saveApi(data)
    }

    @DeleteMapping("/{name}")
    fun deleteProxy(
            @PathVariable("name") name: String
    ) {
        service.removeApi(name)
    }

}