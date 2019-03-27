package pl.net.gwynder.central.security.user.services

import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.services.CommonUserDetails

class CentralUserDetails(
        user: CentralUser,
        admin: Boolean
) : CommonUserDetails(
        user.email,
        if (admin) {
            listOf("USER", "ADMIN")
        } else {
            listOf("USER")
        },
        user.passwordHash
)