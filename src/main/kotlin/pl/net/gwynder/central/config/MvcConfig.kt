package pl.net.gwynder.central.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/resources/")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/resources/**").allowedOrigins("*")
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}