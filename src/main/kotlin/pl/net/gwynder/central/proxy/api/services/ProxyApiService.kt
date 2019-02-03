package pl.net.gwynder.central.proxy.api.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.api.entities.ProxyApiData
import pl.net.gwynder.central.proxy.api.repositories.ProxyApiRepository

@Service
class ProxyApiService(
        private val repository: ProxyApiRepository
) : BaseService() {

    fun saveApi(data: ProxyApiData): ProxyApiData {
        val entity: ProxyApi = getByName(data)
        entity.path = data.path
        val saved = repository.save(entity)
        return toData(saved)
    }

    private fun getByName(data: ProxyApiData) =
            repository.findFirstByName(data.name)
                    .orElseGet { ProxyApi(data.name) }

    fun toData(entity: ProxyApi): ProxyApiData {
        return ProxyApiData(
                entity.name,
                entity.path
        )
    }

    fun removeApi(name: String) {
        repository.findFirstByName(name)
                .ifPresent(repository::delete)
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