package pl.net.gwynder.central.security.user.services

import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.services.CommonUserDetails

class CentralUserDetails(
        user: CentralUser
) : CommonUserDetails(
        user.email,
        listOf("USER"),
        user.passwordHash
)