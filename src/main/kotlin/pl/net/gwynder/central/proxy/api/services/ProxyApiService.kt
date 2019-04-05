package pl.net.gwynder.central.proxy.api.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.dashboard.services.ProxyDashboardService
import pl.net.gwynder.central.health.services.ProxyHealthService
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.api.entities.ProxyApiData
import pl.net.gwynder.central.proxy.api.repositories.ProxyApiRepository
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService
import javax.transaction.Transactional

@Service
class ProxyApiService(
        private val repository: ProxyApiRepository,
        private val applicationService: ProxyApplicationService,
        private val dashboardService: ProxyDashboardService,
        private val healthService: ProxyHealthService
) : BaseService() {

    @Transactional
    fun update(data: ProxyApiData): ProxyApi {
        val entity: ProxyApi = get(data.name)
        entity.path = data.path
        val saved = repository.save(entity)
        applicationService.update(saved, data.application)
        dashboardService.update(saved, data.dashboard)
        healthService.update(saved, data.health)
        return saved
    }

    fun find(name: String): ProxyApi {
        return repository.findFirstByName(name)
                .orElseThrow { DataNotFound("proxy api - $name") }
    }

    fun get(name: String): ProxyApi = repository.findFirstByName(name).orElseGet { ProxyApi(name) }

    fun toData(entity: ProxyApi): ProxyApiData {
        return ProxyApiData(
                entity.name,
                entity.path,
                applicationService.toData(
                        applicationService.get(entity)
                ),
                dashboardService.toData(
                        dashboardService.get(entity)
                ),
                healthService.toData(
                        healthService.get(entity)
                )
        )
    }

    @Transactional
    fun remove(name: String) {
        val api = find(name)
        applicationService.remove(api)
        dashboardService.remove(api)
        healthService.remove(api)
        repository.delete(api)
    }

    fun applyPath(name: String, suffix: String?, queryString: String?): String {
        val proxy = repository.findFirstByName(name)
                .orElseThrow { DataNotFound("proxy api - $name") }
        val path = if (Strings.isNullOrEmpty(suffix)) {
            proxy.path
        } else {
            "${proxy.path}/$suffix"
        }
        return if (Strings.isNullOrEmpty(queryString)) {
            path
        } else {
            "$path?$queryString"
        }
    }
}