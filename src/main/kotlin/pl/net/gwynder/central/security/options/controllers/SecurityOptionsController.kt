package pl.net.gwynder.central.security.options.controllers

import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.admin.AdminOptions
import pl.net.gwynder.central.security.options.services.SecurityOptionsService
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin/api")
class SecurityOptionsController(
        private val optionsService: SecurityOptionsService
) : BaseService() {

    @PostMapping("/options")
    fun optionsUpdate(
            @ModelAttribute options: AdminOptions,
            response: HttpServletResponse
    ) {
        optionsService.showRegistration = options.showRegistration
        response.sendRedirect("/admin")
    }

}