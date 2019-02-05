package pl.net.gwynder.central.proxy.application.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.proxy.application.entities.ProxyApplication
import pl.net.gwynder.central.proxy.application.entities.ProxyApplicationData
import pl.net.gwynder.central.proxy.application.repositories.ProxyApplicationRepository

@Service
class ProxyApplicationService(
        private val repository: ProxyApplicationRepository
) : BaseService() {

    fun saveApplication(data: ProxyApplicationData): ProxyApplicationData {
        val entity = fetchApplication(data.name)
        entity.path = data.path
        entity.displayName = data.displayName
        val saved = repository.save(entity)
        return toData(saved)
    }

    private fun fetchApplication(name: String): ProxyApplication {
        return repository.findFirstByName(name)
                .orElseGet { ProxyApplication(name) }
    }

    fun getApplication(name: String): ProxyApplicationData {
        return repository.findFirstByName(name)
                .map(this::toData)
                .orElseThrow { DataNotFound("Proxy application with name: $name") }
    }

    fun toData(entity: ProxyApplication): ProxyApplicationData {
        return ProxyApplicationData(
                entity.name,
                entity.path,
                entity.displayName
        )
    }

    fun removeApplication(name: String) {
        repository.findFirstByName(name)
                .ifPresent { application -> repository.delete(application) }
    }

    fun applyPath(name: String, suffix: String?, queryString: String?): String {
        val proxy = repository.findFirstByName(name)
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

    fun selectApplications(): List<ProxyApplicationData> {
        return repository.findAll()
                .map(this::toData)
    }

    fun checkApplication(name: String) {
        repository.findFirstByName(name).orElseThrow { DataNotFound("Proxy application with name: $name") }
    }
}