package pl.net.gwynder.central.home

import org.springframework.stereotype.Service
import org.springframework.ui.Model
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService
import pl.net.gwynder.central.security.options.services.SecurityOptionsService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider

@Service
class NavigationService(
        private val userProvider: CommonUserDetailsProvider,
        private val applicationService: ProxyApplicationService,
        private val options: SecurityOptionsService
) : BaseService() {

    fun addNavigationData(model: Model, dashboard: Boolean = false, activeApplication: String? = null, admin: Boolean = false) {
        model.addAttribute(
                "username",
                userProvider.current()
        )
        model.addAttribute(
                "dashboard",
                dashboard
        )
        model.addAttribute(
                "admin",
                admin
        )
        model.addAttribute(
                "activeApplication",
                activeApplication
        )
        model.addAttribute(
                "applications",
                applicationService.selectApplications()
        )
        model.addAttribute(
                "adminAvailable",
                options.isCurrentAdmin
        )
    }

}