package pl.net.gwynder.central.admin

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.home.NavigationService
import pl.net.gwynder.central.security.options.services.SecurityOptionsService

@Controller
class AdminPageController(
        private val navigation: NavigationService,
        private val options: SecurityOptionsService
) : BaseService() {

    @GetMapping("/admin")
    fun adminPage(
            model: Model
    ): String {
        navigation.addNavigationData(model, admin = true)
        model.addAttribute("options", AdminOptions(
                options.showRegistration
        ))
        return "admin/admin"
    }

}