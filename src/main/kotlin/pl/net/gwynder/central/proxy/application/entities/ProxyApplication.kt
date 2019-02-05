package pl.net.gwynder.central.proxy.application.entities

import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ProxyApplication(
        @Column(unique = true, nullable = false)
        val name: String = "",
        @Column(nullable = false)
        var path: String = "",
        @Column(nullable = false)
        var displayName: String = ""
): BaseEntity()