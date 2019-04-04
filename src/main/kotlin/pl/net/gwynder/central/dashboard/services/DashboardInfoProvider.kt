package pl.net.gwynder.central.dashboard.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.dashboard.entities.DashboardInfo
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.api.services.ProxyApiService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import java.lang.Exception
import java.util.*
import java.util.stream.Collectors

@Service
class DashboardInfoProvider(
        private val restTemplate: RestTemplate,
        private val proxyApiService: ProxyApiService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    fun selectDashboard(): List<DashboardInfo> {
        return proxyApiService.selectDashboards()
                .flatMap { proxyApi -> fetchDashboard(proxyApi).stream().collect(Collectors.toList()) }
    }

    private fun fetchDashboard(proxyApi: ProxyApi): Optional<DashboardInfo> {
        return try {
            val headers: MultiValueMap<String, String> = HttpHeaders()
            userProvider.addUserHeader(headers)
            val response = restTemplate.exchange<DashboardInfo>(
                    proxyApiService.dashboardPath(proxyApi),
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    DashboardInfo::class
            )
            if (!response.statusCode.is2xxSuccessful) {
                logger.info("Response code: ${response.statusCode.value()}")
            }
            Optional.ofNullable(response.body)
        } catch (ex: Exception) {
            logger.info("Failed to download dashboard ${proxyApi.name}")
            Optional.empty()
        }
    }

}