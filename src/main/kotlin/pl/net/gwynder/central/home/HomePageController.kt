package pl.net.gwynder.central.home

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.admin.AdminOptions
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService
import pl.net.gwynder.central.security.options.services.SecurityOptionsService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import javax.servlet.http.HttpServletResponse

@Controller
class HomePageController(
        private val userProvider: CommonUserDetailsProvider,
        private val applicationService: ProxyApplicationService,
        private val navigation: NavigationService
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
        navigation.addNavigationData(model, dashboard = true)
        return "home/dashboard"
    }

    @GetMapping("/home/{applicationName}")
    fun applicationPage(
            @PathVariable("applicationName") applicationName: String,
            model: Model
    ): String {
        applicationService.checkApplication(applicationName)
        model.addAttribute("applicationUrl", "/application/$applicationName")
        navigation.addNavigationData(model, activeApplication = applicationName)
        return "home/proxy.application"
    }

}