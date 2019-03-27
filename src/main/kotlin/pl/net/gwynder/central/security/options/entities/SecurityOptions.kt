package pl.net.gwynder.central.security.options.entities

import pl.net.gwynder.central.common.database.BaseEntity
import pl.net.gwynder.central.security.user.entities.CentralUser
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class SecurityOptions(
        @Column(nullable = false)
        var showRegistration: Boolean = false,
        @ManyToOne
        @JoinColumn(name = "adminId")
        var admin: CentralUser? = null
) : BaseEntity()