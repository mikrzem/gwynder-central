package pl.net.gwynder.central.health.services

import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.health.entities.ProxyHealth
import pl.net.gwynder.central.health.entities.ProxyHealthData
import pl.net.gwynder.central.health.repositories.ProxyHealthRepository
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

    fun remove(api: ProxyApi) {
        repository.deleteByApi(api)
    }
}