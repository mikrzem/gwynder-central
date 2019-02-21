package pl.net.gwynder.central.home

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import javax.servlet.http.HttpServletResponse

@Controller
class HomePageController(
        private val userProvider: CommonUserDetailsProvider,
        private val applicationService: ProxyApplicationService
) : BaseService() {

    @GetMapping("/")
    fun root(response: HttpServletResponse) {
        if (userProvider.findCurrent().isPresent) {
            response.sendRedirect("/home")
        } else {
            response.sendRedirect("/auth/login")
        }
    }

    @GetMapping("/home")
    fun home(
            model: Model
    ): String {
        addNavigationData(model, dashboard = true)
        return "home/dashboard"
    }

    @GetMapping("/home/{applicationName}")
    fun applicationPage(
            @PathVariable("applicationName") applicationName: String,
            model: Model
    ): String {
        val data = applicationService.getApplication(applicationName)
        model.addAttribute("applicationUrl", "/application/${data.name}/${data.startPath}")
        addNavigationData(model, activeApplication = applicationName)
        return "home/proxy.application"
    }

    private fun addNavigationData(model: Model, dashboard: Boolean = false, activeApplication: String? = null) {
        model.addAttribute(
                "username",
                userProvider.findCurrent().orElse("[no user]")
        )
        model.addAttribute(
                "dashboard",
                dashboard
        )
        model.addAttribute(
                "activeApplication",
                activeApplication
        )
        model.addAttribute(
                "applications",
                applicationService.selectApplications()
        )
    }

}