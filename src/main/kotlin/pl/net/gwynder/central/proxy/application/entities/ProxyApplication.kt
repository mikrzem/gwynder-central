package pl.net.gwynder.central.proxy.application.entities

import pl.net.gwynder.central.common.database.BaseEntity
import pl.net.gwynder.central.proxy.api.entities.ProxyApi
import pl.net.gwynder.central.proxy.api.entities.ProxyApiSection
import javax.persistence.*

@Entity
class ProxyApplication(
        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "apiId")
        override var api: ProxyApi = ProxyApi(),
        @Column(nullable = false)
        var active: Boolean = false,
        @Column(nullable = false)
        var path: String = "",
        @Column(nullable = false)
        var displayName: String = "",
        @Column(nullable = false)
        var startPath: String = ""
) : BaseEntity(), ProxyApiSection