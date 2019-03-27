package pl.net.gwynder.central.security.options.services

import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.options.entities.SecurityOptions
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.user.services.CentralUserService
import java.util.*

@Service
class SecurityOptionsService(
        private val repository: SecurityOptionsRepository,
        private val userService: CentralUserService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    private fun findCurrent(): SecurityOptions {
        return getOptions().orElseGet { createNewOptions() }
    }

    private fun createNewOptions(): SecurityOptions {
        val result = SecurityOptions(
                true,
                userService.findFirst()
        )
        return repository.save(result)
    }

    private fun getOptions(): Optional<SecurityOptions> {
        return repository.findAll().stream().findFirst()
    }

    var showRegistration: Boolean
        get() = findCurrent().showRegistration
        set(value) {
            val current = findCurrent()
            if (current.showRegistration != value) {
                current.showRegistration = value
                repository.save(current)
            }
        }

    var admin: CentralUser?
        get() = findCurrent().admin
        set(value) {
            val current = findCurrent()
            current.admin = value
            repository.save(current)
        }

    var isCurrentAdmin: Boolean = false
        get() {
            val existingAdmin = admin
            return if (existingAdmin != null) {
                Objects.equals(existingAdmin.email, userProvider.current())
            } else {
                false
            }
        }

    fun isAdmin(user: CentralUser): Boolean {
        val existingAdmin = admin
        return if (existingAdmin == null) {
            false
        } else {
            Objects.equals(user.id, existingAdmin.id)
        }
    }

}