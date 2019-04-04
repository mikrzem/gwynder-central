package pl.net.gwynder.central.dashboard.services

import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.dashboard.entities.DashboardInfo
import pl.net.gwynder.central.dashboard.entities.DashboardInfoRow
import pl.net.gwynder.central.dashboard.entities.DashboardLog
import pl.net.gwynder.central.dashboard.entities.DashboardLogRow
import pl.net.gwynder.central.dashboard.repositories.DashboardLogRepository
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import java.time.LocalDateTime

@Service
class DashboardLogService(
        private val repository: DashboardLogRepository,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    fun save(log: DashboardInfo) {
        repository.save(
                DashboardLog(
                        userProvider.current(),
                        LocalDateTime.now(),
                        log.title,
                        rowsToEntity(log.rows)
                )
        )
    }

    private fun rowsToEntity(rows: List<DashboardInfoRow>): List<DashboardLogRow> {
        return rows.map { row -> DashboardLogRow(row.description, row.content) }
    }

}