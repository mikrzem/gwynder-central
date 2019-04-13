package pl.net.gwynder.central.proxy.dashboard.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.dashboard.entities.DashboardInfo
import pl.net.gwynder.central.proxy.dashboard.entities.ProxyDashboard
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import java.lang.Exception
import java.util.*
import java.util.stream.Collectors

@Service
class DashboardInfoProvider(
        private val restTemplate: RestTemplate,
        private val dashboardService: ProxyDashboardService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    fun selectDashboard(): List<DashboardInfo> {
        return dashboardService.select()
                .flatMap { dashboard -> fetchDashboard(dashboard).stream().collect(Collectors.toList()) }
    }

    private fun fetchDashboard(dashboard: ProxyDashboard): Optional<DashboardInfo> {
        return try {
            val headers: MultiValueMap<String, String> = HttpHeaders()
            userProvider.addUserHeader(headers)
            val response = restTemplate.exchange<DashboardInfo>(
                    dashboardService.findPath(dashboard),
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    DashboardInfo::class
            )
            if (!response.statusCode.is2xxSuccessful) {
                logger.info("Response code: ${response.statusCode.value()}")
            }
            Optional.ofNullable(response.body)
        } catch (ex: Exception) {
            logger.info("Failed to download dashboard ${dashboard.api.name}")
            Optional.empty()
        }
    }

}