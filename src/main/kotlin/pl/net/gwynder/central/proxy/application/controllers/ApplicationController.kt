package pl.net.gwynder.central.proxy.application.controllers

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.AntPathMatcher
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import pl.net.gwynder.central.common.BaseService
import pl.net.gwynder.central.proxy.application.services.ProxyApplicationService
import pl.net.gwynder.central.security.services.CommonUserDetailsProvider
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/application")
class ApplicationController(
        private val service: ProxyApplicationService,
        private val restTemplate: RestTemplate,
        private val userProvider: CommonUserDetailsProvider
) : BaseService() {

    @RequestMapping("/{name}/**")
    fun proxyApi(
            @PathVariable("name") name: String,
            @RequestBody(required = false) body: String?,
            @RequestHeader requestHeaders: MultiValueMap<String, String>,
            method: HttpMethod,
            request: HttpServletRequest
    ): ResponseEntity<String> {
        val urlTail = AntPathMatcher().extractPathWithinPattern("/application/{name}/**", request.requestURI)
        val targetURI = service.applyPath(name, urlTail, request.queryString)
        val headers = createProxyHeaders(requestHeaders)
        val entity = body?.let { HttpEntity(it, headers) } ?: HttpEntity(headers)
        return try {
            val response = restTemplate.exchange(targetURI, method, entity, String::class.java)
            val responseHeaders = fixResponseHeaders(response.headers, urlTail)
            ResponseEntity.status(response.statusCodeValue)
                    .headers(responseHeaders)
                    .body(response.body)
        } catch (ex: HttpClientErrorException) {
            ResponseEntity.status(ex.rawStatusCode).build()
        } catch (ex: Exception) {
            logger.error("Error during proxying request", ex)
            ResponseEntity.status(500).build()
        }

    }

    private fun fixResponseHeaders(headers: HttpHeaders, urlTail: String): HttpHeaders {
        val resultHeaders = HttpHeaders.writableHttpHeaders(headers)
        if (urlTail.endsWith(".js")) {
            resultHeaders.set("Content-Type", "text/javascript")
        } else if (urlTail.endsWith(".css")) {
            resultHeaders.set("Content-Type", "text/css")
        }
        return resultHeaders
    }

    private fun createProxyHeaders(requestHeaders: MultiValueMap<String, String>): MultiValueMap<String, String> {
        userProvider.addUserHeader(requestHeaders)
        return requestHeaders
    }

}