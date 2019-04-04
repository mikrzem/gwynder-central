package pl.net.gwynder.central.dashboard.entities

import pl.net.gwynder.central.common.database.BaseEntity
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class DashboardLog(
        @Column(nullable = false)
        var owner: String = "",
        @Column(nullable = false)
        var generatedAt: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var title: String = "",
        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "logId", nullable = false)
        var rows: List<DashboardLogRow> = listOf()
) : BaseEntity()

@Entity
class DashboardLogRow(
        @Column(nullable = false)
        var description: String = "",
        @Column
        var content: String? = null
) : BaseEntity()