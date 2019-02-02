package pl.net.gwynder.central.security.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class CentralUser(
        @Column(unique = true)
        var email: String = "",
        var passwordHash: String = ""
) : BaseEntity(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(
                SimpleGrantedAuthority("ROLE_USER")
        )
    }

    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}