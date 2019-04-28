package pl.net.gwynder.central.security.user.repositories

import pl.net.gwynder.central.common.database.BaseEntityRepository
import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.user.entities.CentralUserToken
import java.util.*

interface CentralUserTokenRepository : BaseEntityRepository<CentralUserToken> {

    fun findByUserAndActiveIsTrue(user: CentralUser): List<CentralUserToken>

    fun findFirstByRequestCode(code: String): Optional<CentralUserToken>

    fun findFirstByAuthorizationTokenAndActiveIsTrue(token: String): Optional<CentralUserToken>

    fun findFirstByIdAndUser(id: Long, user: CentralUser): Optional<CentralUserToken>

}