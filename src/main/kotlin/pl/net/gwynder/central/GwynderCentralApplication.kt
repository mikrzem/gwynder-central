package pl.net.gwynder.central

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GwynderCentralApplication

fun main(args: Array<String>) {
    runApplication<GwynderCentralApplication>(*args)
}

