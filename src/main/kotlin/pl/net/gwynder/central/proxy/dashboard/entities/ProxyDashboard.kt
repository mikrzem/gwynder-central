package pl.net.gwynder.central.proxy.dashboard.entities

import pl.net.gwynder.central.common.database.BaseEntity
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.api.entities.ProxyApiSection
import javax.persistence.*

@Entity
class ProxyDashboard(
        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "apiId")
        override var api: ProxyApi = ProxyApi(),
        @Column(nullable = false)
        var active: Boolean = false,
        @Column
        var path: String? = null
) : BaseEntity(), ProxyApiSection