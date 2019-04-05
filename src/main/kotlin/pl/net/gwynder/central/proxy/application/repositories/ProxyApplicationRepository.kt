package pl.net.gwynder.central.proxy.application.repositories

import org.springframework.data.jpa.repository.Modifying
import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.application.entities.ProxyApplication
import java.util.*
import javax.transaction.Transactional

interface ProxyApplicationRepository : BaseEntityRepository<ProxyApplication> {

    fun findFirstByApi(api: ProxyApi): Optional<ProxyApplication>

    fun findFirstByApiNameAndActiveIsTrue(name: String): Optional<ProxyApplication>

    fun findByActiveIsTrue(): List<ProxyApplication>

    @Modifying
    @Transactional
    fun deleteByApi(api: ProxyApi)

}