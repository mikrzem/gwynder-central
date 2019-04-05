package pl.net.gwynder.central.health.entities

import pl.net.gwynder.central.common.database.BaseEntity
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import javax.persistence.*

@Entity
class ProxyHealth(
        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "apiId")
        var api: ProxyApi = ProxyApi(),
        @Column(nullable = false)
        var active: Boolean = false,
        @Column
        var path: String? = null
) : BaseEntity()