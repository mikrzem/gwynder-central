package pl.net.gwynder.central.proxy.api.entities

import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ProxyApi(
        @Column(nullable = false, unique = true)
        var name: String = "",
        @Column(nullable = false)
        var path: String = ""
) : BaseEntity()