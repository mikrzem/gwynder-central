package pl.net.gwynder.central.proxy.health.entities

import com.fasterxml.jackson.annotation.JsonCreator

class HealthInfo @JsonCreator constructor(
        val healthy: Boolean,
        val startupTime: String
)

class HealthResponse @JsonCreator constructor(
        val code: Int,
        val info: HealthInfo?
)