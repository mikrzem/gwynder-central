package pl.net.gwynder.central.security.internal.services

import com.google.common.base.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import javax.annotation.PostConstruct

@Service
class InternalUserService(
        @Value("\${internal.token}")
        private val tokenFile: String
) : BaseService() {

    private var token: String = ""

    @PostConstruct
    fun ensureToken() {
        logger.info("Reading token file")
        if (Strings.isNullOrEmpty(tokenFile)) {
            throw Exception("Property 'internal.token' must be set")
        }
        token = try {
            Files.readString(
                    Path.of(tokenFile)
            )
        } catch (ex: Exception) {
            logger.warn("Couldn't read token file: $tokenFile")
            createNewToken()
        }
        logger.info("Current token: $token")
    }

    private fun createNewToken(): String {
        logger.info("Creating new token file")
        val newToken = UUID.randomUUID().toString()
        try {
            Files.writeString(
                    Path.of(tokenFile),
                    newToken
            )
        } catch (ex: Exception) {
            logger.error("Failed to create token file: $tokenFile")
            throw Exception(ex)
        }
        logger.info("Successfully created new token")
        return newToken
    }

    fun validateToken(testToken: String?): Boolean {
        return testToken != null && token == testToken
    }

}