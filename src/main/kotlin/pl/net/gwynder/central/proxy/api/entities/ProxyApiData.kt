package pl.net.gwynder.central.proxy.api.entities

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyApiData @JsonCreator constructor(
    val name: String,
    val path: String,
    val showDashboard: Boolean,
    val dashboardPath: String?,
    val checkHealth: Boolean,
    val hasApplication: Boolean
)