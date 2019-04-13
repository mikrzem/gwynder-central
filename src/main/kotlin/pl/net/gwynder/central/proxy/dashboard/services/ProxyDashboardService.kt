package pl.net.gwynder.central.proxy.dashboard.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.proxy.dashboard.entities.ProxyDashboard
import pl.net.gwynder.central.proxy.dashboard.entities.ProxyDashboardData
import pl.net.gwynder.central.proxy.dashboard.repositories.ProxyDashboardRepository
import pl.net.gwynder.central.proxy.api.entities.ProxyApi

@Service
class ProxyDashboardService(
        private val repository: ProxyDashboardRepository
) : BaseService() {

    fun update(api: ProxyApi, data: ProxyDashboardData?): ProxyDashboard {
        val dashboard = get(api)
        if (data == null) {
            dashboard.active = false
            dashboard.path = null
        } else {
            dashboard.active = data.active
            dashboard.path = data.path
        }
        return repository.save(dashboard)
    }

    fun find(name: String): ProxyDashboard {
        return repository.findFirstByApiNameAndActiveIsTrue(name)
                .orElseThrow { DataNotFound("Proxy dashboard with name: $name") }
    }

    fun get(api: ProxyApi): ProxyDashboard {
        return repository.findFirstByApi(api)
                .orElseGet { ProxyDashboard(api) }
    }

    fun toData(entity: ProxyDashboard): ProxyDashboardData {
        return ProxyDashboardData(
                entity.active,
                entity.path
        )
    }

    fun select(): List<ProxyDashboard> {
        return repository.findByActiveIsTrue()
    }

    fun remove(api: ProxyApi) {
        repository.deleteByApi(api)
    }

    fun findPath(dashboard: ProxyDashboard): String {
        return if (Strings.isNullOrEmpty(dashboard.path)) {
            "${dashboard.api.path}/central/dashboard"
        } else {
            "${dashboard.api.path}/${dashboard.path}"
        }
    }
}