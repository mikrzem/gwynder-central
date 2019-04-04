package pl.net.gwynder.central.dashboard.services

import org.springframework.stereotype.Service
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.dashboard.entities.DashboardInfo

@Service
class DashboardService(
        private val logService: DashboardLogService,
        private val infoProvider: DashboardInfoProvider
) : BaseService() {

    fun select(): List<DashboardInfo> {
        val result = infoProvider.selectDashboard()
        result.forEach { dashboardInfo -> logService.save(dashboardInfo) }
        return result
    }

}