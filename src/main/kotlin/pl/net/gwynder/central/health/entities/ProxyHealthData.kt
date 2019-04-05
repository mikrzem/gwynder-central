package pl.net.gwynder.central.health.entities

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyHealthData @JsonCreator constructor(
        val active: Boolean,
        val path: String?
)