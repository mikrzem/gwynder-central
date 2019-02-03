package pl.net.gwynder.central.security.services

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

abstract class CommonUserDetails(
        val displayName: String,
        roles: Collection<String>,
        private val passwordHash: String = "[invalid password]"
) : UserDetails {

    private val grantedAuthorities: MutableCollection<GrantedAuthority> = roles
            .map { role -> SimpleGrantedAuthority("ROLE_$role") }
            .toMutableList()

    override fun getUsername(): String {
        return displayName
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return grantedAuthorities
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