package pl.net.gwynder.central.proxy.api.entities

import com.fasterxml.jackson.annotation.JsonCreator
import pl.net.gwynder.central.dashboard.entities.ProxyDashboardData
import pl.net.gwynder.central.health.entities.ProxyHealthData
import pl.net.gwynder.central.proxy.application.entities.ProxyApplicationData

data class ProxyApiData @JsonCreator constructor(
        val name: String,
        val path: String,
        val application: ProxyApplicationData?,
        val dashboard: ProxyDashboardData?,
        val health: ProxyHealthData?
)