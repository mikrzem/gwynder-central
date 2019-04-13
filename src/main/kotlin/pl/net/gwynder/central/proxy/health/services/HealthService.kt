package pl.net.gwynder.central.proxy.health.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.health.entities.HealthResponse
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import java.time.LocalDateTime

@Service
class HealthService(
        private val healthService: ProxyHealthService,
        private val logService: HealthLogService,
        private val infoProvider: HealthInfoProvider
) : BaseService() {

    private val status: MutableMap<String, HealthStatus> = HashMap()

    @Scheduled(
            fixedDelay = 5 * 60 * 1000,  // 5 min
            initialDelay = 60 * 1000     // 1 min
    )
    fun checkHealth() {
        logger.info("Testing health")
        for (health in healthService.selectAll()) {
            if (health.active) {
                logger.info("Checking health: ${health.api.name}")
                markResponse(
                        health.api,
                        infoProvider.fetchReponse(health)
                )
            } else {
                markNotActive(health.api)
            }
        }
    }

    private fun markResponse(api: ProxyApi, response: HealthResponse) {
        logService.save(response.code, api, response.info)
        assign(
                api,
                HealthStatus(
                        true,
                        response.info?.healthy ?: false,
                        LocalDateTime.now()
                )
        )
    }

    private fun markNotActive(api: ProxyApi) {
        assign(
                api,
                HealthStatus(
                        false,
                        true,
                        LocalDateTime.now()
                )
        )
    }

    @Synchronized
    private fun assign(api: ProxyApi, nextStatus: HealthStatus) {
        status[api.name] = nextStatus
    }

    @Synchronized
    private fun find(api: ProxyApi): HealthStatus {
        if (!status.containsKey(api.name)) {
            val newStatus = HealthStatus(
                    false,
                    true,
                    LocalDateTime.now()
            )
            status[api.name] = newStatus
        }
        return status.getValue(api.name)
    }

    fun filter(apis: Collection<ProxyApi>): List<ProxyApi> {
        return apis.filter { api -> find(api).healthy }
    }

}

private data class HealthStatus(
        val active: Boolean,
        val healthy: Boolean,
        val lastCheck: LocalDateTime
)