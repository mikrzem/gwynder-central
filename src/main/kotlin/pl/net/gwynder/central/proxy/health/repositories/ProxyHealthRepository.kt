package pl.net.gwynder.central.proxy.health.repositories

import org.springframework.data.jpa.repository.Modifying
import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.proxy.health.entities.ProxyHealth
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import java.util.*
import javax.transaction.Transactional

interface ProxyHealthRepository : BaseEntityRepository<ProxyHealth> {

    fun findFirstByApi(api: ProxyApi): Optional<ProxyHealth>

    fun findFirstByApiName(name: String): Optional<ProxyHealth>

    fun findByActiveIsTrue(): List<ProxyHealth>

    @Modifying
    @Transactional
    fun deleteByApi(api: ProxyApi)

}