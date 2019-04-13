package pl.net.gwynder.central.proxy.health.services

import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.health.entities.HealthInfo
import pl.net.gwynder.central.proxy.health.entities.HealthLog
import pl.net.gwynder.central.proxy.health.repositories.HealthLogRepository
import pl.net.gwynder.central.proxy.api.entities.ProxyApi

@Service
class HealthLogService(
        private val repository: HealthLogRepository
) : BaseService() {

    fun save(statusCode: Int, api: ProxyApi, data: HealthInfo?): HealthLog {
        return repository.save(
                HealthLog(
                        api.name,
                        statusCode == 200 && data != null && data.healthy,
                        statusCode,
                        data?.startupTime ?: ""
                )
        )
    }

}