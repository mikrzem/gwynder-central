package pl.net.gwynder.central.proxy.health.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.health.entities.HealthInfo
import pl.net.gwynder.central.proxy.health.entities.HealthResponse
import pl.net.gwynder.central.proxy.health.entities.ProxyHealth
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider

@Service
class HealthInfoProvider(
        private val rest: RestTemplate,
        private val proxyHealthService: ProxyHealthService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    fun fetchReponse(proxy: ProxyHealth): HealthResponse {
        try {
            val headers: MultiValueMap<String, String> = HttpHeaders()
            userProvider.addUserHeader(headers)
            val response = rest.exchange<HealthInfo>(
                    proxyHealthService.findPath(proxy),
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    HealthInfo::class.java
            )
            return HealthResponse(
                    response.statusCodeValue,
                    response.body
            )
        } catch (ex: Exception) {
            logger.error("Failed to check application health", ex)
            return HealthResponse(
                    600, null
            )
        }
    }

}