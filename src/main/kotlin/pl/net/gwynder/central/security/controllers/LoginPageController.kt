package pl.net.gwynder.central.security.controllers

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import pl.net.gwynder.central.security.user.services.CentralUserService
import pl.net.gwynder.central.security.user.services.RegisterError
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/auth")
class LoginPageController(
        private val userService: CentralUserService,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    @GetMapping("/login")
    fun loginPage(
            @RequestParam("error", defaultValue = "false") error: Boolean,
            model: Model,
            response: HttpServletResponse
    ): String {
        if (userProvider.findCurrent().isPresent) {
            response.sendRedirect("/home")
        }
        model.addAttribute("error", error)
        return "auth/login"
    }

    @PostMapping("/login", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun verifyLogin(
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        loginUser(request, username, password, response)
    }

    @GetMapping("/register")
    fun registerPage(
            @RequestParam("errorMessage", required = false) errorMessage: String?,
            model: Model
    ): String {
        model.addAttribute("errorMessage", errorMessage)
        return "auth/register";
    }

    @PostMapping("/register", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun verifyRegister(
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("repeatPassword") repeatPassword: String,
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        try {
            userService.register(username, password, repeatPassword)
            loginUser(request, username, password, response)
        } catch (error: RegisterError) {
            logger.error("Error during registration", error)
            response.sendRedirect("/auth/register?errorMessage=" + URLEncoder.encode(error.message, StandardCharsets.UTF_8))
        }

    }

    private fun loginUser(
            request: HttpServletRequest,
            username: String,
            password: String,
            response: HttpServletResponse
    ) {
        try {
            request.login(username, password)
            response.sendRedirect("/home")
        } catch (ex: Exception) {
            logger.error("Error during login", ex)
            response.sendRedirect("/auth/login?error=true")
        }
    }

    @PostMapping("/logout")
    fun logout(
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        request.logout()
        userProvider.clearCurrent()
        response.sendRedirect("/auth/login")
    }

}