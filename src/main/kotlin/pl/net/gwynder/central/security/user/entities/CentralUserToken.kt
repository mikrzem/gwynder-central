package pl.net.gwynder.central.security.user.entities

import pl.net.gwynder.central.common.database.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class CentralUserToken(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        var user: CentralUser = CentralUser(),
        @Column
        var requestCode: String? = null,
        @Column
        var applicationName: String? = null,
        @Column
        var responseCode: String? = null,
        @Column
        var authorizationToken: String? = null,
        @Column(nullable = false)
        var active: Boolean = false,
        @Column(nullable = false)
        var createdAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity()