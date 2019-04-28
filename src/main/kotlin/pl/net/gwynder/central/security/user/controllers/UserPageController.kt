package pl.net.gwynder.central.security.user.controllers

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.home.NavigationService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import pl.net.gwynder.central.security.user.entities.CentralUser
import pl.net.gwynder.central.security.user.services.CentralUserService
import pl.net.gwynder.central.security.user.services.CentralUserTokenService
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

@Controller
class UserPageController(
        private val navigation: NavigationService,
        private val service: CentralUserService,
        private val userProvider: CommonUserDetailsProvider,
        private val tokenService: CentralUserTokenService
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
        if (!service.checkPassword(userProvider.current(), current)) {
            changedPasswordResponse(response, "Incorrect current password")
        } else if (next != repeat) {
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

    @GetMapping("/user/token/list")
    fun tokenList(
            model: Model
    ): String {
        navigation.addNavigationData(model)
        model.addAttribute(
                "tokens",
                tokenService.select(
                        currentUser()
                )
        )
        return "user/token.list"
    }

    private fun currentUser(): CentralUser {
        return service.getUser(
                userProvider.current()
        )
    }

    @PostMapping("/user/token/removal")
    fun removeToken(
            @RequestParam("id") id: Long,
            response: HttpServletResponse
    ) {
        tokenService.remove(id, currentUser())
        response.sendRedirect("/user/token/list")
    }

    @GetMapping("/user/token/creation")
    fun createNewToken(
            response: HttpServletResponse
    ) {
        val token = tokenService.createNew(currentUser())
        response.sendRedirect("/user/token/progress/${token.id?.toString()}")
    }

    @GetMapping("/user/token/progress/{id}")
    fun tokenPanel(
            @PathVariable("id") id: Long,
            model: Model
    ): String {
        navigation.addNavigationData(model)
        model.addAttribute("token", tokenService.get(id, currentUser()))
        return "user/token.page"
    }

    @PostMapping("/user/token/activation")
    fun activateToken(
            @RequestParam("id") id: Long,
            response: HttpServletResponse
    ) {
        val token = tokenService.get(id, currentUser());
        tokenService.activate(token)
        response.sendRedirect("/user/token/progress/${token.id?.toString()}")
    }

}