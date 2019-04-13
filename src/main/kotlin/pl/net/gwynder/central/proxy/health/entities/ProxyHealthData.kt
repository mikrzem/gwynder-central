package pl.net.gwynder.central.proxy.health.entities

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyHealthData @JsonCreator constructor(
        val active: Boolean,
        val path: String?
)