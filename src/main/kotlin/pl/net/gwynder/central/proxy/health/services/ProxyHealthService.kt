package pl.net.gwynder.central.proxy.health.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.health.entities.ProxyHealth
import pl.net.gwynder.central.proxy.health.entities.ProxyHealthData
import pl.net.gwynder.central.proxy.health.repositories.ProxyHealthRepository
import pl.net.gwynder.central.proxy.api.entities.ProxyApi

@Service
class ProxyHealthService(
        private val repository: ProxyHealthRepository
) : BaseService() {

    fun update(api: ProxyApi, data: ProxyHealthData?): ProxyHealth {
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

    fun find(name: String): ProxyHealth {
        return repository.findFirstByApiName(name).orElseGet { ProxyHealth() }
    }

    fun get(api: ProxyApi): ProxyHealth {
        return repository.findFirstByApi(api)
                .orElseGet { ProxyHealth(api) }
    }

    fun toData(entity: ProxyHealth): ProxyHealthData {
        return ProxyHealthData(
                entity.active,
                entity.path
        )
    }

    fun select(): List<ProxyHealth> {
        return repository.findByActiveIsTrue()
    }

    fun selectAll(): List<ProxyHealth> {
        return repository.findAll()
    }

    fun remove(api: ProxyApi) {
        repository.deleteByApi(api)
    }

    fun findPath(dashboard: ProxyHealth): String {
        return if (Strings.isNullOrEmpty(dashboard.path)) {
            "${dashboard.api.path}/central/health"
        } else {
            "${dashboard.api.path}/${dashboard.path}"
        }
    }
}