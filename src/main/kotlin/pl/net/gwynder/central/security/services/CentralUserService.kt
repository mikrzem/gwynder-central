package pl.net.gwynder.central.security.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pl.net.gwynder.central.security.repositories.CentralUserRepository

@Service
class CentralUserService(
        private val repository: CentralUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("Empty username")
        }
        return repository.findByLogin(username)
                .orElseThrow { UsernameNotFoundException("No user with username: $username") }
    }

}