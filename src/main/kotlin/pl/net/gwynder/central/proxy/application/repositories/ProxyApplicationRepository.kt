package pl.net.gwynder.central.proxy.application.repositories

import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.proxy.application.entities.ProxyApplication
import java.util.*

interface ProxyApplicationRepository : BaseEntityRepository<ProxyApplication> {

    fun findFirstByName(name: String): Optional<ProxyApplication>

}