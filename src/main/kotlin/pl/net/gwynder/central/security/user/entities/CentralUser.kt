package pl.net.gwynder.central.security.user.entities

import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class CentralUser(
        @Column(unique = true, nullable = false)
        var email: String = "",
        @Column(nullable = false)
        var passwordHash: String = ""
) : BaseEntity()