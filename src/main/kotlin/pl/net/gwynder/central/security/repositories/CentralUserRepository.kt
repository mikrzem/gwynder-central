package pl.net.gwynder.central.security.repositories

import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.security.entities.CentralUser
import java.util.*

interface CentralUserRepository : BaseEntityRepository<CentralUser> {

    fun findByEmail(login: String): Optional<CentralUser>

}