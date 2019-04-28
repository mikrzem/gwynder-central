package pl.net.gwynder.central.security.user.services

import com.google.common.base.Strings
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.user.entities.CentralUserToken
import pl.net.gwynder.central.security.user.repositories.CentralUserTokenRepository
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class CentralUserTokenService(
        private val repository: CentralUserTokenRepository
) : BaseService() {

    private val codeLength: Int = 6

    private val symbols: List<Char> = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    private val random: Random = SecureRandom()

    fun select(user: CentralUser): List<CentralUserToken> {
        return repository.findByUserAndActiveIsTrue(user)
    }

    fun createNew(user: CentralUser): CentralUserToken {
        val token = CentralUserToken(
                user,
                generateNewCode(),
                null,
                null,
                null,
                false,
                LocalDateTime.now()
        )
        return repository.save(token)
    }

    fun confirm(requestCode: String, applicationName: String): CentralUserToken {
        val token = repository.findFirstByRequestCode(requestCode)
                .orElseThrow { DataNotFound("central-user-token") }
        token.responseCode = generateNewCode()
        token.applicationName = applicationName
        token.authorizationToken = generateNewToken()
        token.requestCode = null
        return repository.save(token)
    }

    fun activate(token: CentralUserToken): CentralUserToken {
        token.active = true
        token.responseCode = null
        return repository.save(token)
    }

    fun get(id: Long, user: CentralUser): CentralUserToken {
        return repository.findFirstByIdAndUser(id, user)
                .orElseThrow { DataNotFound("central-user-token") }
    }

    private fun generateNewCode(): String {
        val chars: MutableList<Char> = ArrayList()
        for (index in 1..codeLength) {
            chars.add(
                    symbols[random.nextInt(symbols.size)]
            )
        }
        return String(chars.toCharArray())
    }

    private fun generateNewToken(): String {
        return UUID.randomUUID().toString()
    }

    fun remove(id: Long, currentUser: CentralUser) {
        val token = get(id, currentUser)
        repository.delete(token)
    }

    fun checkToken(token: String?): Optional<CentralUser> {
        if (Strings.isNullOrEmpty(token) || token == null) {
            return Optional.empty()
        }
        return repository.findFirstByAuthorizationTokenAndActiveIsTrue(token)
                .map { userToken -> userToken.user }
    }

}