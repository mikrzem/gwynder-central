package pl.net.gwynder.central.home

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import javax.servlet.http.HttpServletResponse

@Controller
class HomePageController(
        private val userProvider: CommonUserDetailsProvider
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
    fun home(): String {
        return "home/index"
    }

}