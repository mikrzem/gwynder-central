package pl.net.gwynder.central.security.user.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.common.errors.DataNotFound
import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.user.repositories.CentralUserRepository

@Service
class CentralUserService(
        private val repository: CentralUserRepository,
        private val passwordEncoder: PasswordEncoder
) : BaseService() {

    fun register(email: String, password: String, repeatPassword: String) {
        if (password != repeatPassword) {
            throw RegisterError("Password different then repeat password")
        }
        try {
            val user = CentralUser(
                    email,
                    passwordEncoder.encode(password)
            )
            repository.save(user)
        } catch (ex: Exception) {
            logger.error("Error creating user: $email", ex)
            throw RegisterError("Failed to create user")
        }
    }

    fun findFirst(): CentralUser? {
        return repository.findAll().firstOrNull()
    }

    fun checkPassword(userEmail: String?, password: String): Boolean {
        if (userEmail == null) {
            return false
        }
        return repository.findByEmail(userEmail)
                .map { user -> passwordEncoder.matches(password, user.passwordHash) }
                .orElse(false)
    }

    fun changePassword(userEmail: String?, password: String) {
        if (userEmail == null) {
            return
        }
        repository.findByEmail(userEmail)
                .ifPresent { user ->
                    user.passwordHash = passwordEncoder.encode(password);
                    repository.save(user)
                }
    }

    fun getUser(userEmail: String): CentralUser {
        return repository.findByEmail(userEmail)
                .orElseThrow { DataNotFound("logged-in-user") }
    }

}