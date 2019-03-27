package pl.net.gwynder.central.security.user.controllers

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.home.NavigationService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import pl.net.gwynder.central.security.user.services.CentralUserService
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

@Controller
class UserPageController(
        private val navigation: NavigationService,
        private val service: CentralUserService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    @GetMapping("/user/password")
    fun changePassword(
            @RequestParam("success", defaultValue = "false") success: Boolean,
            @RequestParam("error", required = false) error: String?,
            model: Model
    ): String {
        navigation.addNavigationData(model)
        model.addAttribute("success", success)
        model.addAttribute("error", error)
        return "user/change.password"
    }

    @PostMapping("/user/password", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun verifyChangePassword(
            @RequestParam("current") current: String,
            @RequestParam("next") next: String,
            @RequestParam("repeat") repeat: String,
            response: HttpServletResponse
    ) {
        if(!service.checkPassword(userProvider.current(), current)) {
            changedPasswordResponse(response, "Incorrect current password")
        } else if(next != repeat) {
            changedPasswordResponse(response, "New password doesn't match repeated password")
        } else {
            service.changePassword(userProvider.current(), next)
            changedPasswordResponse(response)
        }
    }

    fun changedPasswordResponse(response: HttpServletResponse, error: String? = null) {
        if (error != null) {
            response.sendRedirect("/user/password?error=" + URLEncoder.encode(error, Charsets.UTF_8))
        } else {
            response.sendRedirect("/user/password?success=true")
        }
    }

}