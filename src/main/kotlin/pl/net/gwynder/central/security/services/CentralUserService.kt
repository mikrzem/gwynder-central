package pl.net.gwynder.central.security.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.entities.CentralUser
import pl.net.gwynder.central.security.repositories.CentralUserRepository

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

}