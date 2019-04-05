package pl.net.gwynder.central.dashboard.repositories

import org.springframework.data.jpa.repository.Modifying
import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.dashboard.entities.ProxyDashboard
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import java.util.*
import javax.transaction.Transactional

interface ProxyDashboardRepository : BaseEntityRepository<ProxyDashboard> {

    fun findFirstByApi(api: ProxyApi): Optional<ProxyDashboard>

    fun findFirstByApiNameAndActiveIsTrue(name: String): Optional<ProxyDashboard>

    fun findByActiveIsTrue(): List<ProxyDashboard>

    @Modifying
    @Transactional
    fun deleteByApi(api: ProxyApi)

}