package pl.net.gwynder.central.proxy.api.repositories

import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import java.util.*

interface ProxyApiRepository : BaseEntityRepository<ProxyApi> {

    fun findFirstByName(name: String): Optional<ProxyApi>

}