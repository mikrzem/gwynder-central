package pl.net.gwynder.central.proxy.application.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.application.entities.ProxyApplication
import pl.net.gwynder.central.proxy.application.entities.ProxyApplicationData
import pl.net.gwynder.central.proxy.application.repositories.ProxyApplicationRepository

@Service
class ProxyApplicationService(
        private val repository: ProxyApplicationRepository
) : BaseService() {

    fun update(api: ProxyApi, data: ProxyApplicationData?): ProxyApplication {
        val entity = get(api)
        if (data == null) {
            entity.active = false
            entity.displayName = api.name
            entity.path = ""
            entity.startPath = ""
        } else {
            entity.active = data.active
            entity.path = data.path
            entity.displayName = data.displayName
            entity.startPath = data.startPath
        }
        return repository.save(entity)
    }


    fun find(name: String): ProxyApplication {
        return repository.findFirstByApiNameAndActiveIsTrue(name)
                .orElseThrow { DataNotFound("Proxy application with name: $name") }
    }

    fun get(api: ProxyApi): ProxyApplication {
        return repository.findFirstByApi(api)
                .orElseGet { ProxyApplication(api) }
    }

    fun toData(entity: ProxyApplication): ProxyApplicationData {
        return ProxyApplicationData(
                entity.active,
                entity.path,
                entity.displayName,
                entity.startPath
        )
    }

    fun applyPath(name: String, suffix: String?, queryString: String?): String {
        val proxy = repository.findFirstByApiNameAndActiveIsTrue(name)
                .orElseThrow { DataNotFound("proxy application - $name") }
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

    fun selectApplications(): List<ProxyApplication> {
        return repository.findByActiveIsTrue()
    }

    fun remove(api: ProxyApi) {
        repository.deleteByApi(api)
    }
}