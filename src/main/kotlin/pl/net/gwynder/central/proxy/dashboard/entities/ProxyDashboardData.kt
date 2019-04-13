package pl.net.gwynder.central.proxy.dashboard.entities

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyDashboardData @JsonCreator constructor(
        val active: Boolean,
        val path: String?
)