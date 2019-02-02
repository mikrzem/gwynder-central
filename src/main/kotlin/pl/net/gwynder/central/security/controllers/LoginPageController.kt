package pl.net.gwynder.central.security.controllers

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.net.gwynder.central.common.BaseService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/auth")
class LoginPageController : BaseService() {

    @GetMapping("/login")
    fun loginPage(
            @RequestParam("error", defaultValue = "false") error: Boolean,
            model: Model
    ): String {
        model.addAttribute("error", error)
        return "auth/login"
    }

    @PostMapping("/login/verify", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun verifyLogin(
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            request: HttpServletRequest,
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

}