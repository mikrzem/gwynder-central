package pl.net.gwynder.central.proxy.api.entities

import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.*

@Entity
class ProxyApi(
        @Column(nullable = false, unique = true)
        var name: String = "",
        @Column(nullable = false)
        var path: String = "",
        @Column
        var dashboardPath: String? = null,
        @ElementCollection
        @CollectionTable(name = "ProxyApiOptions", joinColumns = [JoinColumn(name = "apiId")])
        @Column(name = "optionType")
        @Enumerated(EnumType.STRING)
        var options: MutableSet<ProxyApiOptions> = HashSet()
) : BaseEntity()