package pl.net.gwynder.central.proxy.health.entities

import pl.net.gwynder.central.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class HealthLog(
        @Column(nullable = false)
        var api: String = "",
        @Column(nullable = false)
        var healthy: Boolean = false,
        @Column(nullable = false)
        var responseCode: Int = 0,
        @Column(nullable = false)
        var startupTime: String = ""
) : BaseEntity()